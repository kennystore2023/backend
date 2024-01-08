package com.example.projectswp.repository;

import com.example.projectswp.model.cart.Cart;

import java.util.List;

public interface CartRepository {
    List<Cart> getAllCart() throws Exception;
    Cart getCartByCartId(int cartId) throws Exception;
    Cart getCartByUserId(int userId) throws Exception;
    Cart getCartByUserUid(String userUid) throws Exception;
    boolean createCart(Cart cart) throws Exception;
    boolean deleteCart(int[] cartId) throws Exception;
}
