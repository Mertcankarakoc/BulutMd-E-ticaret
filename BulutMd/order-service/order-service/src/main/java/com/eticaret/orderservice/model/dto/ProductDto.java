package com.eticaret.orderservice.model.dto;

import lombok.Data;

@Data
public class ProductDto {

    private Long id;
    private String name;
    private String price;
    private Integer stock;
    private int quantity;
}
