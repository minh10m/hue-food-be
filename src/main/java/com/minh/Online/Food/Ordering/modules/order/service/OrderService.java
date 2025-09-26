package com.minh.Online.Food.Ordering.modules.order.service;

import com.minh.Online.Food.Ordering.modules.order.dto.OrderRequest;
import com.minh.Online.Food.Ordering.modules.order.model.Order;
import com.minh.Online.Food.Ordering.modules.user.User;

import java.util.List;

public interface OrderService {

    public Long createOrder(OrderRequest req, User user) throws Exception;

    public Order updateOrder(Long orderId, String orderStatus) throws Exception;

    public void cancelOrder(Long orderId) throws Exception;

    public List<Order> getUserOrders(Long userId) throws Exception;

    public List<Order> getRestaurantOrders(Long restaurantId, String orderStatus) throws Exception;

    public Order findOrderById(Long orderId) throws Exception;
}
