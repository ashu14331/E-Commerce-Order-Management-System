package com.project.service.serviceImpl;

import com.project.dto.request.InventoryRequest;
import com.project.entity.Inventory;
import com.project.repository.InventoryRepository;
import com.project.service.InventoryService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class InventoryServiceImpl implements InventoryService {

    private final ModelMapper modelMapper;

    public  InventoryServiceImpl(ModelMapper modelMapper, InventoryRepository inventoryRepository){
        this.modelMapper = modelMapper;
    }

    public Inventory prepareInventoryEntity(InventoryRequest inventoryRequest){
       return modelMapper.map(inventoryRequest,Inventory.class);
    }
}
