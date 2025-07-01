package com.inventoryservice.controller;

import com.inventoryservice.request.StockRequestDto;
import com.inventoryservice.response.ApiResponse;
import com.inventoryservice.service.StockService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api-stock")
@RequiredArgsConstructor
public class StockController {

    private final StockService stockService;

    @PostMapping("/create-stocks")
    public ResponseEntity<ApiResponse> createStock(@RequestBody @Valid StockRequestDto stockRequest) {
        return new ResponseEntity<>(stockService.createStock(stockRequest), HttpStatus.OK);

    }

    @GetMapping("/get-stockById")
    public ResponseEntity<ApiResponse> getStockById(@RequestParam Long stockId) {
        return new ResponseEntity<>(stockService.getStockById(stockId), HttpStatus.OK);

    }

    @GetMapping("/get-stockByName")
    public ResponseEntity<ApiResponse> getStockById(@RequestParam String stockName) {
        return new ResponseEntity<>(stockService.getStockByName(stockName), HttpStatus.OK);
    }

    @GetMapping("/get-allstocks")
    public ResponseEntity<ApiResponse> getStocks() {
        return new ResponseEntity<>(stockService.getAllStocks(), HttpStatus.OK);

    }

    @DeleteMapping("/delete-stock")
    public ResponseEntity<ApiResponse> deleteStock(@RequestParam Long stockId) {
        return new ResponseEntity<>(stockService.deleteStock(stockId), HttpStatus.OK);
    }

    @GetMapping("/get_stock_quantity")
    public Integer getStockQuantityById(@RequestParam Long stockId) {
        return stockService.getStockQuantityByStockId(stockId);
    }
}
