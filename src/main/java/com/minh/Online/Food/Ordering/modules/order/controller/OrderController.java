package com.minh.Online.Food.Ordering.modules.order.controller;

import com.minh.Online.Food.Ordering.modules.order.dto.OrderRequest;
import com.minh.Online.Food.Ordering.modules.order.model.Order;
import com.minh.Online.Food.Ordering.modules.order.service.OrderService;
import com.minh.Online.Food.Ordering.modules.payment.PaymentResponse;
import com.minh.Online.Food.Ordering.modules.payment.PaymentService;
import com.minh.Online.Food.Ordering.modules.user.User;
import com.minh.Online.Food.Ordering.modules.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private PaymentService paymentService;

    private final UserRepository userRepository;

    @Autowired
    public OrderController(OrderService orderService, PaymentService paymentService, UserRepository userRepository) {
        this.orderService = orderService;
        this.paymentService = paymentService;
        this.userRepository = userRepository;
    }

    private User getAuthenticatedUser(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("User not authenticated");
        }
        String email = authentication.getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @PostMapping("/order")
    public ResponseEntity<?> createOrder(
            @RequestBody OrderRequest req,
            Authentication authentication) {
        try {
            User user = getAuthenticatedUser(authentication);
            Long orderId = orderService.createOrder(req, user);
            Order order = orderService.findOrderById(orderId)
                    .orElseThrow(() -> new RuntimeException("Failed to create order"));
            
            PaymentResponse paymentResponse = paymentService.createPaymentLink(order);
            return ResponseEntity.status(HttpStatus.CREATED).body(paymentResponse);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error creating order: " + e.getMessage());
        }
    }

    @GetMapping("/order/user")
    public ResponseEntity<?> getOrderHistory(Authentication authentication) {
        try {
            User user = getAuthenticatedUser(authentication);
            List<Order> orders = orderService.getUserOrders(user.getId());
            return ResponseEntity.ok(orders);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching order history: " + e.getMessage());
        }
    }

}
