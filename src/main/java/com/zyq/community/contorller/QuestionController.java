package com.zyq.community.contorller;

import com.zyq.community.dto.QuestionDTO;
import com.zyq.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author zyq
 * @description   管理自己发布的内容
 * @create 2021/5/13
 **/
@Controller
public class QuestionController {
    @Autowired
    private QuestionService questionService;
    //用户不止发布了一个内容所以也要有很多ID;
    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id")Integer id,
                           Model model){
        //返回QuestionDTO  是因为管理发布的问题,需要user 和Question 两张表
        QuestionDTO questionDTO = questionService.getById(id);
        model.addAttribute("question",questionDTO);
        return "question";

    }
}
