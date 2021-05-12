package com.zyq.community.mapper;

import com.zyq.community.bean.Question;
import com.zyq.community.dto.QuestionDTO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
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

    @Select("select * from question limit #{offset},#{size}")
    public List<Question> list1(@Param(value = "offset") Integer offset,@Param(value = "size") Integer size);
    @Select("select count(1) from question")
    Integer count();
    @Select("select * from question where creator=#{userId} limit #{offset},#{size}")
    List<Question> list2(@Param(value = "userId") Integer userId,@Param(value = "offset") Integer offset,@Param(value = "size") Integer size);
}
