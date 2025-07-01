package com.inventoryservice.producer;

import com.inventoryservice.dto.InventoryFailedDto;
import com.inventoryservice.dto.InventoryPaymentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class InventoryFailedProducer {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendFailedToOrderService(String message) {
        kafkaTemplate.send("inventory-failed", message);
    }
}
