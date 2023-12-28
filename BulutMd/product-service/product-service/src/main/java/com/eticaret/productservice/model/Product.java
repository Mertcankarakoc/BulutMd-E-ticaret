package com.eticaret.productservice.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn
    private Long id;

    private String name;
    private Double price;
    private Integer stock;
    private String imageUrl;
}
