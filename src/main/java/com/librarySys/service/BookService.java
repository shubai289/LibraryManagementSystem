package com.librarySys.service;

import com.librarySys.pojo.Book;
import com.librarySys.pojo.Page;

import java.util.List;

public interface BookService {
    String getBookNameById(String book_id);

    boolean insertNewBook(Book book);

    boolean updateBookInfo(Book book);

    List<Book> show();

    List<Book> selBookByPageSearch(String entry, int start, int rows);

    int SearchBookforCount(String entry);

    Book selBookById(int id);

    Page<Book> findBookByPage(int page, int rows);

    boolean delById(Integer id);
}
