package com.eticaret.basketservice.repository;

import com.eticaret.basketservice.model.Basket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BasketRepository extends JpaRepository<Basket,Long> {

    Basket findByUsername(String username);
}
