package com.inventoryservice.service;

import com.inventoryservice.request.StockRequestDto;
import com.inventoryservice.response.ApiResponse;

public interface StockService {

    ApiResponse createStock(StockRequestDto stockRequestDto);

    ApiResponse deleteStock(Long id);

    ApiResponse getStockById(Long id);

    ApiResponse getStockByName(String stockName);

    ApiResponse getAllStocks();

    Integer getStockQuantityByStockId(Long id);

    void updateStockQuantity(Long stockId, Integer remeaningQuantity);


}
