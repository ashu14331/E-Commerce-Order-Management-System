package com.project.entity;

import jakarta.persistence.Id;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Setter
@Getter
@Data
@Document(collection = "productReview")
public class ProductReview {
    @Id
    private String id;
    private Long productId;
    private  Long userId;
    private int rating;
    private String comment;
    private LocalDateTime createdAt;
}
