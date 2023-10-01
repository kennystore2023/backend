package com.example.projectswp.service.impl;

import com.example.projectswp.model.user.User;
import com.example.projectswp.repository.UserRepository;
import com.example.projectswp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    UserRepository userRepository;

    @Override
    public User getUserById(int userId){
        try{
            return userRepository.getUserByUserId(userId);
        } catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> getAllUser() {
        try{
            return userRepository.getAllUser();
        } catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getUserId(String userUid){
        try{
            return userRepository.getUserByUserUid(userUid).getUserId();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getUserRoleByUid(String userUid){
        try{
            return userRepository.getUserByUserUid(userUid).getUserRole();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
