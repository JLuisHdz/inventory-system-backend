package com.inventory.inventorysystem.mapper;

import com.inventory.inventorysystem.dto.product.ProductRequest;
import com.inventory.inventorysystem.dto.product.ProductResponse;
import com.inventory.inventorysystem.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public ProductResponse toResponse(Product product){
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStock(),
                product.getCreationDate()
        );
    }

    public Product toEntity(ProductRequest request) {
        Product product = new Product();
        product.setName(request.name());
        product.setDescription(request.description());
        product.setPrice(request.price());
        product.setStock(request.stock());
        return product;
    }
}
