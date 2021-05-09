package com.zyq.community.contorller;

import com.zyq.community.bean.User;
import com.zyq.community.dto.QuestionDTO;
import com.zyq.community.mapper.UserMapper;
import com.zyq.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author zyq
 * @description
 * @create 2021/5/5
 **/
@Controller
public class indexController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionService questionService;
    @RequestMapping("/")
    //刷新页面后数据不丢失 不用重新登录
    public String index(HttpServletRequest request, Model model){
        Cookie[] cookies = request.getCookies();
        if (cookies!=null && cookies.length!=0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    String token = cookie.getValue();
                    //在登录页面登陆过就会有token
                    // 去数据库中查询是否有这个user
                    User user = userMapper.findByToken(token);
                    //有的话加入session
                    if (user != null) {
                        request.getSession().setAttribute("user", user);
                    }
                }
            }
        }
        //查询所用发表评论的用户 然后加入到 model中
        //因为要获取question和user里面的avatarUrl字段,这不是一个表,把他们管联系起来所以创建了questionDTO;
        List<QuestionDTO> Questionlist= questionService.list();
        model.addAttribute("questions",Questionlist);
        return "index";
    }
}
