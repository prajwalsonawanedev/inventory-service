package com.inventoryservice.enums;

public enum InventoryStatus {

    SUCCESS("inventory success"),
    FAILED("inventory failed"),
    VALIDATION_ERROR("validation error");

    private final String value;

    InventoryStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
