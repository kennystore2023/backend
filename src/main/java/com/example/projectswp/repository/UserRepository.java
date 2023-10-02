package com.example.projectswp.repository;

import com.example.projectswp.model.user.User;

import java.util.List;

public interface UserRepository {
    List<User> getAllUser() throws Exception;
    User getUserByUserId(int userId) throws Exception;
    User getUserByUserUid(String userUid) throws Exception;
    boolean createUser(User user) throws Exception;
    boolean updateUser(User user, int userId) throws Exception;
    boolean deleteUser(int[] userId) throws Exception;
    User getUserByUsername(String username) throws Exception;
    List<String> checkUser(User checkUser) throws Exception;
}
