package com.example.projectswp.repository.impl;

import com.example.projectswp.dbConnection.DBUtils;
import com.example.projectswp.model.user.User;
import com.example.projectswp.repository.UserRepository;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
@Repository
public class UserRepositoryImpl implements UserRepository {
    private List<User> getMany(String sql){
        List<User> userList = new ArrayList<>();
        try {
            Connection cn = DBUtils.makeConnection();
            if (cn != null) {
                PreparedStatement pst = cn.prepareStatement(sql);
                ResultSet table = pst.executeQuery();
                if (table != null) {
                    while (table.next()) {
                        User user = new User();
                        user.setUserId(table.getInt("userId"));
                        user.setUserRole(table.getInt("userRole"));
                        user.setUserName(table.getString("userName"));
                        user.setUserUid(table.getString("userUid"));
                        user.setEmail(table.getString("email"));
                        user.setPhoneNumber(table.getString("phoneNumber"));
                        user.setAddress(table.getString("address"));
                        user.setNote(table.getString("note"));
                        userList.add(user);
                    }
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return userList;
    }

    private User getOne(String sql) throws Exception {
        User user = new User();
        try {
            Connection cn = DBUtils.makeConnection();
            if (cn != null) {
                PreparedStatement pst = cn.prepareStatement(sql);
                ResultSet table = pst.executeQuery();
                if (table != null) {
                    while (table.next()) {
                        user.setUserId(table.getInt("userId"));
                        user.setUserRole(table.getInt("userRole"));
                        user.setUserName(table.getString("userName"));
                        user.setUserUid(table.getString("userUid"));
                        user.setEmail(table.getString("email"));
                        user.setPhoneNumber(table.getString("phoneNumber"));
                        user.setAddress(table.getString("address"));
                        user.setNote(table.getString("note"));
                    }
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public List<User> getAllUser() throws Exception {
        String sql = "select * from dbo.Users";
        List<User> userList = getMany(sql);
        return userList;
    }

    @Override
    public User getUserByUserId(int userId) throws Exception {
        String sql = "select * from dbo.Users where userId = '" + userId + "'";
        User user = getOne(sql);
        return user;
    }

    @Override
    public User getUserByUserUid(String userUid) throws Exception {
        String sql = "select * from dbo.Users where userUid = '" + userUid + "'";
        User user = getOne(sql);
        return user;
    }

    @Override
    public boolean createUser(User user) throws Exception {
        try {
            Connection cn = DBUtils.makeConnection();
            if (cn != null) {
                String sql = "INSERT INTO Users (userRole, userName, userUid, email, phoneNumber, address, note)"
                        + "VALUES(0, ?, ?, ?, ?, ?, ?)";
                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setString(1, user.getUserName());
                pst.setString(2, user.getUserUid());
                pst.setString(3, user.getEmail());
                pst.setString(4, user.getPhoneNumber());
                pst.setString(5, user.getAddress());
                pst.setString(6, user.getNote());
                int row = pst.executeUpdate();
                if (row > 0) return true;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateUser(User user, int userId) throws Exception {
        try {
            Connection cn = DBUtils.makeConnection();
            if (cn != null) {
                String sql = "update Users set userName = ?, email = ?, phoneNumber = ?, address = ?, note = ? WHERE userId = ?";
                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setString(1, user.getUserName());
                pst.setString(2, user.getEmail());
                pst.setString(3, user.getPhoneNumber());
                pst.setString(4, user.getAddress());
                pst.setString(5, user.getNote());
                pst.setInt(6, userId);
                int row = pst.executeUpdate();
                if (row > 0) return true;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteUser(int[] userId) throws Exception {
        try {
            Connection cn = DBUtils.makeConnection();
            int count = 0;
            if (cn != null) {
                for (int i = 0; i < userId.length; i++) {
                    String sql = "Delete from Users where userId = ?";
                    PreparedStatement pst = cn.prepareStatement(sql);
                    pst.setInt(1, userId[i]);
                    int row = pst.executeUpdate();
                    if (row > 0) count++;
                }
                if (count > 0) return true;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public User getUserByUsername(String username) throws Exception {
        String sql = "select * from dbo.Users where userName = '" + username + "'";
        User user = getOne(sql);
        return user;
    }

    @Override
    public List<String> checkUser(User checkUser) throws Exception {
        List<String> error = new ArrayList<>();
        String sql;
        sql = "select * from dbo.Users where userUid = '" + checkUser.getUserUid() + "'";
        if (!getMany(sql).isEmpty()) error.add("userUid is duplicate");
        sql = "select * from dbo.Users where email = '" + checkUser.getEmail() + "'";
        if (!getMany(sql).isEmpty()) error.add("email is duplicate");
        return error;
    }
}
