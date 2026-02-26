package com.inventory.inventorysystem.exception;

public record ErrorResponse(
        boolean success,
        String message
) {
}
