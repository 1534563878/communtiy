package com.zyq.community.contorller;

import com.zyq.community.bean.User;
import com.zyq.community.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * @author zyq
 * @description
 * @create 2021/5/5
 **/
@Controller
public class indexController {
    @Autowired
    private UserMapper userMapper;
    @RequestMapping("/")
    public String index(HttpServletRequest request ){
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie: cookies) {
            if (cookie.getName().equals("token")){
                String token = cookie.getValue();
                //在登录页面登陆过就会有token
                // 去数据库中查询是否有这个user
                User user =  userMapper.findByToken(token);
                //有的话加入session
                if (user != null) {
                    request.getSession().setAttribute("user",user);
                }
            }
        }
        return "index";
    }
}
