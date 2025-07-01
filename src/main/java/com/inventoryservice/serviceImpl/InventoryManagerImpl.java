package com.inventoryservice.serviceImpl;

import com.inventoryservice.constant.CommonConstant;
import com.inventoryservice.dto.NotificationDto;
import com.inventoryservice.entity.Inventory;
import com.inventoryservice.enums.InventoryStatus;
import com.inventoryservice.producer.KafkaProducer;
import com.inventoryservice.repository.InventoryRepository;
import com.inventoryservice.request.InventoryRequestDto;
import com.inventoryservice.response.InventoryResponseDto;
import com.inventoryservice.service.InventoryManager;
import com.inventoryservice.util.GenericMapper;
import com.inventoryservice.util.JsonUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryManagerImpl implements InventoryManager {

    private final GenericMapper genericMapper;

    private final InventoryRepository inventoryRepository;

    private final JsonUtil jsonUtil;

    private final KafkaProducer kafkaProducer;

    @Override
    public InventoryResponseDto handelFailedInventory(InventoryRequestDto inventoryRequestDto, List<String> errorList) {

        String errorMessage = StringUtils.join(errorList, "- ");

        inventoryRequestDto.setStatus(InventoryStatus.VALIDATION_ERROR.getValue());
        Inventory inventory = genericMapper.convert(inventoryRequestDto, Inventory.class);
        inventory = inventoryRepository.save(inventory);

        InventoryResponseDto inventoryResponseDto = genericMapper.convert(inventory, InventoryResponseDto.class);
        NotificationDto notificationDto = createNotificationDto(inventoryResponseDto, errorMessage, false);
        String jsonMessage = jsonUtil.toJson(notificationDto);

        kafkaProducer.sendMessage(CommonConstant.INVENTORY_FAILED_TOPIC, jsonMessage);
        return inventoryResponseDto;
    }

    @Override
    public InventoryResponseDto handelSuccessInventory(InventoryRequestDto inventoryRequestDto, List<String> errorList) {

        inventoryRequestDto.setStatus(InventoryStatus.SUCCESS.getValue());
        Inventory inventory = genericMapper.convert(inventoryRequestDto, Inventory.class);
        inventory = inventoryRepository.save(inventory);

        InventoryResponseDto inventoryResponseDto = genericMapper.convert(inventory, InventoryResponseDto.class);
        NotificationDto notificationDto = createNotificationDto(inventoryResponseDto, "Success", true);
        String jsonMessage = jsonUtil.toJson(notificationDto);

        kafkaProducer.sendMessage(CommonConstant.INVENTORY_SUCCESS_TOPIC, jsonMessage);

        return inventoryResponseDto;
    }

    public NotificationDto createNotificationDto(InventoryResponseDto inventoryResponseDto, String errorMessage, Boolean isSuccess) {

        String message = jsonUtil.toJson(inventoryResponseDto);

        return NotificationDto
                .builder()
                .isSuccess(isSuccess)
                .serviceName(CommonConstant.INVENTORY_SERVICE)
                .message(message)
                .reasonOfFailure(errorMessage)
                .build();
    }
}
