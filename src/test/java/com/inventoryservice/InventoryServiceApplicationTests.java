package com.inventoryservice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@ImportAutoConfiguration(exclude = {
		org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration.class
})
class InventoryServiceApplicationTests {

	@Test
	void contextLoads() {
	}

}
