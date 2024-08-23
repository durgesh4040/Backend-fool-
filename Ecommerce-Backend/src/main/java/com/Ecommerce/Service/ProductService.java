package com.Ecommerce.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.Ecommerce.Entity.ProductEntity;
import com.Ecommerce.Repository.Product;

@Service
public class ProductService {
	
	@Autowired
	public Product repository;
	
	  public Page<ProductEntity> getAllEntities(int page, int size) {
	        Pageable pageable = PageRequest.of(page, size);
	        return repository.findAll(pageable);
	    }
	  
	  
	    public Page<ProductEntity> searchProducts(String searchTerm, Pageable pageable) {
	        return repository.findByNameContainingIgnoreCaseOrCategoryContainingIgnoreCaseOrBrandContainingIgnoreCaseOrDescriptionContainingIgnoreCase(
	            searchTerm, searchTerm, searchTerm, searchTerm, pageable);
	    }
}
