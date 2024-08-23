package com.Ecommerce.Repository;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.Ecommerce.Entity.ProductEntity;

public interface Product extends JpaRepository<ProductEntity,Integer> {
    Page<ProductEntity> findByNameContainingIgnoreCaseOrCategoryContainingIgnoreCaseOrBrandContainingIgnoreCaseOrDescriptionContainingIgnoreCase(
            String name, String category, String brand, String description, Pageable pageable);
}
