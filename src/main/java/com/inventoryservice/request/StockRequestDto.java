package com.inventoryservice.request;

import lombok.AllArgsConstructor;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockRequestDto {


    @NotBlank(message = "Stock name is required")
    private String stockName;

    @Min(value = 1, message = "Price must be greater than 0")
    private Double price;

    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer stockQuantity;
}
