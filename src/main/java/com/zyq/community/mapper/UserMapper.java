package com.zyq.community.mapper;

import com.zyq.community.bean.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author zyq
 * @description
 * @create 2021/5/5
 **/
@Mapper
public interface UserMapper {
    @Insert("insert into user(name,account_id,token,gmt_create,gmt_modified,avatar_url) values (#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified},#{avatarUrl})" )
    public void insert(User user);

    @Select("select * from user where token = #{token}")
    //是类的话可以直接放 像insert      但是token不是一个类 所以要加RequestParam
    User findByToken(@Param("token") String token);

    @Select("select * from user where id = #{id}")
    User findById(@Param("id")Integer id);
}
