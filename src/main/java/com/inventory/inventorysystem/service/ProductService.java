package com.inventory.inventorysystem.service;

import com.inventory.inventorysystem.dto.common.InventoryStatsResponse;
import com.inventory.inventorysystem.dto.product.ProductUpdateRequest;
import com.inventory.inventorysystem.entity.Product;
import com.inventory.inventorysystem.exception.ResourceNotFoundException;
import com.inventory.inventorysystem.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id" + id));

        productRepository.delete(product);
    }

    public Page<Product> getProducts(String name, Pageable pageable) {

        if (name != null && !name.isBlank()) {
            return productRepository.findByNameContainingIgnoreCase(name, pageable);
        }

        return productRepository.findAll(pageable);
    }

    public Product updateProduct(Long id, ProductUpdateRequest request) {

        Product product = productRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Product not found with id " + id));

        product.setName(request.name());
        product.setDescription(request.description());
        product.setPrice(request.price());
        product.setStock(request.stock());

        return productRepository.save(product);
    }

    public InventoryStatsResponse getInventoryStats() {

        long totalProducts = productRepository.count();

        long lowStockProducts = productRepository.countByStockLessThan(5);

        double totalValue = productRepository.findAll()
                .stream()
                .mapToDouble(p -> p.getPrice() * p.getStock())
                .sum();

        return new InventoryStatsResponse(
                totalProducts,
                lowStockProducts,
                totalValue
        );

    }
}
