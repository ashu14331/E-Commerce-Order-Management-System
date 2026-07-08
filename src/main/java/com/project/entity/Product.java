package com.project.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

//@SQLDelete(sql = "UPDATE product SET is_deleted = true WHERE id=?")
//@SQLRestriction("is_deleted = false")

public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Product Name is required")
    private String name;

    @NotBlank(message = "Product Description is required")
    private String description;

    @Positive(message = "product price Always positive  in controller")
    private BigDecimal  price;

    private boolean isDeleted = false;

    private boolean isActive = true;

    private LocalDateTime deletedAt;

    private Long deletedBy;

    @OneToOne(cascade = CascadeType.ALL)
    private Inventory inventory;

}
