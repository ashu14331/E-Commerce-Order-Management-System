package com.project.controller;

import com.project.dto.ApiResponse;
import com.project.dto.request.ProductRequest;
import com.project.dto.response.ProductResponse;
import com.project.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/admin/")
public class AdminController {


    private final ProductService productService;


    @PostMapping("/products/create")
    public ApiResponse<ProductResponse> createProduct(@Valid @RequestBody ProductRequest productRequest) {
        ProductResponse productResponse = productService.create(productRequest);
        return new ApiResponse<>(true, "Created", productResponse);
    }

    @DeleteMapping("/products/{id}")
    public String delete(@PathVariable Long id) {
        productService.delete(id);
        return "Product softDeleted Successfully";
    }

    @PatchMapping("/products/{id}/restore")
    public String restore(@PathVariable Long id) {
        productService.restore(id);
        return "Product restored Successfully";
    }

    @GetMapping("/products/deleted")
    public List<ProductResponse> deletedList() {
        return productService.deletedlist();
    }


}
