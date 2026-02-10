package com.example.java_sql.repository;

import com.example.java_sql.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    
    // Find products by name containing a keyword
    List<Product> findByNameContaining(String keyword);
    
    // Find products with price less than or equal to given value
    List<Product> findByPriceLessThanEqual(Double price);
    
    // Find products in stock (quantity > 0)
    List<Product> findByStockQuantityGreaterThan(Integer quantity);
}
