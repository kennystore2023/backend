package com.example.projectswp.model.user;

import com.example.projectswp.model.cart.Cart;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateUser extends  User{
    private Cart cart;
}
