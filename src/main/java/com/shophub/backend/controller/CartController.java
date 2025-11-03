package com.shophub.backend.controller;

import com.shophub.backend.dto.CartItemRequest;
import com.shophub.backend.entity.CartItem;
import com.shophub.backend.service.CartService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping
    public ResponseEntity<List<CartItem>> getCartItems() {
        return ResponseEntity.ok(cartService.getCartItems());
    }

    @PostMapping
    public ResponseEntity<CartItem> addToCart(@Valid @RequestBody CartItemRequest request) {
        return ResponseEntity.ok(cartService.addToCart(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CartItem> updateCartItem(
            @PathVariable Long id,
            @RequestParam Integer quantity) {
        return ResponseEntity.ok(cartService.updateCartItem(id, quantity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeFromCart(@PathVariable Long id) {
        cartService.removeFromCart(id);
        return ResponseEntity.noContent().build();
    }
}
