package com.inventoryservice.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class PaymentEvent {

    private String paymenMode;

    private String status;

    private Double ammount;

    private String paymentReferenceId;

    private InventoryPaymentDto inventoryPaymentDto;

    public InventoryPaymentDto getInventoryPaymentDto() {
        return inventoryPaymentDto;
    }

    public void setInventoryPaymentDto(InventoryPaymentDto inventoryPaymentDto) {
        this.inventoryPaymentDto = inventoryPaymentDto;
    }

    public String getPaymenMode() {
        return paymenMode;
    }

    public void setPaymenMode(String paymenMode) {
        this.paymenMode = paymenMode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getAmmount() {
        return ammount;
    }

    public void setAmmount(Double ammount) {
        this.ammount = ammount;
    }

    public String getPaymentReferenceId() {
        return paymentReferenceId;
    }

    public void setPaymentReferenceId(String paymentReferenceId) {
        this.paymentReferenceId = paymentReferenceId;
    }
}