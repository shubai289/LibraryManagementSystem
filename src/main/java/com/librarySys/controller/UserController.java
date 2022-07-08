package com.librarySys.controller;

import com.librarySys.pojo.Book;
import com.librarySys.pojo.Page;
import com.librarySys.pojo.User;
import com.librarySys.service.impl.UserServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;

@Controller
public class UserController {
    @Resource
    private UserServiceImpl userServiceImpl;

    @RequestMapping("/toRegister")
    public String toRegister() {
        return "registerPage";
    }

    @RequestMapping("/userInfo")
    public String userInfo() {
        return "user_info";
    }

    @RequestMapping("/toUserOP")
    public String  toUserOP(HttpServletRequest request){
        return "admin/bs_userop";
    }

    @RequestMapping("/toUserList")
    public ModelAndView toUserList(HttpServletRequest request){
        String page = request.getParameter("page");
        String rows = request.getParameter("rows");
        if(null==page || "".equals(page)){
            page="1";
        }
        if(null==rows || "".equals(rows)){
            rows="10";
        }
        ModelAndView mav = new ModelAndView("admin/bs_userList");
        Page<User> list = userServiceImpl.findUserByPage(Integer.parseInt(page), Integer.parseInt(rows));
        mav.addObject("userList",list);
        return mav;
    }

    @RequestMapping("/changeInfo")
    public String changePwd() {
        return "changeInfo";
    }

    @RequestMapping("/updateInfo")
    public ModelAndView updPwd(User newInfo, HttpSession session) {
        System.out.println("update Info!");
        User user = (User) session.getAttribute("user");

        if (newInfo.getEmail().equals("")){
            newInfo.setEmail(user.getEmail());
        } else if (newInfo.getPhone().equals("")){
            newInfo.setPhone(user.getPhone());
        }

        HashMap<String, Object> map = new HashMap<>();
        map.put("id",user.getId());
        map.put("email",newInfo.getEmail());
        map.put("phone",newInfo.getPhone());

        int index = userServiceImpl.updateInfo(map);
        if (index > 0) {
            user = userServiceImpl.GetUserInfoById(user.getId());
            System.out.println(2);
            session.setAttribute("user", user);
            ModelAndView mav = new ModelAndView("user_info");
            mav.addObject("msg", "修改成功!");
            return mav;
        } else {
            System.out.println(3);
            ModelAndView mav = new ModelAndView("user_info");
            mav.addObject("msg", "修改失败");
            return mav;
        }
    }

    @RequestMapping("/login")
    public ModelAndView indexLogin(User users, HttpSession session) {
        User user = userServiceImpl.login(users);
        ModelAndView mav;
        mav = new ModelAndView("index");
        if (user != null) {
            mav.addObject("mav", "登录成功！");
            session.setAttribute("user", user);
            System.out.println(user);
        } else {
            mav.addObject("r_mav", "账号不存在或账号密码不对！");
        }
        return mav;
    }

    @RequestMapping("/bookList/login")
    public ModelAndView bookListLogin(User users, HttpSession session) {
        User user = userServiceImpl.login(users);
        ModelAndView mav;
        mav = new ModelAndView("redirect:/showAll");
        if (user != null) {
            mav.addObject("mav", "登录成功！");
            session.setAttribute("user", user);
            System.out.println(user);
        } else {
            mav.addObject("r_mav", "账号不存在或账号密码不对！");
        }
        return mav;
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        //将session设置为失效，浏览器自动创建新的session
        session.invalidate();
        return "index";
    }

    @RequestMapping("/register")
    public ModelAndView register(User user, HttpSession session) {
        int index = userServiceImpl.register(user);
        ModelAndView mav = new ModelAndView("index");
        if (index > 0) {
            mav.addObject("mav", "注册成功!");
            session.setAttribute("user", user);
            System.out.println(session);
        } else {
            mav.addObject("r_mav", "注册失败,用户名已存在");
        }
        return mav;
    }

    @RequestMapping("/deleteUser")
    public ModelAndView deleteUser(HttpServletRequest request){
        String delBy = request.getParameter("userId");
        String page = request.getParameter("page");
        int rows = 10;
        if(null==page || "".equals(page)){
            page="1";
        }
        ModelAndView mav = new ModelAndView("admin/bs_userList");
        boolean isDelete = userServiceImpl.delById(delBy);
        if(isDelete) {
            mav.addObject("mav", "删除成功！");
            System.out.println("删除成功！");
        } else {
            mav.addObject("r_mav", "该用户不存在！");
        }

        Page<User> list = userServiceImpl.findUserByPage(Integer.parseInt(page), rows);
        mav.addObject("userList",list);
        return mav;
    }

    @RequestMapping("/adminUpdateOp")
    public ModelAndView adminUpdateOp(User user) {
        System.out.println("update Info!");
        ModelAndView mav = new ModelAndView("redirect:/toUserList");
        boolean index = userServiceImpl.adminUpdateOp(user);
        System.out.println(user);
        if (index) {
            System.out.println(2);
            mav.addObject("msg", "success!");
            return mav;
        } else {
            System.out.println(3);
            mav.addObject("msg", "fail");
            return mav;
        }
    }
}
