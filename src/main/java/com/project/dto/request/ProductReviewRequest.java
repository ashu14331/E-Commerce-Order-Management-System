package com.project.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ProductReviewRequest{
    private Long productId;
    private  Long userId;
    private int rating;
    private String comment;
    private LocalDateTime createdAt;
}
