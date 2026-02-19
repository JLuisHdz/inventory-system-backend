package com.inventory.inventorysystem.dto.product;

import java.time.LocalDateTime;

public record ProductResponse(
        Long id,
        String name,
        String description,
        double price,
        int stock,
        LocalDateTime creationDate
) {}
