package com.project.repository;


import com.project.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long>{

    boolean existsByName(String name);

    Optional<Product> findByIdAndIsActiveTrue(Long id);

    Product findByIdAndIsDeletedFalse(Long id);

    Optional<Product> findByIdAndIsDeletedTrue(Long id);

    List<Product> findByIsDeletedTrue();

}
