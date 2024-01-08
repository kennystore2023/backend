package com.example.projectswp.service.impl;

import com.example.projectswp.model.cart.Cart;
import com.example.projectswp.model.user.CreateUser;
import com.example.projectswp.model.user.User;
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
    CartRepository cartRepository;

    @Override
    public User getUser(String uid) {
        try {
            return userRepository.getUserByUserUid(uid);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

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
    public List<String> createUser(CreateUser newUser) {
        try{
                if(createUser) {
                    int userId = userRepository.getUserByUserUid(newUser.getUserUid()).getUserId();
                    System.out.print(userId);
                    Cart cart = new Cart(0, userId);
                    boolean createCart = cartRepository.createCart(cart);
                    if (createCart) {
                        check.add("Create successful");
                        return check;
                    } else {
                        int[] id = {userId};
                        userRepository.deleteUser(id);
                        check.add("Create fail");
                        return check;
                    }
                }
                else {
                    check.add("Create fail");
                    return check;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean updateUser(User user, int userId) {
        try{
            return userRepository.updateUser(user, userId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
