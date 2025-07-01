package com.inventoryservice.serviceImpl;

import com.inventoryservice.entity.Stock;
import com.inventoryservice.exception.ResourceNotFoundException;
import com.inventoryservice.repository.StockRepository;
import com.inventoryservice.request.StockRequestDto;
import com.inventoryservice.response.ApiResponse;
import com.inventoryservice.response.StockResponseDto;
import com.inventoryservice.service.StockService;
import com.inventoryservice.util.GenericMapper;
import com.inventoryservice.util.JsonUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StockServiceImpl implements StockService {

    private final StockRepository stockRepository;

    private final JsonUtil jsonUtil;

    private final GenericMapper genericMapper;

    @Override
    public ApiResponse createStock(StockRequestDto stockRequestDto) {
        Stock stock = genericMapper.convert(stockRequestDto, Stock.class);

        stock = stockRepository.save(stock);

        return ApiResponse.response("stock created succesfully", true, "", genericMapper.convert(stock, StockRequestDto.class));
    }

    @Override
    public ApiResponse deleteStock(Long stockId) {

        if (!stockRepository.existsById(stockId)) {
            throw new ResourceNotFoundException("Invalid stock Id: " + stockId);
        }
        stockRepository.deleteById(stockId);

        return ApiResponse.response("Stock deleted successfully: " + stockId, true, null, null);
    }

    @Override
    public ApiResponse getStockById(Long id) {
        StockResponseDto stock = stockRepository.findById(id)
                .map(stk -> genericMapper.convert(stk, StockResponseDto.class))
                .orElseThrow(() -> new ResourceNotFoundException("stock Not found with id:" + id));

        return ApiResponse.response("Stock details found", true, "", stock);
    }

    @Override
    public ApiResponse getStockByName(String stockName) {
        StockResponseDto stock = stockRepository.findByStockName(stockName)
                .map(stk -> genericMapper.convert(stk, StockResponseDto.class))
                .orElseThrow(() -> new ResourceNotFoundException("stock Not found with stock name:" + stockName));

        return ApiResponse.response("Stock details found", true, "", stock);
    }

    @Override
    public ApiResponse getAllStocks() {
        List<StockResponseDto> stockList = stockRepository.findAll()
                .stream()
                .map(stk -> genericMapper.convert(stk, StockResponseDto.class))
                .toList();

        return ApiResponse.response("Stock details found", true, "", stockList);
    }

    @Override
    public Integer getStockQuantityByStockId(Long stockId) {
        Integer stockQuantity = stockRepository.findStockQuantityByStockId(stockId);
        if (stockQuantity == null) {
            throw new ResourceNotFoundException("Stock not found for id: " + stockId);
        }
        return stockQuantity;
    }

    public void updateStockQuantity(Long stockId, Integer remeaningQuantity) {
        Optional<Stock> stock = stockRepository.findById(stockId);
        stock.get().setStockQuantity(remeaningQuantity);
        stockRepository.save(stock.get());
    }

}
