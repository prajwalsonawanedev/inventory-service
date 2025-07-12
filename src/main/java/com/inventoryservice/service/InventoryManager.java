package com.inventoryservice.service;

import com.inventoryservice.dto.OrderInventoryDto;
import com.inventoryservice.request.InventoryRequestDto;
import com.inventoryservice.response.InventoryResponseDto;

import java.util.List;

public interface InventoryManager {

    InventoryResponseDto handelFailedInventory(InventoryRequestDto inventoryRequestDto, List<String> errorList);

    InventoryResponseDto handelSuccessInventory(InventoryRequestDto inventoryRequestDto, List<String> errorList);

}