package com.example.projectswp.model.cart;

import com.example.projectswp.model.book.BookAndCartItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartAndCartItemAndBook extends Cart{
    private Cart cart;
    private List<BookAndCartItem> bookAndCartItemList;
}
