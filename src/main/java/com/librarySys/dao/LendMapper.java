package com.librarySys.dao;

import com.librarySys.pojo.Book;
import com.librarySys.pojo.Lend;
import com.librarySys.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface LendMapper {

    List<Lend> showAllLendList();

    Lend selById(@Param("id") int id);

    List<Lend> showUserLendList(User user);

    List<Lend> showBookLendList(Map<String, Integer> map);

    int insLendList(HashMap<String, Object> map);

    int updLendList(HashMap<String, Object> map);

}
