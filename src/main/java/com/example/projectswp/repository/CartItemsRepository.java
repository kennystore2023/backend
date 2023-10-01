package com.example.projectswp.repository;

import com.example.projectswp.dbConnection.DBUtils;
import com.example.projectswp.model.cart.CartItems;
import com.example.projectswp.model.book.Book;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
@Repository
public class CartItemsRepository {
    public static List<CartItems> getAllCartItems() throws Exception {
        List<CartItems> cartItemsList = new ArrayList<>();
        try {
            Connection cn = DBUtils.makeConnection();
            if (cn != null) {
                String sql = "SELECT * FROM CartItems";
                PreparedStatement pst = cn.prepareStatement(sql);
                ResultSet table = pst.executeQuery();
                if (table != null) {
                    while (table.next()) {
                        CartItems cartItems = new CartItems();
                        cartItems.setCartItemId(table.getInt("cartItemId"));
                        cartItems.setCartId(table.getInt("cartId"));
                        cartItems.setBookId(table.getInt("productId"));
                        cartItems.setQuantity(table.getInt("quantity"));
                        cartItemsList.add(cartItems);
                    }
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return cartItemsList;
    }

    public static CartItems getCartItemsById(int cartItemId) throws Exception {
        CartItems cartItems = new CartItems();
        try {
            Connection cn = DBUtils.makeConnection();
            if (cn != null) {
                String sql = "SELECT * FROM CartItems WHERE cartItemId = ?";
                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setInt(1, cartItemId);
                ResultSet table = pst.executeQuery();
                if (table != null) {
                    while (table.next()) {
                        cartItems.setCartItemId(table.getInt("cartItemId"));
                        cartItems.setCartId(table.getInt("cartId"));
                        cartItems.setBookId(table.getInt("bookId"));
                        cartItems.setQuantity(table.getInt("quantity"));
                    }
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return cartItems;
    }

    public static List<CartItems> getCartItemsByCartId(int cartId) throws Exception {
        List<CartItems> cartItemsList = new ArrayList<>();
        try {
            Connection cn = DBUtils.makeConnection();
            if (cn != null) {
                String sql = "SELECT * FROM CartItems WHERE cartId = ?";
                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setInt(1, cartId);
                ResultSet table = pst.executeQuery();
                if (table != null) {
                    while (table.next()) {
                        CartItems cartItems = new CartItems();
                        cartItems.setCartItemId(table.getInt("cartItemId"));
                        cartItems.setCartId(table.getInt("cartId"));
                        cartItems.setBookId(table.getInt("bookId"));
                        cartItems.setQuantity(table.getInt("quantity"));
                        cartItemsList.add(cartItems);
                    }
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return cartItemsList;
    }

    //Create cartItems
    public static boolean createCartItems(CartItems cartItem) throws Exception {
        try {
            Connection cn = DBUtils.makeConnection();
            if (cn != null) {
                Book book = BookRepository.getBookById(cartItem.getBookId());
                if (book.getQuantity() >= cartItem.getQuantity() || book.getQuantity() != 0) {
                    CartItems newCartItem = getCartItemsByCartIdAndBookId(cartItem.getCartId(), cartItem.getBookId());
                    if (newCartItem.getCartId() != 0) {
                        newCartItem.setQuantity(newCartItem.getQuantity() + 1);
                        updateCartItems(newCartItem);
                        return true;
                    } else {
                        String sql = "INSERT INTO CartItems (cartId, bookId, quantity) VALUES (?, ?, ?)";
                        PreparedStatement pst = cn.prepareStatement(sql);
                        pst.setInt(1, cartItem.getCartId());
                        pst.setInt(2, cartItem.getBookId());
                        pst.setInt(3, cartItem.getQuantity());
                        int row = pst.executeUpdate();
                        if (row > 0) return true;
                    }
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    //Update cartItems
    public static boolean updateCartItems(CartItems cartItem) throws Exception {
        try {
            Connection cn = DBUtils.makeConnection();
            if (cn != null) {
                String sql = "UPDATE CartItems SET quantity = ? WHERE cartItemId = ?";
                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setInt(1, cartItem.getQuantity());
                pst.setInt(2, cartItem.getCartItemId());
                int row = pst.executeUpdate();
                if (row > 0) {
                    return true;
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    //Delete cartItems
    public static boolean deleteCartItem(int[] cartItemId) throws Exception {
        try {
            Connection cn = DBUtils.makeConnection();
            int count = 0;
            if (cn != null) {
                for (int i = 0; i < cartItemId.length; i++) {
                    String sql = "DELETE FROM CartItems WHERE cartItemId = ?";
                    PreparedStatement pst = cn.prepareStatement(sql);
                    pst.setInt(1, cartItemId[i]);
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

    public static int countCartItems(int cartId) throws Exception {
        int count = 0;
        try {
            Connection cn = DBUtils.makeConnection();
            if (cn != null) {
                String sql = "select cartId, count(*) as count from CartItems where cartId = ? group by cartId";
                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setInt(1, cartId);
                ResultSet table = pst.executeQuery();
                if (table != null) {
                    table.next();
                    count = table.getInt("count");
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return count;
    }

    public static CartItems getCartItemsByCartIdAndBookId(int cartId, int bookId) throws Exception {
        CartItems cartItems = new CartItems();
        try {
            Connection cn = DBUtils.makeConnection();
            if (cn != null) {
                String sql = "SELECT * FROM CartItems WHERE cartId = ? and bookId = ?";
                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setInt(1, cartId);
                pst.setInt(2, bookId);
                ResultSet table = pst.executeQuery();
                if (table != null && table.next()) {
                    cartItems.setCartItemId(table.getInt("cartItemId"));
                    cartItems.setCartId(table.getInt("cartId"));
                    cartItems.setBookId(table.getInt("bookId"));
                    cartItems.setQuantity(table.getInt("quantity"));
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return cartItems;
    }
}
