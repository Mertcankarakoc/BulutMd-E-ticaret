package com.eticaret.basketservice.service;

import com.eticaret.basketservice.model.Basket;
import com.eticaret.basketservice.model.dto.BasketItemRequest;
import com.eticaret.basketservice.model.dto.BasketResponse;
import com.eticaret.basketservice.model.dto.ProductDto;
import com.eticaret.basketservice.repository.BasketRepository;
import com.google.gson.Gson;
import org.apache.http.client.fluent.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class BasketService {

    @Autowired
    BasketRepository basketRepo;

    private final Gson gson = new Gson();
    private String productServiceUrl = "http://localhost:9001/product-service/products/";

    public BasketResponse getBasketOfUser(String username) {
        Basket basket = basketRepo.findByUsername(username);
        BasketResponse basketResponse = new BasketResponse();
        basketResponse.setUsername(basket.getUsername());
        basketResponse.setId(basket.getId());

        try {
            for (Map.Entry<Long, Integer> entry : basket.getProductQuantities().entrySet()) {
                String json = Request.Get(productServiceUrl + entry.getKey()).execute().returnContent().asString();
                ProductDto productDto = gson.fromJson(json, ProductDto.class);
                productDto.setQuantity(entry.getValue());
                basketResponse.getProducts().add(productDto);
            }
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
        return basketResponse;
    }

    public Basket addToBasket(BasketItemRequest request) {
        Basket basket = basketRepo.findByUsername(request.getUsername());

        if (basket == null) {
            basket = new Basket();
            basket.setUsername(request.getUsername());
        }

        Map<Long, Integer> productQuantities = basket.getProductQuantities();
        if (productQuantities == null) {
            basket.setProductQuantities(new HashMap<>());
        }

        basket.getProductQuantities().put(request.getProductId(), request.getQuantity());
        return basketRepo.save(basket);
    }

    public Basket removeFromBasket(BasketItemRequest request) {
        Basket basket = basketRepo.findByUsername(request.getUsername());
        basket.getProductQuantities().remove(request.getProductId());
        return basketRepo.save(basket);
    }

    public Basket updateQuantity(String username, long productId, int quantity) {
        Basket basket = basketRepo.findByUsername(username);
        if (basket == null) {
            throw new RuntimeException("Basket not found for user: " + username);
        }

        if (!basket.getProductQuantities().containsKey(productId)) {
            throw new RuntimeException("Product not found in the basket");
        }

        basket.getProductQuantities().put(productId, quantity);
        return basketRepo.save(basket);
    }

    public Basket removeProductFromBasket(String username, long productId) {
        Basket basket = basketRepo.findByUsername(username);
        if (basket == null) {
            throw new RuntimeException("Basket not found for user: " + username);
        }

        if (!basket.getProductQuantities().containsKey(productId)) {
            throw new RuntimeException("Product not found in the basket");
        }

        basket.getProductQuantities().remove(productId);
        return basketRepo.save(basket);
    }

    public Basket emptyBasket(String username) {
        Basket basket = basketRepo.findByUsername(username);
        if (basket == null) {
            throw new RuntimeException("Basket not found for user: " + username);
        }

        basket.getProductQuantities().clear();
        return basketRepo.save(basket);
    }
}