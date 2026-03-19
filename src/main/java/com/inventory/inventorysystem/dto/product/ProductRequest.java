package com.inventory.inventorysystem.dto.product;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProductRequest(

        @NotBlank
        @Schema(example = "Laptop")
        String name,

        @NotNull
        @Schema(example = "Laptop gamer")
        String description,

        @NotNull
        @Schema(example = "1500.0")
        Double price,
        @NotNull
        @Min(0)
        @NotNull
        @Schema(example = "10")
        Integer stock

        private Long categoryId;
) {
}
