package com.inventoryservice.service;


import com.inventoryservice.request.InventoryRequestDto;
import com.inventoryservice.response.ApiResponse;

public interface InventoryService {

    ApiResponse createInventory(InventoryRequestDto dto);

    ApiResponse getInventoryById(Long id);

    ApiResponse getAllInventories();

    ApiResponse deleteInventory(Long id);


}
