package com.project.dto.response;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Data
@Setter
@Getter
public class ProductResponse {
    private Long productId;
    private String name;
    private BigDecimal price;
    private Integer availableQuantity;
}
