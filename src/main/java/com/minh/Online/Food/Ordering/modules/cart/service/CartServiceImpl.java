package com.minh.Online.Food.Ordering.modules.cart.service;

import com.minh.Online.Food.Ordering.modules.cart.dto.AddCartItemRequest;
import com.minh.Online.Food.Ordering.modules.cart.model.Cart;
import com.minh.Online.Food.Ordering.modules.cart.model.CartItem;
import com.minh.Online.Food.Ordering.modules.cart.repository.CartItemRepository;
import com.minh.Online.Food.Ordering.modules.cart.repository.CartRepository;
import com.minh.Online.Food.Ordering.modules.food.Food;
import com.minh.Online.Food.Ordering.modules.user.User;
import com.minh.Online.Food.Ordering.modules.food.service.FoodService;
import com.minh.Online.Food.Ordering.modules.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private FoodService foodService;

    @Override
    public CartItem addItemToCart(AddCartItemRequest req, User user) throws Exception {
        if (user == null) {
            throw new Exception("User not found");
        }

        Food food = foodService.findFoodById(req.getFoodId());

        Cart cart = cartRepository.findByCustomerId(user.getId());

        for (CartItem cartItem : cart.getItems()){
            if (cartItem.getFood().equals(food)){
                int newQuantity = cartItem.getQuantity() + req.getQuantity();
                return updateCartItemQuantity(cartItem.getId(), newQuantity);
            }
        }

        CartItem newCartItem = new CartItem();
        newCartItem.setFood(food);
        newCartItem.setQuantity(req.getQuantity());
        newCartItem.setCart(cart);
        newCartItem.setIngredients(req.getIngredients());
        newCartItem.setTotalPrice(req.getQuantity() * food.getPrice());

        CartItem savedCart = cartItemRepository.save(newCartItem);
        cart.getItems().add(savedCart);

        return savedCart;
    }

    @Override
    public CartItem updateCartItemQuantity(Long cartItemId, int quantity) throws Exception {
        Optional<CartItem> optCartItem = cartItemRepository.findById(cartItemId);

        if (optCartItem.isEmpty()){
            throw new Exception("CartItem not found");
        }

        CartItem cartItemToUpdate = optCartItem.get();
        cartItemToUpdate.setQuantity(quantity);
        cartItemToUpdate.setTotalPrice(cartItemToUpdate.getFood().getPrice() * quantity);
        return cartItemRepository.save(cartItemToUpdate);
    }

    @Override
    public Cart removeItemFromCart(Long cartItemId, User user) throws Exception {
        if (user == null) {
            throw new Exception("User not found");
        }

        Cart cart = cartRepository.findByCustomerId(user.getId());

        Optional<CartItem> optCartItem = cartItemRepository.findById(cartItemId);

        if (optCartItem.isEmpty()){
            throw new Exception("CartItem not found");
        }

        CartItem cartItemToRemove = optCartItem.get();
        cart.getItems().remove(cartItemToRemove);

        return cartRepository.save(cart);
    }

    @Override
    public Long calculateCartTotal(Cart cart) throws Exception {
        Long total = 0L;
        for (CartItem cartItem : cart.getItems()) {
            total += cartItem.getFood().getPrice() * cartItem.getQuantity();
        }
        return total;
    }

    @Override
    public Cart findCartById(Long cartId) throws Exception {
        Optional<Cart> optionalCart = cartRepository.findById(cartId);

        if (optionalCart.isEmpty()){
            throw new Exception("CartItem not found with id" + cartId);
        }

        return optionalCart.get();
    }

    @Override
    public Cart findCartByUserId(Long userId) throws Exception {
        Cart cart = cartRepository.findByCustomerId(userId);

        cart.setTotal(calculateCartTotal(cart));

        return  cart;
    }

    @Override
    public Cart clearCart(Long userId) throws Exception {

        Cart cart = findCartByUserId(userId);

        cart.getItems().clear();

        return cartRepository.save(cart);
    }
}
