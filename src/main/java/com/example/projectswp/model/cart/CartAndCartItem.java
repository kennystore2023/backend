package com.example.projectswp.model.cart;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartAndCartItem extends Cart{
    private Cart cart;
    private List<CartItems> CartItems;
}
