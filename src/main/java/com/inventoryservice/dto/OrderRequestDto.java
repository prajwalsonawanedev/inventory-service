package com.inventoryservice.dto;


import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderRequestDto {

    private Long orderId;

    private String userId;

    private String productId;

    private Integer quantity;

    private Double price;

    private Double totalAmount; // optional

    private String status;      // optional
}
