package com.inventoryservice.serviceImpl;


import com.inventoryservice.entity.Inventory;
import com.inventoryservice.exception.ResourceNotFoundException;
import com.inventoryservice.repository.InventoryRepository;
import com.inventoryservice.repository.StockRepository;
import com.inventoryservice.request.InventoryRequestDto;
import com.inventoryservice.response.ApiResponse;
import com.inventoryservice.response.InventoryResponseDto;
import com.inventoryservice.service.InventoryManager;
import com.inventoryservice.service.InventoryService;
import com.inventoryservice.service.StockService;
import com.inventoryservice.util.GenericMapper;
import com.inventoryservice.validation.InventoryValidation;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;

    private final InventoryManager inventoryManager;

    private final GenericMapper genericMapper;

    private final StockService stockService;

    private List<String> errorList = new ArrayList<>();

    @Override
    public ApiResponse createInventory(InventoryRequestDto inventoryRequestDto) {

        if (validateInventory(inventoryRequestDto)) {
            InventoryResponseDto inventoryResponseDto = inventoryManager.handelSuccessInventory(inventoryRequestDto, errorList);
            return ApiResponse.response("Inventory Detail Success", true, "Success", inventoryResponseDto);
        }

        InventoryResponseDto inventoryResponseDto = inventoryManager.handelFailedInventory(inventoryRequestDto, errorList);
        String errorMessage = StringUtils.join(errorList, "- ");

        return ApiResponse.response("Validation Error", false, errorMessage, inventoryResponseDto);
    }

    @Override
    public ApiResponse getInventoryById(Long id) {

        InventoryResponseDto inventoryResponseDto = inventoryRepository.findById(id)
                .map(ivnt -> genericMapper.convert(ivnt, InventoryResponseDto.class))
                .orElseThrow(() -> new ResourceNotFoundException("Inventory Details Not found with Id :" + id));

        return ApiResponse.response("Inventory Details Found", true, "Success", inventoryResponseDto);
    }

    @Override
    public ApiResponse getAllInventories() {
        List<InventoryResponseDto> inventoryResponseDtoList = inventoryRepository.findAll()
                .stream()
                .map(ivnt -> genericMapper.convert(ivnt, InventoryResponseDto.class))
                .toList();
        if (!CollectionUtils.isEmpty(inventoryResponseDtoList)) {
            return ApiResponse.response("Inventory Details Found", true, "Success", inventoryResponseDtoList);
        }
        return ApiResponse.response("Inventory Details Not  Found", true, "Failed", inventoryResponseDtoList);
    }

    @Override
    public ApiResponse deleteInventory(Long id) {
        if (!inventoryRepository.existsById(id)) {
            throw new ResourceNotFoundException("Invalid stock Id: " + id);
        }
        inventoryRepository.deleteById(id);

        return ApiResponse.response("Inventory Details Deleted Successfully", true, "Success", null);

    }

    public boolean validateInventory(InventoryRequestDto inventoryRequestDto) {

        errorList = InventoryValidation.validateInventoryRequest(inventoryRequestDto);
        Integer stockQuantity = stockService.getStockQuantityByStockId(inventoryRequestDto.getStockId());

        if (CollectionUtils.isEmpty(errorList) && inventoryRequestDto.getQuantity() < stockQuantity) {
            stockService.updateStockQuantity(inventoryRequestDto.getStockId(), stockQuantity - inventoryRequestDto.getQuantity());
            return true;
        }

        return false;
    }
}
