package com.example.projectswp.service;

import com.example.projectswp.model.User;

import java.util.List;

public interface UserService {
    User getUserById(int userId);
    List<User> getAllUser();
    int getUserId(String userUid);
    int getUserRoleByUid(String userUid);
}
