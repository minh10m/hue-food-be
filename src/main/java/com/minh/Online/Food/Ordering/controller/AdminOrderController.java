package com.minh.Online.Food.Ordering.controller;

import com.minh.Online.Food.Ordering.model.Order;
import com.minh.Online.Food.Ordering.model.User;
import com.minh.Online.Food.Ordering.request.OrderRequest;
import com.minh.Online.Food.Ordering.service.OrderService;
import com.minh.Online.Food.Ordering.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("ap/admin")
public class AdminOrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @GetMapping("/order/restaurant/{id}")
    public ResponseEntity<List<Order>> getOrderHistory(
            @PathVariable Long id,
            @RequestHeader("Authorization") String jwt,
            @RequestParam(required = false) String orderStatus) throws Exception {
        User user = userService.findUserByJwtToken(jwt);

        List<Order> orders = orderService.getRestaurantOrders(id, orderStatus);

        return new ResponseEntity<>(orders, HttpStatus.OK);

    }

    @PutMapping("/order/{orderId}/{orderStatus}")
    public ResponseEntity<Order> updateOrderStatus(
            @PathVariable Long orderId,
            @PathVariable String orderStatus,
            @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);

        Order orders = orderService.updateOrder(orderId, orderStatus);

        return new ResponseEntity<>(orders, HttpStatus.OK);

    }
}
