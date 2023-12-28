package com.eticaret.basketservice.model.dto;

import lombok.Data;

@Data
public class BasketItemRequest {

    private String username;
    private Long productId;
    private Integer quantity;

}