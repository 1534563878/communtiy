package com.zyq.community.mapper;

import com.zyq.community.bean.Question;
import com.zyq.community.dto.QuestionDTO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author zyq
 * @description
 * @create 2021/5/9
 **/
@Mapper
public interface QuestionMapper {
    @Insert("insert into question(title,description,tag,gmt_create,gmt_modified,creator) values(#{title},#{description},#{tag},#{gmtCreate},#{gmtModified},#{creator})")
    public void create(Question question);
    @Select("select * from question")
    public List<Question> list();
}
