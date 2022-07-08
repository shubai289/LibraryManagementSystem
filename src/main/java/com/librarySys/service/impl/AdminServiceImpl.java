package com.librarySys.service.impl;

import com.librarySys.dao.AdminMapper;
import com.librarySys.pojo.Admin;
import com.librarySys.service.AdminService;
import com.librarySys.utils.Util;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

@Service
public class AdminServiceImpl implements AdminService {
    @Resource
    private AdminMapper adminMapper;

    @Override
    public Admin adminLogin(Admin admin) {
        Admin ad = adminMapper.getAdminByName(admin.getAdminName());


        if (ad != null){
            if (ad.getPassword().equals(Util.encrypt(admin.getPassword()))){
                return ad;
            }else {
                return null;
            }
        } else {
            return null;
        }
    }

    @Override
    public Admin adminInsert(Admin admin) {
        adminMapper.CreateAdmin(admin);
        return null;
    }
}
