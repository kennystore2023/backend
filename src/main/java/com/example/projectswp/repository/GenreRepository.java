package com.example.projectswp.repository;

import com.example.projectswp.dbConnection.DBUtils;
import com.example.projectswp.model.book.Book;
import com.example.projectswp.model.genre.Genre;
import com.example.projectswp.model.genre.GenreAndBook;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GenreRepository {
    public static List<Genre> getAllGenre() throws Exception {
        List<Genre> genreList = new ArrayList<>();
        try {
            Connection cn = DBUtils.makeConnection();
            if (cn != null) {
                String sql = "Select * from dbo.Genre";
                PreparedStatement pst = cn.prepareStatement(sql);
                ResultSet table = pst.executeQuery();
                if (table != null) {
                    while (table.next()) {
                        Genre genre = new Genre();
                        genre.setGenreId(table.getInt("genreId"));
                        genre.setGenreName(table.getString("genreName"));
                        genreList.add(genre);
                    }
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return genreList;
    }

    public static Genre getGenreById(int genreId) throws Exception {
        Genre genre = new Genre();
        try {
            Connection cn = DBUtils.makeConnection();
            if (cn != null) {
                String sql = "Select * from dbo.Genre where genreId = ? ";
                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setInt(1, genreId);
                ResultSet table = pst.executeQuery();
                if (table != null) {
                    while (table.next()) {
                        genre.setGenreId(table.getInt("genreId"));
                        genre.setGenreName(table.getString("genreName"));
                    }
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return genre;
    }

    //Create new Genre
    public static boolean createGenre(Genre genre) throws Exception {
        try {
            Connection cn = DBUtils.makeConnection();
            if (cn != null) {
                String sql = "INSERT INTO Genre (genreName) VALUES (?)";
                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setString(1, genre.getGenreName());
                int row = pst.executeUpdate();
                if (row > 0) return true;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    //Update Genre
    public static boolean updateGenre(Genre genre) throws Exception {
        try {
            Connection cn = DBUtils.makeConnection();
            if (cn != null) {
                String sql = "Update dbo.Genre Set genreName = ? WHERE genreId = ?";
                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setString(1, genre.getGenreName());
                pst.setInt(2, genre.getGenreId());
                int row = pst.executeUpdate();
                if (row > 0) return true;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    //Delete Genre
    public static boolean deleteGenre(int[] genreId) throws Exception {
        try {
            Connection cn = DBUtils.makeConnection();
            int count = 0;
            if (cn != null) {
                for (int i = 0; i < genreId.length; i++) {
                    String sql = "Delete from dbo.Genre where genreId = ?";
                    PreparedStatement pst = cn.prepareStatement(sql);
                    pst.setInt(1, genreId[i]);
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


    //Get both Genre and Book
    public static List<GenreAndBook> getGenreAndBook() throws Exception {
        List<GenreAndBook> genreAndBookList = new ArrayList<>();
        List<Genre> genreList = getAllGenre();
        try {
            for (int i = 0; i < genreList.size(); i++) {
                int genreId = genreList.get(i).getGenreId();
                String genreName = genreList.get(i).getGenreName();
                String sql = "select * from dbo.Genre where genreId = '" + genreId + "'";
                List<Book> bookList = new ArrayList<>();

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
                GenreAndBook genreAndBook = new GenreAndBook();
                genreAndBook.setGenreId(genreId);
                genreAndBook.setGenreName(genreName);
                genreAndBook.setBookList(bookList);
                genreAndBookList.add(genreAndBook);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return genreAndBookList;
    }
}
