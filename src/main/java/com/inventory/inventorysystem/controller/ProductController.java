package com.inventory.inventorysystem.controller;

import com.inventory.inventorysystem.dto.common.ApiResponse;
import com.inventory.inventorysystem.dto.common.PageResponse;
import com.inventory.inventorysystem.dto.product.ProductRequest;
import com.inventory.inventorysystem.dto.product.ProductResponse;
import com.inventory.inventorysystem.dto.product.ProductUpdateRequest;
import com.inventory.inventorysystem.entity.Product;
import com.inventory.inventorysystem.mapper.ProductMapper;
import com.inventory.inventorysystem.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
@Tag(
        name = "Products",
        description = "Product management APIs"
)
@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final ProductMapper productMapper;

    @Operation(
            summary = "Create a new product",
            description = "Creates a new product and returns the created product information"
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Product created successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Access denied"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    @PostMapping
    public ResponseEntity<ApiResponse<ProductResponse>> createProduct(@Valid @RequestBody ProductRequest request) {

        Product product = productMapper.toEntity(request);
        Product created = productService.createProduct(product);

        ProductResponse response = productMapper.toResponse(created);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ApiResponse<>(
                        true,
                        "Product created successfully",
                        response
                ));
    }

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','EMPLOYEE')")
    @GetMapping
    @Operation(
            summary = "Get products",
            description = "Returns paginated, searchable and sortable list of products"
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Products retrieved successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Access denied")
    })
    public ResponseEntity<ApiResponse<PageResponse<ProductResponse>>> getAllProducts(
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id,asc") String sort
    ) {

        String[] sortParams = sort.split(",");

        String sortField = sortParams[0];
        Sort.Direction direction = Sort.Direction.ASC;

        if (sortParams.length > 1 && sortParams[1].equalsIgnoreCase("desc")) {
            direction = Sort.Direction.DESC;
        }

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(direction, sortField)
        );

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
                new ApiResponse<>(
                        true,
                        "Products retrieved successfully",
                        pageResponse
                )
        );
    }

    @Operation(
            summary = "Update a product",
            description = "Updates an existing product by its ID"
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Product updated successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Product not found"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Access denied")
    })
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResponse>> updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody ProductUpdateRequest request
    ) {

        Product updated = productService.updateProduct(id, request);

        ProductResponse response = productMapper.toResponse(updated);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Product updated successfully",
                        response
                )
        );
    }

    @Operation(
            summary = "Delete a product",
            description = "Deletes a product by its ID"
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "204", description = "Product deleted successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Product not found"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Access denied")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
