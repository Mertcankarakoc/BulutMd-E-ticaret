package com.eticaret.orderservice.controller;

import com.eticaret.orderservice.model.Order;
import com.eticaret.orderservice.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "Order Management", description = "Operations pertaining to order in Order Management System")
@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    public OrderController(OrderService orderService) {
            this.orderService = orderService;
        }

    @ApiOperation(value = "Create a new order", response = Order.class)
    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        Order savedOrder = orderService.createOrder(order);
        return ResponseEntity.ok(savedOrder);
    }

    @ApiOperation(value = "Get a list of all orders for admin", response = List.class)
    @GetMapping("/admin/orders")
    public ResponseEntity<List<Order>> getAllOrdersForAdmin() {
        List<Order> allOrders = orderService.getAllOrders();
        return ResponseEntity.ok(allOrders);
    }

    @ApiOperation(value = "Approve an order by its ID", response = Order.class)
    @PutMapping("/approve/{orderId}")
    public ResponseEntity<Order> approveOrder(@PathVariable Long orderId) {
        Order approvedOrder = orderService.approveOrder(orderId);
        return ResponseEntity.ok(approvedOrder);
    }

    @ApiOperation(value = "Reject an order by its ID", response = Order.class)
    @PutMapping("/reject/{orderId}")
    public ResponseEntity<Order> rejectOrder(@PathVariable Long orderId) {
        Order rejectedOrder = orderService.rejectOrder(orderId);
        return ResponseEntity.ok(rejectedOrder);
    }
}
