package com.project.service;




import com.project.dto.request.ProductRequest;
import com.project.dto.response.ProductResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService{

    ProductResponse create(ProductRequest productRequest);

    ProductResponse update(Long id, ProductRequest productRequest);

    ProductResponse fatch(Long id);

    Page<ProductResponse> lists(int pageNumber, int pageSize);

    String delete(Long id);

    void restore(Long id);
   List<ProductResponse> deletedlist();


}
