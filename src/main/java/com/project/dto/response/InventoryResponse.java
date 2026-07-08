package com.project.dto.response;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Data
public class InventoryResponse{
    private Long id;
    private Integer availableQuantity;
    private Integer reservedQuantity;

}
