package com.example.projectswp.model.cart;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartItems {
    private int cartItemId;
    private int cartId;
    private int bookId;
    private int quantity;
}
