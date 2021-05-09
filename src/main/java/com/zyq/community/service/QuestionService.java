package com.zyq.community.service;

import com.zyq.community.bean.Question;
import com.zyq.community.bean.User;
import com.zyq.community.dto.QuestionDTO;
import com.zyq.community.mapper.QuestionMapper;
import com.zyq.community.mapper.UserMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zyq
 * @description  service用来连接user和question的
 * @create 2021/5/9
 **/
@Service
public class QuestionService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionMapper questionMapper;
    public List<QuestionDTO> list() {
        //查询所有发表评论的用户
        List<Question> questions = questionMapper.list();

        List<QuestionDTO> questionDTOlist = new ArrayList<>();
        //循环所有的用户
        for (Question question: questions) {
            //根据ID查询所有用户
            User user= userMapper.findById(question.getCreator());//question 是发表评论的用户, create 其实就是ID
            QuestionDTO questionDTO =new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOlist.add(questionDTO);
        }
        return questionDTOlist;
    }
}
