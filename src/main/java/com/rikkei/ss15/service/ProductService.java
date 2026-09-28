package com.rikkei.ss15.service;

import com.rikkei.ss15.dto.request.ProductRequest;
import com.rikkei.ss15.entity.Product;
import java.util.List;

public interface ProductService {
    List<Product> getAllProducts();
    Product getProductById(Long id);
    Product createProduct(ProductRequest request);
    Product updateProduct(Long id, ProductRequest request);
    void deleteProduct(Long id);
}
