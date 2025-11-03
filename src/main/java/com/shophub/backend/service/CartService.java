package com.shophub.backend.service;

import com.shophub.backend.dto.CartItemRequest;
import com.shophub.backend.entity.CartItem;
import com.shophub.backend.entity.Product;
import com.shophub.backend.entity.User;
import com.shophub.backend.repository.CartItemRepository;
import com.shophub.backend.repository.ProductRepository;
import com.shophub.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CartService {

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public List<CartItem> getCartItems() {
        User user = getCurrentUser();
        return cartItemRepository.findByUser(user);
    }

    @Transactional
    public CartItem addToCart(CartItemRequest request) {
        User user = getCurrentUser();
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (product.getStockQuantity() < request.getQuantity()) {
            throw new RuntimeException("Insufficient stock");
        }

        return cartItemRepository.findByUserAndProductId(user, request.getProductId())
                .map(existingItem -> {
                    existingItem.setQuantity(existingItem.getQuantity() + request.getQuantity());
                    return cartItemRepository.save(existingItem);
                })
                .orElseGet(() -> {
                    CartItem cartItem = new CartItem();
                    cartItem.setUser(user);
                    cartItem.setProduct(product);
                    cartItem.setQuantity(request.getQuantity());
                    return cartItemRepository.save(cartItem);
                });
    }

    @Transactional
    public CartItem updateCartItem(Long id, Integer quantity) {
        User user = getCurrentUser();
        CartItem cartItem = cartItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));

        if (!cartItem.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized");
        }

        if (cartItem.getProduct().getStockQuantity() < quantity) {
            throw new RuntimeException("Insufficient stock");
        }

        cartItem.setQuantity(quantity);
        return cartItemRepository.save(cartItem);
    }

    @Transactional
    public void removeFromCart(Long id) {
        User user = getCurrentUser();
        CartItem cartItem = cartItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));

        if (!cartItem.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized");
        }

        cartItemRepository.delete(cartItem);
    }

    @Transactional
    public void clearCart() {
        User user = getCurrentUser();
        cartItemRepository.deleteByUser(user);
    }
}
