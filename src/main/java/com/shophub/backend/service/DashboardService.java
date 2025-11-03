package com.shophub.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.shophub.backend.repository.ProductRepository;
import com.shophub.backend.repository.OrderRepository;
import com.shophub.backend.repository.UserRepository;
import com.shophub.backend.entity.Order;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DashboardService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    public Map<String, Object> getDashboardStats() {
        Map<String, Object> stats = new HashMap<>();

        long totalProducts = 0;
        long totalOrders = 0;
        long totalUsers = 0;
        double totalRevenue = 0.0;

        try {
            totalProducts = productRepository.count();
        } catch (Exception e) {
            totalProducts = 0;
        }

        try {
            totalOrders = orderRepository.count();
        } catch (Exception e) {
            totalOrders = 0;
        }

        try {
            totalUsers = userRepository.count();
        } catch (Exception e) {
            totalUsers = 0;
        }

        try {
            List<Order> allOrders = orderRepository.findAll();
            totalRevenue = allOrders.stream()
                    .map(Order::getTotalAmount)
                    .filter(amount -> amount != null)
                    .mapToDouble(BigDecimal::doubleValue)
                    .sum();
        } catch (Exception e) {
            totalRevenue = 0.0;
        }

        stats.put("totalProducts", totalProducts);
        stats.put("totalOrders", totalOrders);
        stats.put("userCount", totalUsers);
        stats.put("totalRevenue", totalRevenue);

        return stats;
    }

    public Object getRecentOrders() {
        try {
            return orderRepository.findAll();
        } catch (Exception e) {
            return null;
        }
    }

    public Object getTopProducts() {
        try {
            return productRepository.findAll();
        } catch (Exception e) {
            return null;
        }
    }
}
