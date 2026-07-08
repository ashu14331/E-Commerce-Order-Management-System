package com.project.service.serviceImpl;

import com.project.dto.request.ProductReviewRequest;
import com.project.dto.response.ProductReviewResponse;
import com.project.entity.Product;
import com.project.entity.ProductReview;
import com.project.exception.ResourceNotFoundException;
import com.project.repository.ProductRepository;
import com.project.repository.ProductReviewRepository;
import com.project.service.ProductReviewService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ProductReviewServiceImpl implements ProductReviewService {


    private final ProductReviewRepository productReviewRepository;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;


    ProductReviewServiceImpl(ProductReviewRepository productReviewRepository, ModelMapper modelMapper, ProductRepository productRepository) {
        this.productReviewRepository = productReviewRepository;
        this.modelMapper = modelMapper;
        this.productRepository = productRepository;
    }


    @Override
    public ProductReviewResponse create(ProductReviewRequest productReviewRequest) {
        log.info("Start::ProductReviewServiceImpl create");
        Product product = productRepository.findByIdAndIsActiveTrue(productReviewRequest.getProductId()).orElseThrow(() -> new ResourceNotFoundException("Product Not Avilabel"));

        ProductReview productReview = productReviewRepository.save(prepareProductReviewEntity(productReviewRequest));
        log.info("End::ProductReviewServiceImpl create");
        return prepareProductReviewResponse(productReview);
    }


    public ProductReview prepareProductReviewEntity(ProductReviewRequest productReviewRequest) {
        log.info("Start::prepareProductReviewEntity");
        ProductReview productReview = modelMapper.map(productReviewRequest, ProductReview.class);
        log.info("End::prepareProductReviewEntity");
        return productReview;
    }

    public ProductReviewResponse prepareProductReviewResponse(ProductReview productReview) {
        log.info("Start::prepareProductReviewResponse");
        ProductReviewResponse productReviewResponse = modelMapper.map(productReview, ProductReviewResponse.class);
        log.info("End::prepareProductReviewResponse");
        return productReviewResponse;
    }


}
