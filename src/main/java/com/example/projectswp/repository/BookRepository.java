package com.example.projectswp.repository;

import com.example.projectswp.dbConnection.DBUtils;
import com.example.projectswp.model.book.Book;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BookRepository {
    public static List<Book> getBook(String sql) throws Exception {
        List<Book> bookList = new ArrayList<>();
        try {
            Connection cn = DBUtils.makeConnection();
            if (cn != null) {
                PreparedStatement pst = cn.prepareStatement(sql);
                ResultSet table = pst.executeQuery();
                if (table != null) {
                    while (table.next()) {
                        Book book = new Book();
                        book.setBookId(table.getInt("bookId"));
                        book.setBookName(table.getString("bookName"));
                        book.setAuthor(table.getString("author"));
                        book.setPrice(table.getInt("price"));
                        book.setQuantity(table.getInt("quantity"));
                        book.setGenreId(table.getInt("genreId"));
                        book.setStatus(table.getString("status"));
                        book.setDescription(table.getString("description"));
                        book.setImage(table.getString("image"));
                        book.setDateCreate(table.getDate("dateCreate"));
                        book.setDateUpdate(table.getDate("dateUpdate"));
                        bookList.add(book);
                    }
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return bookList;
    }

    //Get all book
    public static List<Book> getAllBook() throws Exception {
        List<Book> bookList = getBook("select * from dbo.Book");
        return bookList;
    }

    //Chuyển tiếng Việt ko dấu
    public static String unAccent(String s) {
        String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(temp).replaceAll("").replaceAll("Đ", "D").replace("đ", "d");
    }

    //Search book by it's name
    public static List<Book> searchByName(String searchValue) throws Exception {
        /*String sql = "select * from dbo.Product where productName like N'%" + searchValue + "%'";
        List<Book> bookList  = getBook(sql);
        return bookList;*/

        //Search chữ ko dấu
        List<Book> bookList = getBook("select * from dbo.Book");
        Book book = new Book();
        List<Book> result = new ArrayList<>();
        for (int i = 0; i < bookList.size(); i++){
            book = bookList.get(i);
            if(unAccent(book.getBookName()).toLowerCase().contains(unAccent(searchValue).toLowerCase())){
                result.add(book);
            }
        }
        return result;
    }

    //Filter book by id
    public static Book getBookById(int bookId) throws Exception {
        String sql = "select * from dbo.Book where bookId = '" + bookId + "'";
        Book book = getBook(sql).get(0);
        return book;
    }

    public static int getGenreId(String genreName) throws Exception {
        List<Book> bookList = new ArrayList<>();
        int genreId = 0;
        try {
            Connection cn = DBUtils.makeConnection();
            if (cn != null) {
                String sql = "Select * from dbo.Genre where genreName = ?";
                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setString(1, genreName);
                ResultSet table = pst.executeQuery();
                if (table != null) {
                    while (table.next()) {
                        genreId = table.getInt("genreId");
                    }
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return genreId;
    }

    //Multiple filter
    public static List<Book> multiFilter(String genreName, String price, String status) throws Exception {
        int genreId = getGenreId(genreName);

        String sql = "Select * from dbo.Book where";

        if (genreName!=null) sql = sql + " genreId = " + genreId + " and ";

        if(price!=null) {
            Pattern pattern = Pattern.compile("\\d+");
            Matcher matcher = pattern.matcher(price);
            int from = -1, to = -1;
            if (matcher.find()) {
                from = Integer.parseInt(matcher.group());
                if (matcher.find()) {
                    to = Integer.parseInt(matcher.group());
                }
            }
            if(to != -1){
                sql = sql + " price >= " + from + " and price < "+ to + " and ";
            } else {
                sql = sql + " price >= " + from + " and ";
            }

        }

        if (status!=null) sql = sql + " status = N'" + status + "'";
        int lenght = sql.length();
        if (sql.substring(lenght-5,lenght-1).trim().equals("and")) sql = sql.substring(0, lenght-5);
        List<Book> bookList = getBook(sql);
        return bookList;
    }

    //Filter product by it's genre
    //Đã tích hợp trong multiFilter
    public static List<Book> filterByGenreId(int genreId) throws Exception {
        String sql = "select * from dbo.Book where genreId = " + genreId;
        List<Book> bookList = getBook(sql);
        return bookList;
    }

    //Filter book by price from x to y
    //Đã tích hợp trong multiFilter
    public static List<Book> sortByPrice(int from, int to) throws Exception {
        String sql = "Select * from dbo.Book where price >= " + from + " and price <= " + to;
        List<Book> bookList = getBook(sql);
        return bookList;
    }

    //Filter book by status
    //Đã tích hợp trong multiFilter
    public static List<Book> filterByStatus(String status) throws Exception {
        String sql = "Select * from dbo.Book where status = N'" + status + "'";
        List<Book> bookList = getBook(sql);
        return bookList;
    }

    //Add new book
    public static boolean createBook(Book book) throws Exception {
        String dateCreate = DBUtils.getCurrentDate();
        try {
            Connection cn = DBUtils.makeConnection();
            if (cn != null) {
                String sql = "SET ANSI_WARNINGS OFF;" +
                        "INSERT INTO Book(bookName, author, price, quantity, genreId, status, description, image, dateCreate) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?) " +
                        "SET ANSI_WARNINGS ON";

                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setString(1, book.getBookName());
                pst.setString(2, book.getAuthor());
                pst.setInt(3, book.getPrice());
                pst.setInt(4, book.getQuantity());
                pst.setInt(5, book.getGenreId());
                pst.setString(6, "mới");
                pst.setString(7, book.getDescription());
                pst.setString(8, book.getImage());
                pst.setString(9, dateCreate);
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

    //Delete existing book by id
    public static boolean deleteBook(int[] bookId) throws Exception {
        String sql = "Delete from dbo.Book where bookId = ?";
        try {
            Connection cn = DBUtils.makeConnection();
            int count = 0;
            if (cn != null) {
                for (int i = 0; i < bookId.length; i++) {
                    PreparedStatement pst = cn.prepareStatement(sql);
                    pst.setInt(1, bookId[i]);
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

    //Delete book by changing book status to "xóa"
    public static boolean deleteBookByChangingStatus(int[] bookId) throws Exception {
        String sql = "Update dbo.Book set status = N'xóa' where bookId = ?";
        try {
            Connection cn = DBUtils.makeConnection();
            int count = 0;
            if (cn != null) {
                for (int i = 0; i < bookId.length; i++) {
                    PreparedStatement pst = cn.prepareStatement(sql);
                    pst.setInt(1, bookId[i]);
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

    //Update existing book by id
    public static boolean updateBook(Book book) throws Exception {
        String dateUpdate = DBUtils.getCurrentDate();
        try {
            String status = book.getStatus();
            if(book.getQuantity() == 0) status = "hết hàng";
            else if(status.equalsIgnoreCase("hết hàng")) status = "";
            Connection cn = DBUtils.makeConnection();
            if (cn != null) {
                String sql = "Update dbo.Book set bookName = ?, author = ?, price = ?, quantity = ?, genreId = ?, status = ?, description = ?, image = ?, dateUpdate = ? where productId = ?";
                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setString(1, book.getBookName());
                pst.setString(2, book.getAuthor());
                pst.setInt(3, book.getPrice());
                pst.setInt(4, book.getQuantity());
                pst.setInt(5, book.getGenreId());
                pst.setString(6, status);
                pst.setString(7, book.getDescription());
                pst.setString(8, book.getImage());
                pst.setString(9, dateUpdate);
                pst.setInt(10, book.getBookId());
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

    public static String[] getBookName() throws Exception {
        String[] bookName = null;
        try {
            List<Book> bookList = getBook("select * from dbo.Book");
            bookName = new String[bookList.size()];
            for (int i = 0; i < bookList.size(); i++){
                bookName[i] = bookList.get(i).getBookName();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return bookName;
    }
}
