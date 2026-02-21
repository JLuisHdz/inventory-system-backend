package com.inventory.inventorysystem.dto.common;

public record ApiResoponse<T>(
        boolean success,
        String message,
        T data
) {}
