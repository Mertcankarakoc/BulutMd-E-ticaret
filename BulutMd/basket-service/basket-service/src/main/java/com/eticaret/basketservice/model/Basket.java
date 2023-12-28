package com.eticaret.basketservice.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Entity
@Data
public class Basket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ElementCollection
    private List<Long> products;

    @Column(name = "username")
    private String username;

    @ElementCollection
    @CollectionTable(name = "product_quantities")
    @Column(name = "quantity")
    private Map<Long, Integer> productQuantities;

}