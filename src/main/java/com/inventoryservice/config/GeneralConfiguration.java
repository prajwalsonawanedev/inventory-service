package com.inventoryservice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inventoryservice.entity.Inventory;
import com.inventoryservice.request.InventoryRequestDto;
import org.modelmapper.TypeMap;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.modelmapper.convention.MatchingStrategies;

@Configuration
public class GeneralConfiguration {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        // set STRICT to avoid fuzzy matches
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        // DTO → Entity mapping
        TypeMap<InventoryRequestDto, Inventory> requestToEntity =
                modelMapper.createTypeMap(InventoryRequestDto.class, Inventory.class);

        // explicitly skip inventoryId
        requestToEntity.addMappings(mapper -> mapper.skip(Inventory::setInventoryId));

        return modelMapper;
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
