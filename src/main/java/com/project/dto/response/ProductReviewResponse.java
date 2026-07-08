package com.project.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductReviewResponse {
    private String id;
    private Long productId;
    private  Long userId;
    private int rating;
    private String comment;
}
