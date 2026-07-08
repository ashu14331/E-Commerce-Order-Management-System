package com.project.repository;

import com.project.entity.ProductReview;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductReviewRepository extends MongoRepository<ProductReview, String> {

}
