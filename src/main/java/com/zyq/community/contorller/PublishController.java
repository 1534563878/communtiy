package com.zyq.community.contorller;

import com.zyq.community.bean.Question;
import com.zyq.community.bean.User;
import com.zyq.community.mapper.QuestionMapper;
import com.zyq.community.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * @author zyq
 * @description
 * @create 2021/5/8
 **/
@Controller
public class PublishController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionMapper questionMapper;
    //跳转页面
    @GetMapping("/publish")
    public String publish() {
        return "publish";
    }
    //提交表单
    @PostMapping("/publish")
    //title description tag  是三个需要写入上传的三个参数
    public String doPublish(@RequestParam("title")  String title,
                            @RequestParam("description") String description,
                            @RequestParam("tag") String tag,
                            HttpServletRequest request,
                            Model model) {

        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);
        if (title==null|| title.equals("")){
            model.addAttribute("error","标题不能为空");
            return "publish";
        }
        if (description==null|| description.equals("")){
            model.addAttribute("error","问题补充不能为空");
            return "publish";
        }
        if (tag==null|| tag.equals("")){
            model.addAttribute("error","标签不能为空");
            return "publish";
        }

        //判断用户是否处于登录状态
        User user =null;
        Cookie[] cookies = request.getCookies();
        if (cookies!=null && cookies.length!=0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    String token = cookie.getValue();
                    //在登录页面登陆过就会有tokens
                    // 去数据库中查询是否有这个user
                    user = userMapper.findByToken(token);
                    //有的话加入session
                    if (user != null) {
                        request.getSession().setAttribute("user", user);
                    }
                }
            }
        }
        if (user == null) {
            model.addAttribute("error","用户未登录");
            return "publish";
        }
        //问题标题  问题补充 添加标签 这三个写入数据库中
        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(question.getGmtCreate());
        question.setCreator(user.getId());
        questionMapper.create(question);
        return "redirect:/";
    }
}