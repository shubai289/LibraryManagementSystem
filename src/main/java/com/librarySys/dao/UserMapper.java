package com.librarySys.dao;

import com.librarySys.pojo.User;
import org.apache.ibatis.annotations.Param;
import java.util.HashMap;
import java.util.List;

public interface UserMapper {
    boolean delById(@Param("id")String id);

    User selById(@Param("id")String id);

    User selByName(@Param("username") String name);

    int insUsers(User user);

    boolean adminUpdateOp(User user);

    int updUserInfo(HashMap<String, Object> map);

    int findTotalCount();

    List<User> findUserByPage(@Param("start") int start, @Param("rows")int rows);
}
