package com.project.dto.request;

import com.project.entity.Product;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
@Data
public class InventoryRequest {
    private Integer availableQuantity;
    private Integer reservedQuantity;
    private Product product ;
}
