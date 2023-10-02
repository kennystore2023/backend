package com.example.projectswp.repository;

import com.example.projectswp.dbConnection.DBUtils;
import com.example.projectswp.model.book.Book;
import com.example.projectswp.model.book.BookAndCartItem;
import com.example.projectswp.model.cart.Cart;
import com.example.projectswp.model.cart.CartAndCartItem;
import com.example.projectswp.model.cart.CartAndCartItemAndBook;
import com.example.projectswp.model.cart.CartItems;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


@Repository
public class CartRepository {
    /*
    public  List<Cart> getAllCart() throws Exception {
        List<Cart> cartList = new ArrayList<>();
        try {
            Connection cn = DBUtils.makeConnection();
            if (cn != null) {
                String sql = "SELECT * FROM dbo.Cart";
                PreparedStatement pst = cn.prepareStatement(sql);
                ResultSet table = pst.executeQuery();
                if (table != null) {
                    while (table.next()) {
                        Cart cart = new Cart();
                        cart.setCartId(table.getInt("cartId"));
                        cart.setUserId(table.getInt("userId"));
                        cartList.add(cart);
                    }
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return cartList;
    }

    public  Cart getCartByCartId(int cartId) throws Exception {
        Cart cart = new Cart();
        try {
            Connection cn = DBUtils.makeConnection();
            if (cn != null) {
                String sql = "SELECT * FROM Cart WHERE cartId = ?";
                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setInt(1, cartId);
                ResultSet table = pst.executeQuery();
                if (table != null) {
                    while (table.next()) {
                        cart.setCartId(table.getInt("cartId"));
                        cart.setUserId(table.getInt("userId"));
                    }
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return cart;
    }

    public  Cart getCartByUserId(int userId) throws Exception {
        Cart cart = new Cart();
        try{
            Connection cn = DBUtils.makeConnection();
            if (cn != null) {
                String sql = "SELECT * FROM Cart WHERE userId = ?";
                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setInt(1, userId);
                ResultSet table = pst.executeQuery();
                if (table != null) {
                    while (table.next()) {
                        cart.setCartId(table.getInt("cartId"));
                        cart.setUserId(table.getInt("userId"));
                    }
                    return cart;
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public  Cart getCartByUserUid(String userUid) throws Exception {
        Cart cart = null;
        try {
            Connection cn = DBUtils.makeConnection();
            if (cn != null) {
                String sql = "Select * from Cart c Join Users u on c.userId = u.userId where u.userUid = ?";
                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setString(1, userUid);
                ResultSet table = pst.executeQuery();
                if (table.next()) {
                    cart = new Cart();
                    cart.setCartId(table.getInt("cartId"));
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return cart;
    }
    */

    //Create a new cart
    public  boolean createCart(Cart cart) throws Exception {
        try {
            Connection cn = DBUtils.makeConnection();
            if (cn != null) {
                String sql = "INSERT INTO Cart (userId) VALUES (?)";

                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setInt(1, cart.getUserId());

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

    /*
    //Delete Cart
    public  boolean deleteCart(int[] cartId) throws Exception {
        try {
            Connection cn = DBUtils.makeConnection();
            int count = 0;
            if (cn != null) {
                for (int i = 0; i < cartId.length; i++) {
                    String sql = "DELETE FROM Cart WHERE cartId = ?";
                    PreparedStatement pst = cn.prepareStatement(sql);
                    pst.setInt(1, cartId[i]);
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

    public  List<CartAndCartItem> getCartAndCartItem() throws Exception {
        List<Cart> cartList = getAllCart();
        List<CartAndCartItem> cartAndCartItemList = new ArrayList<>();
        for(int i = 0; i < cartList.size(); i++){
            List<CartItems> cartItemsList = getCartItemsByCartId(cartList.get(i).getCartId());
            CartAndCartItem cartAndCartItem = new CartAndCartItem();
            cartAndCartItem.setCart(cartList.get(i));
            cartAndCartItem.setCartItems(cartItemsList);
            cartAndCartItemList.add(cartAndCartItem);
        }
        return cartAndCartItemList;
    }

    public  List<BookAndCartItem> getProductAndCartItem(int cartId) throws Exception {
        List<BookAndCartItem> productAndCartItemList = new ArrayList<>();
        try {
            Connection cn = DBUtils.makeConnection();
            if (cn != null) {
                String sql = "select c.cartItemId, c.quantity, p.productId, p.productName, p.price, p.quantity as productQuantity, p.categoryId, p.description, p.status, p.image, p.dateCreate, p.dateUpdate " +
                        "from CartItems c " +
                        "left join Product p on p.productId = c.productId " +
                        "where c.cartId = ?";
                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setInt(1, cartId);
                ResultSet table = pst.executeQuery();
                if (table != null) {
                    while (table.next()) {
                        BookAndCartItem bookAndCartItem = new BookAndCartItem();
                        bookAndCartItem.setCartItemId(table.getInt("cartItemId"));
                        bookAndCartItem.setQuantity(table.getInt("quantity"));

                        Book book = new Book();
                        book.setBookId(table.getInt("productId"));
                        book.setBookName(table.getString("productName"));
                        book.setAuthor(table.getString("author"));
                        book.setPrice(table.getInt("price"));
                        book.setQuantity(table.getInt("productQuantity"));
                        book.setGenreId(table.getInt("categoryId"));
                        book.setStatus(table.getString("status"));
                        book.setDescription(table.getString("description"));
                        book.setImage(table.getString("image"));
                        book.setDateCreate(table.getDate("dateCreate"));
                        book.setDateUpdate(table.getDate("dateUpdate"));

                        bookAndCartItem.setBook(book);
                        productAndCartItemList.add(bookAndCartItem);
                    }
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return productAndCartItemList;
    }

    public  CartAndCartItemAndBook getCartBookByUserUid(String userUid) throws Exception {
        Cart cart = getCartByUserUid(userUid);
        CartAndCartItemAndBook cartAndCartItemAndProduct = new CartAndCartItemAndBook();
        List<BookAndCartItem> bookAndCartItemList= getProductAndCartItem(cart.getCartId());
        cartAndCartItemAndProduct.setCart(cart);
        cartAndCartItemAndProduct.setBookAndCartItemList(bookAndCartItemList);
        return new CartAndCartItemAndBook();
    }
    */
}


