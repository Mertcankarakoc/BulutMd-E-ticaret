package com.eticaret.orderservice.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Map;

@Entity
@Data
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "status")
    private String status;

    @JoinColumn(name = "user_id")
    private Long userId;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    private boolean adminApproval;

    @ElementCollection
    private Map<Long, Integer> productQuantities;
}