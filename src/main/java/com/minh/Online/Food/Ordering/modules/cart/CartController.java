package com.minh.Online.Food.Ordering.modules.cart;

import com.minh.Online.Food.Ordering.modules.cart.dto.AddCartItemRequest;
import com.minh.Online.Food.Ordering.modules.cart.dto.UpdateCartItemRequest;
import com.minh.Online.Food.Ordering.modules.cart.model.Cart;
import com.minh.Online.Food.Ordering.modules.cart.model.CartItem;
import com.minh.Online.Food.Ordering.modules.cart.service.CartService;
import com.minh.Online.Food.Ordering.modules.user.User;
import com.minh.Online.Food.Ordering.modules.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private UserRepository userRepository;

    private User getAuthenticatedUser(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("User not authenticated");
        }
        
        if (authentication.getPrincipal() instanceof User) {
            return (User) authentication.getPrincipal();
        }
        
        // Fallback to database lookup if principal is not User
        String username = authentication.getName();
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + username));
    }
    
    @PutMapping("/cart/add")
    public ResponseEntity<CartItem> addItemToCart(
            @RequestBody AddCartItemRequest req) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = getAuthenticatedUser(authentication);
        CartItem cartItem = cartService.addItemToCart(req, user);
        return new ResponseEntity<>(cartItem, HttpStatus.OK);
    }

    @PutMapping("/cart-item/update")
    public ResponseEntity<CartItem> updateCartItemQuantity(
            @RequestBody UpdateCartItemRequest req) throws Exception {
        CartItem cartItem = cartService.updateCartItemQuantity(req.getCartItemId(), req.getQuantity());
        return new ResponseEntity<>(cartItem, HttpStatus.OK);
    }

    @DeleteMapping("/cart-item/{id}/remove")
    public ResponseEntity<Cart> removeItemFromCart(
            @PathVariable Long id) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = getAuthenticatedUser(authentication);
        Cart cart = cartService.removeItemFromCart(id, user);
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @PutMapping("/cart/clear")
    public ResponseEntity<?> clearCart() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User user = getAuthenticatedUser(authentication);
            Cart cart = cartService.clearCart(user.getId());
            return ResponseEntity.ok(cart);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/cart")
    public ResponseEntity<Cart> findUserCart() throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = getAuthenticatedUser(authentication);
        Cart cart = cartService.findCartByUserId(user.getId());
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }
}
