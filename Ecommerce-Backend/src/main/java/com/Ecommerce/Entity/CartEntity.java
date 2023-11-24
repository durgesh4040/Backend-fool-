package com.Ecommerce.Entity;

import java.util.List;

import jakarta.annotation.Generated;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class CartEntity {
	@Id 
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
private int id;
@OneToMany
private List<ProductEntity>products;
}
