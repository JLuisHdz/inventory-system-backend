package com.inventory.inventorysystem.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;

public record UserRequest(
        @Schema(example = "Jose")
        String name,

        @Schema(example = "Jose123")
        String username,

        @Schema(example = "password123")
        String password
) {
}
