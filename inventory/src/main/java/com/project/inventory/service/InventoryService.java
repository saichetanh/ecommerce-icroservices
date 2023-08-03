package com.project.inventory.service;

import com.project.inventory.dto.InventoryDto;
import com.project.inventory.model.Inventory;
import com.project.inventory.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    public List<InventoryDto> isPresent(List<String> sku) {
        List<Inventory> bySkuIn = inventoryRepository.findBySkuIn(sku);
      return bySkuIn.stream().map(inventory -> InventoryDto.builder().sku(inventory.getSku()).isPresent(inventory.getQuantity()>0).build()).toList();

    }
}
