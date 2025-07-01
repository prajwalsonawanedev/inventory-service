package com.inventoryservice.producer;

import com.inventoryservice.dto.InventoryPaymentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

@Service
public class InventorySuccessProducer {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessageToPayment(String message) {
        System.out.println("inside sendMessageToPayment()");
        kafkaTemplate.send("inventory-success", message);

    }
}
