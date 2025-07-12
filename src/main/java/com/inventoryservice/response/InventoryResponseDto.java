package com.inventoryservice.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryResponseDto {

    private Long inventoryId;

    private String userId;

    private Long stockId;

    private Double totalAmount;

    private Integer quantity;

    private String status;
}
