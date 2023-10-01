package com.example.projectswp.model.genre;

import com.example.projectswp.model.book.Book;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GenreAndBook extends Genre {
    private List<Book> bookList;
}
