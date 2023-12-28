package com.eticaret.basketservice.controller;

import com.eticaret.basketservice.model.Basket;
import com.eticaret.basketservice.model.dto.BasketItemRequest;
import com.eticaret.basketservice.model.dto.BasketResponse;
import com.eticaret.basketservice.service.BasketService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(value = "Basket Management", description = "Operations pertaining to basket in E-commerce System")
@RestController
@RequestMapping("/basket")
public class BasketController {

    @Autowired
    private BasketService basketService;

    @ApiOperation(value = "Get a user's basket")
    @GetMapping("/{username}")
    public ResponseEntity<String> getBasketOfUser(@PathVariable String username) {
        BasketResponse basket = basketService.getBasketOfUser(username);
        return new ResponseEntity<>("Basket retrieved successfully for user: " + username, HttpStatus.OK);
    }

    @ApiOperation(value = "Add an item to the basket")
    @PostMapping("/add")
    public ResponseEntity<String> addToBasket(@RequestBody BasketItemRequest request) {
        Basket basket = basketService.addToBasket(request);
        return new ResponseEntity<>("Item added to basket successfully", HttpStatus.CREATED);
    }

    @ApiOperation(value = "Remove an item from the basket")
    @PostMapping("/remove")
    public ResponseEntity<String> removeFromBasket(@RequestBody BasketItemRequest request) {
        Basket basket = basketService.removeFromBasket(request);
        return new ResponseEntity<>("Item removed from basket successfully", HttpStatus.OK);
    }

    @ApiOperation(value = "Update the quantity of an item in the basket")
    @PutMapping("/update/{username}/{productId}/{quantity}")
    public ResponseEntity<String> updateQuantity(@PathVariable String username, @PathVariable long productId, @PathVariable int quantity) {
        Basket basket = basketService.updateQuantity(username, productId, quantity);
        return new ResponseEntity<>("Quantity updated successfully", HttpStatus.OK);
    }

    @ApiOperation(value = "Remove a product from the basket")
    @DeleteMapping("/removeProduct/{username}/{productId}")
    public ResponseEntity<String> removeProductFromBasket(@PathVariable String username, @PathVariable long productId) {
        Basket basket = basketService.removeProductFromBasket(username, productId);
        return new ResponseEntity<>("Product removed from basket successfully", HttpStatus.OK);
    }

    @ApiOperation(value = "Empty the basket")
    @DeleteMapping("/empty/{username}")
    public ResponseEntity<String> emptyBasket(@PathVariable String username) {
        Basket basket = basketService.emptyBasket(username);
        return new ResponseEntity<>("Basket emptied successfully", HttpStatus.OK);
    }
}