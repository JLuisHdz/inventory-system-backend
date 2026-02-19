package com.inventory.inventorysystem.mapper;

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
}
