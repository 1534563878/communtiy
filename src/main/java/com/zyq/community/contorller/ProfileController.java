package com.zyq.community.contorller;

import com.zyq.community.bean.User;
import com.zyq.community.bean.UserExample;
import com.zyq.community.dto.PaginationDTO;
import com.zyq.community.mapper.QuestionMapper;
import com.zyq.community.mapper.UserMapper;
import com.zyq.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.websocket.Session;
import java.util.List;

/**
 * @author zyq
 * @description  我的问题模块. 1  首先里面是动态的点击我的问题左上角切换成我的问题,点击最新回复左上角变成最新回复
 *   @PathVariable(name="action")  这个来实现. action 是从前端页面传过来的<a href="/profile/questions" ></a>
 *   第二个就是显示我的问题了.这个过程完全和首页显示一样,分页之类的...
 * @create 2021/5/11
 **/
@Controller
public class ProfileController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionService questionService;
    @GetMapping("/profile/{action}")
    //PathVariable动态的获取
    public String profile(@PathVariable(name="action") String action,
                          Model model,
                          HttpServletRequest request,
                          @RequestParam(name = "page",defaultValue = "1") Integer page,
                          @RequestParam(name = "size",defaultValue = "5") Integer size){
        //登录
      /*  User user =null;
        Cookie[] cookies = request.getCookies();
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
      User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return "redirect:/";
        }
        if ("questions".equals(action)){
            model.addAttribute("section","questions");
            model.addAttribute("sectionName","我的提问");
        }else if("replies".equals(action)){
            model.addAttribute("section","replies");
            model.addAttribute("sectionName","最新回复");
        }
        PaginationDTO paginationDTO = questionService.list2(user.getId(), page, size);
        model.addAttribute("pagination",paginationDTO);
        return "profile";
    }
}
