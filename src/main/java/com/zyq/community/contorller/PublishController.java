package com.zyq.community.contorller;

import com.zyq.community.bean.Question;
import com.zyq.community.bean.User;
import com.zyq.community.bean.UserExample;
import com.zyq.community.dto.QuestionDTO;
import com.zyq.community.mapper.QuestionMapper;
import com.zyq.community.mapper.UserMapper;
import com.zyq.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
    private QuestionService questionService;
    @GetMapping("/publish/{id}")
    public String edit(@PathVariable(value = "id") Integer id,
                       Model model){
        //查到问题
        QuestionDTO question=questionService.getById(id);
        //把查到的问题显示到publish页面
        model.addAttribute("title",question.getTitle());
        model.addAttribute("description",question.getDescription());
        model.addAttribute("tag",question.getTag());
        model.addAttribute("id",question.getId());
        return "publish";
    }
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
                            @RequestParam("id") Integer id,
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
   /*     User user =null;
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
            model.addAttribute("error","用户未登录");
            return "publish";
        }
        //问题标题  问题补充 添加标签 这三个写入数据库中
        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getId());
        question.setId(id);
        questionService.createOrUpdate(question);
        return "redirect:/";
    }
}