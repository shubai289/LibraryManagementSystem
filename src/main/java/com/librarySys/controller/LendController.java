package com.librarySys.controller;

import com.librarySys.pojo.Lend;
import com.librarySys.pojo.User;
import com.librarySys.service.impl.LendServiceImpl;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Controller
public class LendController {
    @Resource
    private LendServiceImpl lendServiceImpl;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @RequestMapping("/returnBook")
    public ModelAndView returnBook(HttpServletRequest request) {
        int lend_id = Integer.parseInt(request.getParameter("id"));
        int index = lendServiceImpl.returnBook(lend_id);
        ModelAndView mav = new ModelAndView("redirect:/showUserLendList");
        if(index > 0) {
            mav.addObject("msg","成功还书");
        } else {
            mav.addObject("msg","还书失败");
        }
        return mav;
    }

    @RequestMapping("/lendBook")
    public ModelAndView lendBook(HttpServletRequest request){
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        HashMap<String, Object> map = new HashMap<>();
        map.put("book_id",Integer.parseInt(request.getParameter("b_no")));
        map.put("user_id",user.getId());

        map.put("lend_date",new Date());
        map.put("state",1);

        int index = lendServiceImpl.insLendList(map);
        ModelAndView mav = new ModelAndView("redirect:/showUserLendList");
        if (index < 0) {
            mav.setViewName("redirect:/showAll");
            session.setAttribute("msg", "error");
        }
        return mav;
    }

    @RequestMapping("/showLendList")
    public ModelAndView showAllLendList(HttpServletRequest request){
        List<Lend> lends = lendServiceImpl.showLendList();
        ModelAndView mav = new ModelAndView("/all_lend_list");
        mav.addObject("lendList", lends);
        return mav;
    }

    @RequestMapping("/showBookLendList")
    public ModelAndView showBookLendList(HttpServletRequest request){

        HashMap<String, Integer> map = new HashMap<>();
        map.put("id",Integer.parseInt(request.getParameter("b_no")));
        List<Lend> lends = lendServiceImpl.showBookLendList(map);
        ModelAndView mav = new ModelAndView("/all_lend_list");
        mav.addObject("lendList", lends);
        return mav;
    }

    @RequestMapping("/showUserLendList")
    public ModelAndView showUserLendList(HttpSession session){
        User user = (User) session.getAttribute("user");
        System.out.println(user);
        List<Lend> lends = lendServiceImpl.showUserLendList(user);
        ModelAndView mav = new ModelAndView("/lend_list");
        mav.addObject("lendList", lends);
        return mav;
    }
}
