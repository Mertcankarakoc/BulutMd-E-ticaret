package com.eticaret.orderservice.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "customer")
    private List<Order> orderHistory;
}
