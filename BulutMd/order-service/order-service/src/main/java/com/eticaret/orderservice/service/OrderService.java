package com.eticaret.orderservice.service;

import com.eticaret.orderservice.model.Order;
import com.eticaret.orderservice.model.dto.ProductDto;
import com.eticaret.orderservice.model.dto.UserDto;
import com.eticaret.orderservice.repository.OrderRepository;
import com.google.gson.Gson;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private Gson gson = new Gson();
    private String productServiceUrl = "http://localhost:9001/product-service/";
    private String userServiceUrl = "http://localhost:9002/user-service/";

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order approveOrder(Long orderId) {
        Order order = getOrderById(orderId);
        order.setAdminApproval(true);
        Order approvedOrder = orderRepository.save(order);
        updateProductStock(order, false);
        return approvedOrder;
    }

    public Order rejectOrder(Long orderId) {
        Order order = getOrderById(orderId);
        order.setAdminApproval(false);
        Order rejectedOrder = orderRepository.save(order);
        updateProductStock(order, true);
        return rejectedOrder;
    }

    private Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found: " + orderId));
    }

    private void updateProductStock(Order order, boolean isAdd) {
        for (Map.Entry<Long, Integer> entry : order.getProductQuantities().entrySet()) {
            Long productId = entry.getKey();
            Integer quantity = entry.getValue();
            ProductDto productDto = getProductDtoById(productId);
            updateProductStock(productDto, quantity, isAdd);
        }
    }

    private ProductDto getProductDtoById(Long productId) {
        String json;
        try {
            json = Request.Get(productServiceUrl + productId).execute().returnContent().asString();
        } catch (IOException e) {
            throw new RuntimeException("Error while getting product: " + productId, e);
        }
        return gson.fromJson(json, ProductDto.class);
    }

    private void updateProductStock(ProductDto productDto, Integer quantity, boolean isAdd) {
        if (isAdd) {
            productDto.setStock(productDto.getStock() + quantity);
        } else {
            productDto.setStock(productDto.getStock() - quantity);
        }
        String productJson = gson.toJson(productDto);
        try {
            Request.Put(productServiceUrl + productDto.getId()).bodyString(productJson, ContentType.APPLICATION_JSON).execute();
        } catch (IOException e) {
            throw new RuntimeException("Error while updating product: " + productDto.getId(), e);
        }
    }

    public Order createOrder(Order order) {
        UserDto userDto = getUserDtoById(order.getUserId());
        if (userDto == null) {
            throw new RuntimeException("User not found: " + order.getUserId());
        }
        validateAndReduceProductStock(order);
        order.setStatus("new order");
        return orderRepository.save(order);
    }

    private UserDto getUserDtoById(Long userId) {
        String json;
        try {
            json = Request.Get(userServiceUrl + "users/" + userId).execute().returnContent().asString();
        } catch (IOException e) {
            throw new RuntimeException("Error while getting user: " + userId, e);
        }
        return gson.fromJson(json, UserDto.class);
    }

    private void validateAndReduceProductStock(Order order) {
        for (Map.Entry<Long, Integer> entry : order.getProductQuantities().entrySet()) {
            Long productId = entry.getKey();
            Integer quantity = entry.getValue();
            ProductDto productDto = getProductDtoById(productId);
            if (productDto.getStock() < quantity) {
                throw new RuntimeException("Not enough stock for product: " + productId);
            }
            updateProductStock(productDto, quantity, false);
        }
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
}