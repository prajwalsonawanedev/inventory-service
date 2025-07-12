package com.inventoryservice.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.inventoryservice.dto.NotificationDto;
import com.inventoryservice.dto.OrderInventoryDto;
import com.inventoryservice.dto.PaymentEvent;
import com.inventoryservice.request.InventoryRequestDto;
import com.inventoryservice.response.ApiResponse;
import com.inventoryservice.response.InventoryResponseDto;
import com.inventoryservice.service.InventoryManager;
import com.inventoryservice.service.InventoryService;
import com.inventoryservice.util.JsonUtil;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class Consumer {

    private static final Logger log = LoggerFactory.getLogger(Consumer.class);

    private final InventoryService inventoryService;

    private final InventoryManager inventoryManager;

    private final JsonUtil jsonUtil;

    public Consumer(InventoryService inventoryService, InventoryManager inventoryManager, JsonUtil jsonUtil) {
        this.inventoryService = inventoryService;
        this.inventoryManager = inventoryManager;
        this.jsonUtil = jsonUtil;
    }

    @KafkaListener(
            topics = "order-created",
            groupId = "inventory",
            containerFactory = "kafkaListenerContainerFactory"  // reference your DTO-based factory
    )
    public void readMessageOrderCreated(String message) {
        log.info("Message received: {}", message);
        NotificationDto notificationDto = jsonUtil.fromJson(message, NotificationDto.class);
        InventoryRequestDto inventoryRequestDto = jsonUtil.fromJson(notificationDto.getMessage(), InventoryRequestDto.class);
        ApiResponse apiResponse = inventoryService.createInventory(inventoryRequestDto);
    }

    @KafkaListener(
            topics = "payment-failed",
            groupId = "paymentfailed",
            containerFactory = "kafkaListenerContainerFactory"  // reference your DTO-based factory
    )
    public void readMessagePaymentFailed(String message) {
        log.info("Message received: {}", message);

        PaymentEvent paymentEvent = jsonUtil.fromJson(message, PaymentEvent.class);
        System.out.println("Prajwal");

    }

    @KafkaListener(topics = "notify", groupId = "")
    public void readMessageFromNotification(String message) throws JsonProcessingException {

        log.info("Message received: {}", message);

        NotificationDto notificationDto = jsonUtil.fromJson(message, NotificationDto.class);
    }
}
