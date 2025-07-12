package com.inventoryservice.controller;

import com.inventoryservice.producer.InventorySuccessProducer;
import com.inventoryservice.request.InventoryRequestDto;
import com.inventoryservice.response.ApiResponse;
import com.inventoryservice.service.InventoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api-inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @PostMapping("/create-inventory")
    public ResponseEntity<ApiResponse> createInventory(@RequestBody InventoryRequestDto inventoryRequestDto) {
        return new ResponseEntity<>(inventoryService.createInventory(inventoryRequestDto), HttpStatus.OK);
    }
}
