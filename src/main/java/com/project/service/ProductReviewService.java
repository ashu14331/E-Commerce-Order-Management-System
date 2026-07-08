package com.project.service;

import com.project.dto.request.ProductReviewRequest;
import com.project.dto.response.ProductReviewResponse;

public interface ProductReviewService {


    public ProductReviewResponse create(ProductReviewRequest productReviewRequest);
}
