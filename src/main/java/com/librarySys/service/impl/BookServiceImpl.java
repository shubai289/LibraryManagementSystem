package com.librarySys.service.impl;

import com.librarySys.dao.BookMapper;
import com.librarySys.pojo.Book;
import com.librarySys.pojo.Page;
import com.librarySys.pojo.User;
import com.librarySys.service.BookService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    @Resource
    private BookMapper bookMapper;

    @Override
    public String getBookNameById(String book_id) {
        return bookMapper.getBookNameById(book_id);
    }

    @Override
    public boolean insertNewBook(Book book) {
        //判断图书是否已存在
        if (bookMapper.selBookById(book.getId()) != null) {
            return false;
        }
        return bookMapper.insertNewBook(book);
    }

    @Override
    public boolean updateBookInfo(Book book) {
        return bookMapper.updateBookInfo(book);
    }

    @Override
    public List<Book> show() {
        return bookMapper.selAllBooks();
    }

    @Override
    public Page<Book> findBookByPage(int page, int rows){
        Page<Book> pageBean = new Page<>();
        if (page <= 1) {
            page = 1;
        }
        int start;
        start = (page - 1) * 10;
        int totalCount = bookMapper.findTotalCount();
        //计算总页数
        int totalPage = totalCount % rows == 0 ? totalCount / rows : totalCount / rows + 1;
        pageBean.setTotalPage(totalPage);
        List<Book> list = bookMapper.findBookByPage(start, rows);
        pageBean.setTotalCount(totalCount);
        pageBean.setCurrentPage(page);
        pageBean.setRows(rows);
        pageBean.setList(list);
        return pageBean;
    }

    @Override
    public List<Book> selBookByPageSearch(String entry, int start, int rows) {
        entry = "%" + entry + "%";
        return bookMapper.selBookByName(entry, start, rows);
    }

    @Override
    public int SearchBookforCount(String entry) {
        entry = "%" + entry + "%";
        return bookMapper.SearchBookforCount(entry);
    }

    @Override
    public Book selBookById(int id) {
        return bookMapper.selBookById(id);
    }

    @Override
    public boolean delById(Integer id) {
        return bookMapper.delById(id);
    }
}
