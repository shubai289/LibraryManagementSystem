package com.librarySys.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor implements HandlerInterceptor {
    /**
     * 登录拦截器，未登录不可访问
     * @param httpServletRequest
     * @param httpServletResponse
     * @param o
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        String uri = httpServletRequest.getRequestURI();
        // 放行登录和注册请求
        if (uri.equals("/login") || uri.equals("/bookList/login") || uri.equals("/register") || uri.equals("/toRegister") || uri.equals("/toAdminLogin")){
            return true;
        }else if (uri.contains("/res/") || uri.equals("/findBookByPageServlet")) {
            return true;
        }else if(uri.equals("/adminLogin") || uri.equals("/showAll") || uri.equals("/searchBook")) {
            return true;
        }else{
            Object obj1 = httpServletRequest.getSession().getAttribute("user");
            Object obj2 = httpServletRequest.getSession().getAttribute("admin");
            if (obj1 != null || obj2 != null) {
                return true;
            } else {
                httpServletResponse.sendRedirect("/index.jsp");
                return false;
            }
        }
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
