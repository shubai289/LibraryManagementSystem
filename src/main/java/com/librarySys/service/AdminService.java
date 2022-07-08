package com.librarySys.service;

import com.librarySys.pojo.Admin;

public interface AdminService {

    Admin adminLogin(Admin admin);

    Admin adminInsert(Admin admin);
}
