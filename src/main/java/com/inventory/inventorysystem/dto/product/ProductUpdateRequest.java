package com.inventory.inventorysystem.dto.product;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ProductUpdateRequest(
        @NotBlank
        String name,

        String description,

        @NotNull
        @Positive
        Double price,

        @NotNull
        @Min(0)
        Integer stock
) {}
