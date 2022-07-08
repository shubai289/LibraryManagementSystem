package com.librarySys.service;

import com.librarySys.pojo.Book;
import com.librarySys.pojo.Lend;
import com.librarySys.pojo.User;
import com.librarySys.pojo.Lend;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface LendService {

    List<Lend> showLendList();

    List<Lend> showUserLendList(User user);

    List<Lend> showBookLendList(Map<String, Integer> map);

    int insLendList(HashMap<String, Object> map);

    int returnBook(int lend_id);
}
