package com.Ecommerce.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Ecommerce.Entity.CartEntity;

public interface Cart extends JpaRepository<CartEntity,Integer> {

}
