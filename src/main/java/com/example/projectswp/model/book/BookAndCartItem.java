package com.example.projectswp.model.book;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BookAndCartItem {
    private int cartItemId;
    private int quantity;
    private Book book;
}
