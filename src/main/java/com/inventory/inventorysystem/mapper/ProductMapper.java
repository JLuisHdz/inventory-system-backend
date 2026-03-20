package com.inventory.inventorysystem.mapper;

import com.inventory.inventorysystem.dto.category.CategoryResponse;
import com.inventory.inventorysystem.dto.product.ProductRequest;
import com.inventory.inventorysystem.dto.product.ProductResponse;
import com.inventory.inventorysystem.entity.Category;
import com.inventory.inventorysystem.entity.Product;
import com.inventory.inventorysystem.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    @Autowired
    private CategoryRepository categoryRepository;

    public ProductResponse toResponse(Product product){
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStock(),
                product.getCreationDate(),
                product.getCategory() != null
                        ? new CategoryResponse(
                        product.getCategory().getId(),
                        product.getCategory().getName()
                )
                        : null
                );
    }

    public Product toEntity(ProductRequest request) {
        Product product = new Product();
        product.setName(request.name());
        product.setDescription(request.description());
        product.setPrice(request.price());
        product.setStock(request.stock());
        if (request.categoryId() != null){
            Category category = categoryRepository
                    .findById(request.categoryId())
                    .orElse(null);

            product.setCategory(category);
        }
        return product;
    }


}
