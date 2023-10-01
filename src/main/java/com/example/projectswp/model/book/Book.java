package com.example.projectswp.model.book;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Book {
    private int bookId;
    private String bookName;
    private String author;
    private int price;
    private int quantity;
    private int genreId;
    private String status;
    private String description;
    private String image;
    private Date dateCreate;
    private Date dateUpdate;
}
