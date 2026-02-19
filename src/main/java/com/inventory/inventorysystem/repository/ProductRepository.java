package com.inventory.inventorysystem.repository;

import com.inventory.inventorysystem.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
