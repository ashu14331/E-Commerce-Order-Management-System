package com.project.controller;


import com.project.dto.ApiResponse;
import com.project.dto.request.ProductRequest;
import com.project.dto.request.ProductReviewRequest;
import com.project.dto.response.ProductResponse;
import com.project.dto.response.ProductReviewResponse;
import com.project.service.ProductReviewService;
import com.project.service.ProductService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/products")
@Slf4j
public class ProductController {

    private final ProductReviewService productReviewService;
    private final ProductService productService;


    ProductController(ProductService productService, ProductReviewService productReviewService) {
        this.productService = productService;
        this.productReviewService = productReviewService;
    }

    @PostMapping("/{productId}/reviews")
    public ProductReviewResponse create(@RequestBody ProductReviewRequest productReviewRequest){
        return productReviewService.create(productReviewRequest);
    }

    @PostMapping("/create")
    public ApiResponse<ProductResponse> createProduct( @Valid @RequestBody ProductRequest productRequest) {
        log.info("Start:: creating product in product controller");
        ProductResponse productResponse =  productService.create(productRequest);
        ApiResponse<ProductResponse>  apiResponse= new ApiResponse<>(true,"Created",productResponse);
        log.info("End:: creating product in product controller");
        return apiResponse;
    }

    @PutMapping("update/{id}")
    public ApiResponse<ProductResponse> update( @PathVariable Long id, @RequestBody ProductRequest productRequest){
        log.info("Start:: updating product in product controller");
        ProductResponse productResponse = productService.update(id, productRequest);
        ApiResponse<ProductResponse> apiResponse = new ApiResponse<>(true, "UpdateSuccessful", productResponse);
        log.info("End:: updating product in product controller");
        return apiResponse;
    }

    @GetMapping("/{id}")
    public ApiResponse<ProductResponse> fetch (@PathVariable Long id){
        log.info("Start:: fetching product in product controller");
        ProductResponse  productResponse = productService.fatch(id);
       // ApiResponse<ProductResponse>  apiResponse= new ApiResponse<>(true,"Fatch successful",productResponse);
        log.info("End:: fetching product in product controller");
        return apiResponse(productResponse);
    }

    @DeleteMapping("delete/{id}")
    public ApiResponse<String> delete(@PathVariable Long id){
        log.info("Start:: deleting product in product controller");
        String string =  productService.delete(id);
        ApiResponse<String> apiResponse = new ApiResponse<>(true,string);
        log.info("End:: deleting product in product controller");
        return apiResponse;
    }

    public ApiResponse<ProductResponse> apiResponse(ProductResponse productResponse){
        return new ApiResponse<>(true,productResponse);
    }

}
