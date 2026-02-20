package com.inventory.inventorysystem.dto.product;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProductRequest(
        @NotBlank
        String name,
        @NotNull
        Double price,
        @NotNull
        @Min(0)
        Integer stock
) {
}
