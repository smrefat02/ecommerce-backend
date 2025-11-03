package com.shophub.backend.repository;

import com.shophub.backend.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategory(String category);
    List<Product> findByIsActiveTrue();
    Optional<Product> findBySku(String sku);
    Boolean existsBySku(String sku);
}
