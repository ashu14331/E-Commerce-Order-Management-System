package com.project.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductRequest {
    @NotBlank(message = "name is required")
    private String name;

    @NotBlank(message = "description is required")
    private String description;

    @Positive(message = "Price GreaterThan Zero")
    private BigDecimal price;

    @Min(0)
    private Integer availableQuantity;

}
