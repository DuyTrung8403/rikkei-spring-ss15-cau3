package com.rikkei.ss15.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.rikkei.ss15.dto.request.ProductRequest;
import com.rikkei.ss15.entity.Product;
import com.rikkei.ss15.repository.ProductRepository;
import com.rikkei.ss15.service.ProductService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    @Override
    public Product createProduct(ProductRequest request) {
        Product product = Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .size(request.getSize())
                .toppings(request.getToppings())
                .build();
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Long id, ProductRequest request) {
        Product product = getProductById(id);
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setSize(request.getSize());
        product.setToppings(request.getToppings());
        return productRepository.save(product);
    }

    @Override
    public void deleteProduct(Long id) {
        Product product = getProductById(id);
        productRepository.delete(product);
    }
}
