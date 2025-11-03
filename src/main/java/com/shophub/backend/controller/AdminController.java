package com.shophub.backend.controller;

import com.shophub.backend.dto.OrderStatusRequest;
import com.shophub.backend.dto.ProductRequest;
import com.shophub.backend.entity.Order;
import com.shophub.backend.entity.Product;
import com.shophub.backend.entity.User;
import com.shophub.backend.repository.UserRepository;
import com.shophub.backend.service.DashboardService;
import com.shophub.backend.service.OrderService;
import com.shophub.backend.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private DashboardService dashboardService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/dashboard")
    public ResponseEntity<Map<String, Object>> getDashboardStats() {
        return ResponseEntity.ok(dashboardService.getDashboardStats());
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userRepository.findAll();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/dashboard/recent-orders")
    public ResponseEntity<List<Order>> getRecentOrders() {
        try {
            Object result = dashboardService.getRecentOrders();
            if (result instanceof List) {
                return ResponseEntity.ok((List<Order>) result);
            }
            return ResponseEntity.ok(new ArrayList<>());
        } catch (Exception e) {
            return ResponseEntity.ok(new ArrayList<>());
        }
    }

    @GetMapping("/dashboard/top-products")
    public ResponseEntity<List<Product>> getTopProducts() {
        try {
            Object result = dashboardService.getTopProducts();
            if (result instanceof List) {
                return ResponseEntity.ok((List<Product>) result);
            }
            return ResponseEntity.ok(new ArrayList<>());
        } catch (Exception e) {
            return ResponseEntity.ok(new ArrayList<>());
        }
    }

    @PostMapping("/products")
    public ResponseEntity<Product> createProduct(@Valid @RequestBody ProductRequest request) {
        return ResponseEntity.ok(productService.createProduct(request));
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<Product> updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody ProductRequest request) {
        return ResponseEntity.ok(productService.updateProduct(id, request));
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/orders")
    public ResponseEntity<List<Order>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @PutMapping("/orders/{id}/status")
    public ResponseEntity<Order> updateOrderStatus(
            @PathVariable Long id,
            @Valid @RequestBody OrderStatusRequest request) {
        return ResponseEntity.ok(orderService.updateOrderStatus(id, request.getStatus()));
    }
}
