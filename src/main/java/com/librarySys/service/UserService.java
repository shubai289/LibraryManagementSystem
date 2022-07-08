package com.librarySys.service;

import com.librarySys.pojo.Book;
import com.librarySys.pojo.Page;
import com.librarySys.pojo.User;

import java.util.HashMap;

public interface UserService {
    User GetUserInfoById(String id);
    /**
     * 登录功能
     */
    User login(User user);

    /**
     * 账户注册功能
     */
    int register(User user);

    /**
     * 修改个人信息功能
     */
    int updateInfo(HashMap<String, Object> map);

    /**
     * 修改个人信息功能
     */
    boolean adminUpdateOp(User user);

    /**
     * 获取用户列表
     */
    Page<User> findUserByPage(int page, int rows);
    /**
     * 删除用户
     */
    boolean delById(String id);
}
