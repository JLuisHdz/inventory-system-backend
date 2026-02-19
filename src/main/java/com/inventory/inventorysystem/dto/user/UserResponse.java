package com.inventory.inventorysystem.dto.user;

import java.time.LocalDateTime;
import java.util.Set;

public record UserResponse(
        Long id,
        String name,
        String username,
        boolean active,
        LocalDateTime creationDate,
        Set<String> roles
) {}
