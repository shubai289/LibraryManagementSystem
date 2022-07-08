package com.librarySys.dao;

import com.librarySys.pojo.Admin;
import org.apache.ibatis.annotations.Param;

public interface AdminMapper {
    Admin getAdminByName(@Param("adminName")String adminName);

    int CreateAdmin(Admin admin);
}
