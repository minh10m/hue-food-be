package com.minh.Online.Food.Ordering.modules.cart;

import com.minh.Online.Food.Ordering.modules.cart.dto.AddCartItemRequest;
import com.minh.Online.Food.Ordering.modules.cart.dto.UpdateCartItemRequest;
import com.minh.Online.Food.Ordering.modules.cart.model.Cart;
import com.minh.Online.Food.Ordering.modules.cart.model.CartItem;
import com.minh.Online.Food.Ordering.modules.cart.service.CartService;
import com.minh.Online.Food.Ordering.modules.user.User;
import com.minh.Online.Food.Ordering.modules.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private UserService userService;

    @PutMapping("/cart/add")
    public ResponseEntity<CartItem> addItemToCart(
            @RequestBody AddCartItemRequest req,
            @AuthenticationPrincipal User user) throws Exception {
        CartItem cartItem = cartService.addItemToCart(req, user);
        return new ResponseEntity<>(cartItem, HttpStatus.OK);
    }

    @PutMapping("/cart-item/update")
    public ResponseEntity<CartItem> updateCartItemQuantity(
            @RequestBody UpdateCartItemRequest req,
            @AuthenticationPrincipal User user) throws Exception {
        CartItem cartItem = cartService.updateCartItemQuantity(req.getCartItemId(), req.getQuantity());
        return new ResponseEntity<>(cartItem, HttpStatus.OK);
    }

    @DeleteMapping("/cart-item/{id}/remove")
    public ResponseEntity<Cart> removeItemFromCart(
            @PathVariable Long id,
            @AuthenticationPrincipal User user) throws Exception {
        Cart cart = cartService.removeItemFromCart(id, user);
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @PutMapping("/cart/clear")
    public ResponseEntity<?> clearCart(@AuthenticationPrincipal User user) {
        try {
            Cart cart = cartService.clearCart(user.getId());
            return ResponseEntity.ok(cart);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error clearing cart: " + e.getMessage());
        }
    }

    @GetMapping("/cart")
    public ResponseEntity<Cart> findUserCart(@AuthenticationPrincipal User user) throws Exception {
        Cart cart = cartService.findCartByUserId(user.getId());
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }
}
