package com.librarySys.dao;

import com.librarySys.pojo.Book;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BookMapper {
    boolean delById(@Param("id")Integer id);

    boolean insertNewBook(Book book);

    boolean updateBookInfo(Book book);

    List<Book> selAllBooks();

    List<Book> selBookByName(@Param("entry") String entry,@Param("start") int start,@Param("rows") int rows);

    int SearchBookforCount(@Param("entry") String entry);

    Book selBookById(@Param("id") int id);

    int updBookState(@Param("state") int state, @Param("book_id") int book_id);

    String getBookNameById(String book_id);

    int findTotalCount();

    List<Book> findBookByPage(@Param("start") int start, @Param("rows")int rows);

}
