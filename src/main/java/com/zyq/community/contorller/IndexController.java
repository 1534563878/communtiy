package com.zyq.community.contorller;

import com.zyq.community.bean.User;
import com.zyq.community.bean.UserExample;
import com.zyq.community.dto.PaginationDTO;
import com.zyq.community.dto.QuestionDTO;
import com.zyq.community.mapper.UserMapper;
import com.zyq.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author zyq
 * @description
 * @create 2021/5/5
 **/
@Controller
public class IndexController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionService questionService;
    @RequestMapping("/")
    //刷新页面后数据不丢失 不用重新登录
    public String index(HttpServletRequest request, Model model,
                        @RequestParam(name = "page",defaultValue = "1") Integer page,
                        @RequestParam(name = "size",defaultValue = "7") Integer size ){

        //查询所用发表评论的用户 然后加入到 model中
        //因为要获取question和user里面的avatarUrl字段,这不是一个表,把他们管联系起来所以创建了questionDTO;
      /*  Cookie[] cookies = request.getCookies();
        if (cookies!=null && cookies.length!=0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    String token = cookie.getValue();
                    //在登录页面登陆过就会有tokens
                    // 去数据库中查询是否有这个user
                    UserExample userExample = new UserExample();
                    userExample.createCriteria().andTokenEqualTo(token);
                    List<User> users = userMapper.selectByExample(userExample);
                    //有的话加入session
                    if (users.size() != 0) {
                        request.getSession().setAttribute("user", users.get(0));
                    }
                }
            }
        }*/
        PaginationDTO pagination= questionService.list1(page,size);
        model.addAttribute("pagination",pagination);
        return "index";
    }
}
