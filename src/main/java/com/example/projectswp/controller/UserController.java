package com.example.projectswp.controller;

import com.example.projectswp.model.user.CreateUser;
import com.example.projectswp.model.user.User;
import com.example.projectswp.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class UserController {
    @Autowired
    UserServiceImpl userService;

    @GetMapping("/getUser")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<User>> getUser() {
        try {
            List<User> userList = userService.getAllUser();
            return userList.isEmpty() ? ResponseEntity.ok(userList) : ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/createUser")
    public ResponseEntity<Object> createUser(@RequestBody CreateUser createUser) {
        try {
            boolean result = userService.createUser(createUser);
            return result ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
        } catch (Exception e) {
            System.out.println(e.toString());
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/updateUser")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<List<User>> createUser(@RequestBody User user) {
        try {
            boolean result = userService.updateUser(user);
            return result ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /*@GetMapping("/user")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<User> getAccountToken() {
        int uid = Ultil.getUserId();
        User user = userService.getUserAccountById(uid);
        return userAccount != null ? ResponseEntity.ok(userAccount) : ResponseEntity.notFound().build();
    }*/
}
