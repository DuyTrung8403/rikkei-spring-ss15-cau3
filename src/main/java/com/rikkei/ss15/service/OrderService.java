package com.rikkei.ss15.service;

import com.rikkei.ss15.dto.request.OrderRequest;
import com.rikkei.ss15.entity.Order;
import java.util.List;

public interface OrderService {
    Order createOrder(OrderRequest request, String email);
    List<Order> getMyOrders(String email);
    List<Order> getAllOrders();
    Order updateOrderStatus(Long id, String status);
}
