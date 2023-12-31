package com.example.projectswp.model.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private int userId;
    private int userRole;
    private String userName;
    private String userUid;
    private String email;
    private String phoneNumber;
    private String address;
    private String note;
}
