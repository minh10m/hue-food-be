package com.minh.Online.Food.Ordering.service;

import com.minh.Online.Food.Ordering.model.Cart;
import com.minh.Online.Food.Ordering.model.CartItem;
import com.minh.Online.Food.Ordering.model.Food;
import com.minh.Online.Food.Ordering.model.User;
import com.minh.Online.Food.Ordering.repository.CartItemRepository;
import com.minh.Online.Food.Ordering.repository.CartRepository;
import com.minh.Online.Food.Ordering.repository.FoodRepository;
import com.minh.Online.Food.Ordering.request.AddCartItemRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartServiceImpl implements CartService{

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private FoodService foodService;

    @Override
    public CartItem addItemToCart(AddCartItemRequest req, String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);

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
    public Cart removeItemFromCart(Long cartItemId, String jwt) throws Exception {

        User user = userService.findUserByJwtToken(jwt);

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
