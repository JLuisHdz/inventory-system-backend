package com.inventory.inventorysystem.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {

    @Schema(example = "Jose123")
    private String username;

    @Schema(example = "password123")
    private String password;
}

