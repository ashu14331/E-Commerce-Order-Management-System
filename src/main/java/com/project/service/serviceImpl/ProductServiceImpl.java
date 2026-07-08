package com.project.service.serviceImpl;

import com.project.dto.request.ProductRequest;
import com.project.dto.response.ProductResponse;
import com.project.entity.Inventory;
import com.project.entity.Product;
import com.project.exception.DuplicateResourceException;
import com.project.exception.ResourceNotFoundException;
import com.project.repository.ProductRepository;
import com.project.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final RedisTemplate<String,Object> redisTemplate;

    public ProductServiceImpl(ProductRepository productRepository,RedisTemplate<String,Object> redisTemplate) {
        this.productRepository = productRepository;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public ProductResponse create(ProductRequest productRequest) {
        log.info("Start :: Creating product ");
        if (productRepository.existsByName(productRequest.getName())) {
            throw new DuplicateResourceException("Product Already available");
        }
        Product product = productRepository.save(prepareProductEntity(productRequest));

        String key ="product:"+product.getId();
        redisTemplate.opsForValue().set(key,product,Duration.ofHours(1));

        log.info("Stored in Redis :: {}", key);

        log.info("End :: Creating product ");
        return prepareProductResponse(product);
    }

    @Override
    public ProductResponse update(Long id, ProductRequest productRequest) {
        log.info("Start :: Updating product  in service impl");
        Product product = productRepository.findByIdAndIsDeletedFalse(id);
        if (product == null) {
            throw new ResourceNotFoundException("Product not found");
        }
        product.setPrice(productRequest.getPrice());
        productRepository.save(product);
        log.info("End :: Updating product ");
        return prepareProductResponse(product);
    }

    @Override
    public ProductResponse fatch(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        return prepareProductResponse(product);
    }

    @Override
    public Page<ProductResponse> lists(int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        Page<Product> productPage = productRepository.findAll(pageRequest);
       return productPage.map(this::prepareProductResponse);
    }

    @Override
    public String delete(Long id) {
        log.info("Start :: Deleted product ");
        Product product = productRepository.findByIdAndIsDeletedFalse(id);
        if (product == null) {
            throw new ResourceNotFoundException("Product not found");
        }
        product.setDeleted(true);
        product.setDeletedAt(LocalDateTime.now());
        productRepository.save(product);

        String key = "product:"+id;
        redisTemplate.delete(key);

        log.info("End :: Deleted product ");
        return "Product deleted successfully";
    }

    @Override
    public void  restore(Long id) {
        Product product =  productRepository.findByIdAndIsDeletedTrue(id)
                .orElseThrow(() ->new ResourceNotFoundException("Product not found"));
        product.setDeleted(false);
        product.setDeletedAt(null);
        productRepository.save(product);
    }

    @Override
    public List<ProductResponse> deletedlist() {
     List<Product>  isDeletedTruelist = productRepository.findByIsDeletedTrue();
     return isDeletedTruelist.stream().map(product -> prepareProductResponse(product)).toList();
    }

    public Product prepareProductEntity(ProductRequest productRequest) {
        Product product = new Product();
        product.setName(productRequest.getName());
        product.setPrice(productRequest.getPrice());
        product.setDescription(productRequest.getDescription());
        Inventory inventory = new Inventory();
        inventory.setAvailableQuantity(productRequest.getAvailableQuantity());
        product.setInventory(inventory);
        return product;
    }

    public ProductResponse prepareProductResponse(Product product) {
        ProductResponse productResponse = new ProductResponse();
        productResponse.setName(product.getName());
        productResponse.setPrice(product.getPrice());
        productResponse.setProductId(product.getId());
        productResponse.setAvailableQuantity(product.getInventory().getAvailableQuantity());
        return productResponse;
    }
}
