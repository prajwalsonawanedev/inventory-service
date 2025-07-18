package com.inventoryservice;

import com.inventoryservice.dto.InventoryPaymentDto;
import com.inventoryservice.producer.InventorySuccessProducer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class InventoryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(InventoryServiceApplication.class, args);
        System.out.println("Inventory Service !");

    }

}
