package com.minh.Online.Food.Ordering.modules.order.controller;

import com.minh.Online.Food.Ordering.modules.order.model.Order;
import com.minh.Online.Food.Ordering.modules.order.service.OrderService;
import com.minh.Online.Food.Ordering.modules.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("ap/admin")
public class AdminOrderController {

    @Autowired
    private OrderService orderService;


    @GetMapping("/order/restaurant/{id}")
    public ResponseEntity<List<Order>> getOrderHistory(
            @PathVariable Long id,
            @AuthenticationPrincipal User user,
            @RequestParam(required = false) String orderStatus) throws Exception {
                
        if (user == null) {
            throw new Exception("User not authenticated");
        }

        List<Order> orders = orderService.getRestaurantOrders(id, orderStatus);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @PutMapping("/order/{orderId}/{orderStatus}")
    public ResponseEntity<Order> updateOrderStatus(
            @PathVariable Long orderId,
            @PathVariable String orderStatus,
            @AuthenticationPrincipal User user) throws Exception {
                
        if (user == null) {
            throw new Exception("User not authenticated");
        }

        Order order = orderService.updateOrder(orderId, orderStatus);
        if (order == null) {
            throw new Exception("Failed to update order status");
        }

        return new ResponseEntity<>(order, HttpStatus.OK);
    }
}
