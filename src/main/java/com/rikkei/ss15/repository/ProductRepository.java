package com.rikkei.ss15.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.rikkei.ss15.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
