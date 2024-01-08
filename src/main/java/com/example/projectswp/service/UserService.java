package com.example.projectswp.service;

import com.example.projectswp.model.user.CreateUser;
import com.example.projectswp.model.user.User;

import java.util.List;

public interface UserService {
    User getUser(String uid);
    User getUserById(int userId);
    List<User> getAllUser();
    int getUserId(String userUid);
    int getUserRoleByUid(String userUid);
    //String checkUser(CreateUser createUser);
    List<String> createUser(CreateUser user);
    boolean updateUser(User user, int userId);
}
