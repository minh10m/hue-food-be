package com.minh.Online.Food.Ordering.modules.order.service;

import com.minh.Online.Food.Ordering.modules.address.Address;
import com.minh.Online.Food.Ordering.modules.address.AddressRepository;
import com.minh.Online.Food.Ordering.modules.cart.model.Cart;
import com.minh.Online.Food.Ordering.modules.cart.model.CartItem;
import com.minh.Online.Food.Ordering.modules.cart.service.CartService;
import com.minh.Online.Food.Ordering.modules.order.repository.OrderItemRepository;
import com.minh.Online.Food.Ordering.modules.order.repository.OrderRepository;
import com.minh.Online.Food.Ordering.modules.order.dto.OrderRequest;
import com.minh.Online.Food.Ordering.modules.order.model.Order;
import com.minh.Online.Food.Ordering.modules.order.model.OrderItem;
import com.minh.Online.Food.Ordering.modules.restaurant.Restaurant;
import com.minh.Online.Food.Ordering.modules.user.User;
import com.minh.Online.Food.Ordering.modules.user.UserRepository;
import com.minh.Online.Food.Ordering.modules.restaurant.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private CartService cartService;

    @Override
    @Transactional
    public Long createOrder(OrderRequest req, User user) throws Exception {
        Address shipAddress = new Address();
        shipAddress.setStreet(req.getDeliveryAddress().getStreet());
        shipAddress.setCity(req.getDeliveryAddress().getCity());
        shipAddress.setPhone(req.getDeliveryAddress().getPhone());
        shipAddress.setUser(user);
        Address savedAddress = addressRepository.save(shipAddress);

        if (!user.getAddresses().contains(savedAddress)) {
            user.getAddresses().add(savedAddress);
            userRepository.save(user);
        }

        Restaurant restaurant;
        try {
            restaurant = restaurantService.findRestaurantById(req.getRestaurantId());
        } catch (Exception e) {
            throw new Exception("Restaurant not found with id: " + req.getRestaurantId());
        }

        Order order = new Order();
        order.setRestaurant(restaurant);
        order.setCustomer(user);
        order.setDeliveryAddress(savedAddress);
        order.setCreateAt(java.util.Date.from(java.time.LocalDateTime.now().atZone(java.time.ZoneId.systemDefault()).toInstant()));
        order.setOrderStatus("PENDING");

        Cart cart = cartService.findCartByUserId(user.getId());

        List<OrderItem> items = new ArrayList<>();
        for (CartItem ci : cart.getItems()) {
            OrderItem oi = new OrderItem();
            oi.setQuantity(ci.getQuantity());
            oi.setFood(ci.getFood());
            oi.setIngredients(ci.getIngredients());
            oi.setTotalPrice(ci.getTotalPrice());
            items.add(orderItemRepository.save(oi));
        }

        Long total = cartService.calculateCartTotal(cart);
        order.setItems(items);
        order.setTotalPrice(total);
        order.setTotalAmount(total);  // Set both fields to the same value to prevent NPE
        Order saved = orderRepository.save(order);
        restaurant.getOrders().add(saved);

        return saved.getId();
    }


    @Override
    public Order updateOrder(Long orderId, String orderStatus) throws Exception {

        Order order = findOrderById(orderId);

        if (orderStatus.equals("OUT_FOR_DELIVERY")
                || orderStatus.equals("DELIVERED")
                || orderStatus.equals("COMPLETED")
                ||orderStatus.equals("PENDING")){
            order.setOrderStatus(orderStatus);
            return orderRepository.save(order);
        }

        throw new Exception("Invalid order status");
    }

    @Override
    public void cancelOrder(Long orderId) throws Exception {
        Order order = findOrderById(orderId);
        orderRepository.deleteById(orderId);

    }

    @Override
    public List<Order> getUserOrders(Long userId) throws Exception {
        return orderRepository.findByCustomerId(userId);
    }

    @Override
    public List<Order> getRestaurantOrders(Long restaurantId, String orderStatus) throws Exception {
        List<Order> orders = orderRepository.findByRestaurantId(restaurantId);
        if (orderStatus != null){
            orders = orders.stream().filter(order ->
                    order.getOrderStatus().equals(orderStatus)).collect(Collectors.toList());
        }
        return orders;
    }

    @Override
    public Order findOrderById(Long orderId) throws Exception {

        Optional<Order> optionalOrder = orderRepository.findById(orderId);

        if (optionalOrder.isEmpty()){
            throw new Exception("order not found");
        }
        return optionalOrder.get();
    }
}
