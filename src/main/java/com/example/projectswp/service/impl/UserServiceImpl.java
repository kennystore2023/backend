package com.example.projectswp.service.impl;

import com.example.projectswp.model.user.CreateUser;
import com.example.projectswp.model.user.User;
import com.example.projectswp.repository.AddressRepository;
import com.example.projectswp.repository.CartRepository;
import com.example.projectswp.repository.UserRepository;
import com.example.projectswp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    UserRepository userRepository;
    @Autowired
    AddressRepository addressRepository;
    @Autowired
    CartRepository cartRepository;

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

    @Override
    public boolean createUser(CreateUser newUser) {
        try{
            User user = new User(0, 0, newUser.getUserName(), newUser.getUserUid(), newUser.getEmail(), newUser.getPhoneNumber(), newUser.getNote());
            boolean createUser = userRepository.createUser(user);
            boolean createAddress = addressRepository.createAddress(newUser.getAddress());
            boolean createCart = cartRepository.createCart(newUser.getCart());
            if(createUser && createAddress && createCart) return true;
            else return false;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean updateUser(User user) {
        try{
            return userRepository.updateUser(user);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
