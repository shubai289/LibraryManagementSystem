package com.librarySys.controller;

import com.librarySys.pojo.Admin;
import com.librarySys.service.impl.AdminServiceImpl;
import com.librarySys.utils.Util;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
public class AdminController {
    @Resource
    AdminServiceImpl adminServiceImpl;

    @RequestMapping("/toIndex")
    public ModelAndView toIndex(){
        ModelAndView mav = new ModelAndView("index");
        return mav;
    }

    @RequestMapping("/toAdminLogin")
    public String toAdminLogin(){
        return "admin/adminLogin";
    }

    @RequestMapping("/toServer")
    @ResponseBody
    public Map<String,String> sendString(Admin admin){    //admin是与页面参数对应的JavaBean
        //map集合用来存放返回值
        Map<String,String> map = new HashMap<>();
        if(admin != null) {
            map.put("result","登录成功");
        }else {
            map.put("result","登录失败");
        }
        return map;
    }

    @RequestMapping("/adminLogout")
    public String logout(HttpSession session) {
        //将session设置为失效，浏览器自动创建新的session
        session.invalidate();
        return "index";
    }
    @RequestMapping("/adminLogin")
    public ModelAndView adminLogin(Admin admin, HttpSession session) {
        System.out.println(Util.encrypt(admin.getPassword()));
        //21232F297A57A5A743894A0E4A801FC3
        Admin ad = adminServiceImpl.adminLogin(admin);
        ModelAndView mav;
        if (ad != null) {
            mav = new ModelAndView("redirect:/toUserList");
            session.setAttribute("admin", ad);
            System.out.println(ad);
        } else {
            mav = new ModelAndView("admin/adminLogin");
            mav.addObject("mav", "账号或账号密码不正确！");
        }
        return mav;
    }
}
