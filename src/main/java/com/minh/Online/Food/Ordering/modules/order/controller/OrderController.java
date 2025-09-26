package com.minh.Online.Food.Ordering.modules.order.controller;

import com.minh.Online.Food.Ordering.modules.order.dto.OrderRequest;
import com.minh.Online.Food.Ordering.modules.order.model.Order;
import com.minh.Online.Food.Ordering.modules.order.service.OrderService;
import com.minh.Online.Food.Ordering.modules.payment.dto.PaymentResponse;
import com.minh.Online.Food.Ordering.modules.payment.service.PaymentService;
import com.minh.Online.Food.Ordering.modules.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/order")
    public ResponseEntity<PaymentResponse> createOrder(
            @RequestBody OrderRequest req,
            @AuthenticationPrincipal User user) throws Exception {
                
        if (user == null) {
            throw new Exception("User not authenticated");
        }
        
        Long orderId = orderService.createOrder(req, user);
        Order order = orderService.findOrderById(orderId);
        if (order == null) {
            throw new Exception("Failed to create order");
        }
        
        PaymentResponse paymentResponse = paymentService.createPaymentLink(order);
        return ResponseEntity.status(HttpStatus.CREATED).body(paymentResponse);
    }


    @GetMapping("/order/user")
    public ResponseEntity<List<Order>> getOrderHistory(
            @AuthenticationPrincipal User user) throws Exception {
                
        if (user == null) {
            throw new Exception("User not authenticated");
        }

        List<Order> orders = orderService.getUserOrders(user.getId());
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

}
