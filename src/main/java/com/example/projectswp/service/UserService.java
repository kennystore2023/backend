package com.example.projectswp.service;

import com.example.projectswp.model.Address;
import com.example.projectswp.model.cart.Cart;
import com.example.projectswp.model.user.CreateUser;
import com.example.projectswp.model.user.User;

import java.util.List;

public interface UserService {
    User getUserById(int userId);
    List<User> getAllUser();
    int getUserId(String userUid);
    int getUserRoleByUid(String userUid);
    boolean createUser(CreateUser user);
    boolean updateUser(User user);
}
