package com.inventory.inventorysystem.dto.common;

public class InventoryStatsResponse {

    private long totalProducts;
    private long lowStockProducts;
    private double totalInventoryValue;

    public InventoryStatsResponse(long totalProducts,
                                  long lowStockProducts,
                                  double totalInventoryValue) {
        this.totalProducts = totalProducts;
        this.lowStockProducts = lowStockProducts;
        this.totalInventoryValue = totalInventoryValue;
    }

    public long getTotalProducts() {
        return totalProducts;
    }

    public long getLowStockProducts() {
        return lowStockProducts;
    }

    public double getTotalInventoryValue() {
        return totalInventoryValue;
    }
}
