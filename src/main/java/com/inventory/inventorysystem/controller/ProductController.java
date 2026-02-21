package com.inventory.inventorysystem.controller;

import com.inventory.inventorysystem.dto.common.ApiResoponse;
import com.inventory.inventorysystem.dto.common.PageResponse;
import com.inventory.inventorysystem.dto.product.ProductRequest;
import com.inventory.inventorysystem.dto.product.ProductResponse;
import com.inventory.inventorysystem.entity.Product;
import com.inventory.inventorysystem.mapper.ProductMapper;
import com.inventory.inventorysystem.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final ProductMapper productMapper;

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    @PostMapping
    public ResponseEntity<ApiResoponse<ProductResponse>> createProduct(@Valid @RequestBody ProductRequest request) {

        Product product = productMapper.toEntity(request);
        Product created = productService.createProduct(product);

        ProductResponse response = productMapper.toResponse(created);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ApiResoponse<>(
                        true,
                        "Product created successfully",
                        response
                ));
    }

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','EMPLOYEE')")
    @GetMapping
    public ResponseEntity<ApiResoponse<PageResponse<ProductResponse>>> getAllProducts(
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {

        Pageable pageable = PageRequest.of(page, size);

        Page<Product> productsPage = productService.getProducts(name, pageable);

        Page<ProductResponse> mappedPage =
                productsPage.map(productMapper::toResponse);

        PageResponse<ProductResponse> pageResponse =
                new PageResponse<>(
                        mappedPage.getContent(),
                        mappedPage.getNumber(),
                        mappedPage.getSize(),
                        mappedPage.getTotalElements(),
                        mappedPage.getTotalPages()
                );

        return ResponseEntity.ok(
                new ApiResoponse<>(
                        true,
                        "Products retrieved successfully",
                        pageResponse
                )
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
