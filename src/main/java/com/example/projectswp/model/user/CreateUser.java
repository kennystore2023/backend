package com.example.projectswp.model.user;

import com.example.projectswp.model.cart.Cart;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateUser extends  User{
    private Cart cart;
}
