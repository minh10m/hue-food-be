package com.minh.Online.Food.Ordering.modules.cart.service;

import com.minh.Online.Food.Ordering.modules.cart.dto.AddCartItemRequest;
import com.minh.Online.Food.Ordering.modules.cart.model.Cart;
import com.minh.Online.Food.Ordering.modules.cart.model.CartItem;
import com.minh.Online.Food.Ordering.modules.user.User;

public interface CartService {

    public CartItem addItemToCart(AddCartItemRequest req, User user) throws Exception;

    public CartItem updateCartItemQuantity(Long cartItemId, int quantity) throws Exception;

    public Cart removeItemFromCart(Long cartItemId, User user) throws Exception;

    public Long calculateCartTotal(Cart cart) throws Exception;

    public Cart findCartById(Long cartId) throws Exception;

    public Cart findCartByUserId(Long userId) throws Exception;

    public Cart clearCart(Long userId) throws Exception;
}
