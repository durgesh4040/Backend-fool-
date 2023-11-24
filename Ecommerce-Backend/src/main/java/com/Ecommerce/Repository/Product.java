package com.Ecommerce.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import com.Ecommerce.Entity.ProductEntity;

public interface Product extends JpaRepository<ProductEntity,Integer> {

}
