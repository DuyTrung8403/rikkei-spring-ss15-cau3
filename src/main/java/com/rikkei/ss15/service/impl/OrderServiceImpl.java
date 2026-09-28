package com.rikkei.ss15.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.rikkei.ss15.dto.request.OrderRequest;
import com.rikkei.ss15.entity.Order;
import com.rikkei.ss15.entity.OrderItem;
import com.rikkei.ss15.entity.Product;
import com.rikkei.ss15.entity.User;
import com.rikkei.ss15.repository.OrderItemRepository;
import com.rikkei.ss15.repository.OrderRepository;
import com.rikkei.ss15.repository.ProductRepository;
import com.rikkei.ss15.repository.UserRepository;
import com.rikkei.ss15.service.OrderService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public Order createOrder(OrderRequest request, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Order order = Order.builder()
                .user(user)
                .createdDate(new Date())
                .status("PENDING")
                .totalMoney(BigDecimal.ZERO)
                .orderItems(new ArrayList<>())
                .build();
                
        order = orderRepository.save(order);

        BigDecimal totalMoney = BigDecimal.ZERO;

        for (var itemReq : request.getItems()) {
            Product product = productRepository.findById(itemReq.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found: " + itemReq.getProductId()));
            
            BigDecimal priceBuy = product.getPrice();
            BigDecimal itemTotal = priceBuy.multiply(BigDecimal.valueOf(itemReq.getQuantity()));
            totalMoney = totalMoney.add(itemTotal);
            
            OrderItem orderItem = OrderItem.builder()
                    .order(order)
                    .product(product)
                    .quantity(itemReq.getQuantity())
                    .priceBuy(priceBuy)
                    .build();
            
            orderItemRepository.save(orderItem);
            order.getOrderItems().add(orderItem);
        }
        
        order.setTotalMoney(totalMoney);
        return orderRepository.save(order);
    }

    @Override
    public List<Order> getMyOrders(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return orderRepository.findByUser(user);
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Order updateOrderStatus(Long id, String status) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        order.setStatus(status);
        return orderRepository.save(order);
    }
}
