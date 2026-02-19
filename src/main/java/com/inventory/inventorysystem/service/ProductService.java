package com.inventory.inventorysystem.service;

import com.inventory.inventorysystem.entity.Product;
import com.inventory.inventorysystem.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public Product createProduct(Product product){
        product.setCreationDate(LocalDateTime.now());
        return productRepository.save(product);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public  void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
