package com.librarySys.service.impl;

import com.librarySys.dao.UserMapper;
import com.librarySys.pojo.Book;
import com.librarySys.pojo.Page;
import com.librarySys.pojo.User;
import com.librarySys.service.UserService;
import com.librarySys.utils.IdUtils;
import com.librarySys.utils.Util;
import org.apache.ibatis.jdbc.Null;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public User GetUserInfoById(String id) {
        return userMapper.selById(id);
    }

    @Override
    public User login(User user) {
        User u = userMapper.selByName(user.getUsername());
        if (u != null) {
            if (u.getPassword().equals(user.getPassword())) {
                return u;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    @Override
    public int register(User user) {
        //判断用户是否已存在
        User u = userMapper.selByName(user.getUsername());
        if (u != null) {
            return -1;
        }
        user.setId(IdUtils.getId());
        user.setPassword(user.getPassword());
        return userMapper.insUsers(user);
    }

    @Override
    public int updateInfo(HashMap<String, Object> map) {
        return userMapper.updUserInfo(map);
    }

    @Override
    public boolean adminUpdateOp(User user) {
        return userMapper.adminUpdateOp(user);
    }

    @Override
    public Page<User> findUserByPage(int page, int rows) {
        Page<User> pageBean = new Page<>();
        if (page <= 1) {
            page = 1;
        }
        int start;
        start = (page - 1) * 10;
        int totalCount = userMapper.findTotalCount();
        //计算总页数
        int totalPage = totalCount % rows == 0 ? totalCount / rows : totalCount / rows + 1;
        pageBean.setTotalPage(totalPage);
        List<User> list = userMapper.findUserByPage(start, rows);
        pageBean.setTotalCount(totalCount);
        pageBean.setCurrentPage(page);
        pageBean.setRows(rows);
        pageBean.setList(list);
        return pageBean;
    }

    @Override
    public boolean delById(String id) {
        return userMapper.delById(id);
    }
}
