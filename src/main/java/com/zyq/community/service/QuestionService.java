package com.zyq.community.service;

import com.zyq.community.bean.Question;
import com.zyq.community.bean.QuestionExample;
import com.zyq.community.bean.User;
import com.zyq.community.dto.PaginationDTO;
import com.zyq.community.dto.QuestionDTO;
import com.zyq.community.mapper.QuestionMapper;
import com.zyq.community.mapper.UserMapper;
import org.apache.ibatis.session.RowBounds;
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
    public PaginationDTO list1(Integer page, Integer size) {
        PaginationDTO paginationDTO = new PaginationDTO();
        //查出数据的总数
        Integer count =(int)questionMapper.countByExample(new QuestionExample());
        //分页功能的实现
        paginationDTO.setPagination(count,page,size);
        if (page<1){
            page=1;
        }
        if (page>paginationDTO.getTotalPage()){
            page=paginationDTO.getTotalPage();
        }
        //offset : 分多少页
        Integer offset = size*(page-1);
        //查询5个发表评论的用户
        //select * from question limit 10,5   所以需要传入 offset size
        List<Question> questions = questionMapper.selectByExampleWithRowbounds(new QuestionExample(), new RowBounds(offset, size));
        List<QuestionDTO> questionDTOlist = new ArrayList<>();
        //循环所有的用户
        for (Question question: questions) {
            //根据ID查询所有用户
            User user= userMapper.selectByPrimaryKey(question.getCreator());//question 是发表评论的用户, create 其实就是ID
            QuestionDTO questionDTO =new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOlist.add(questionDTO);
        }
        paginationDTO.setQuestions(questionDTOlist);
        return paginationDTO;
    }
    public PaginationDTO list2(Integer userId, Integer page, Integer size) {
        PaginationDTO paginationDTO = new PaginationDTO();
        Integer count =(int)questionMapper.countByExample(new QuestionExample());
        paginationDTO.setPagination(count,page,size);
        if (page<1){
            page=1;
        }
        if (page>paginationDTO.getTotalPage()){
            page=paginationDTO.getTotalPage();
        }
        Integer offset = size*(page-1);
        QuestionExample example = new QuestionExample();
        example.createCriteria().andIdEqualTo(userId);
        List<Question> questions = questionMapper.selectByExampleWithRowbounds(example, new RowBounds(offset, size));
        List<QuestionDTO> questionDTOlist = new ArrayList<>();
        for (Question question: questions) {
            User user= userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO =new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOlist.add(questionDTO);
        }
        paginationDTO.setQuestions(questionDTOlist);
        return paginationDTO;
    }

    public QuestionDTO getById(Integer id) {
        Question question=questionMapper.selectByPrimaryKey(id);
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question,questionDTO);
        User user= userMapper.selectByPrimaryKey(question.getCreator());
        questionDTO.setUser(user);
        return questionDTO;

    }

    public void createOrUpdate(Question question) {
        //需要一个唯一标识   因为要查数据
        if (question.getId()== null) {
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
          /*  question.setCommentCount(0);
            question.setViewCount(0);
            question.setLikeCount(0);*/
            questionMapper.insert(question);
        }else {
            Question updateQuestion = new Question();
            updateQuestion.setGmtModified(System.currentTimeMillis());
            updateQuestion.setTitle(question.getTitle());
            updateQuestion.setDescription(question.getDescription());
            updateQuestion.setTag(question.getTag());

            QuestionExample example = new QuestionExample();
            example.createCriteria().andIdEqualTo(question.getId());
            questionMapper.updateByExampleSelective(updateQuestion, example);
        }
    }

    public void deleteById(Integer id) {
        questionMapper.deleteByPrimaryKey(id);
    }
}
