package com.zyq.community.service;

import com.zyq.community.bean.User;
import com.zyq.community.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zyq
 * @description
 * @create 2021/5/15
 **/
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public void createOrUpdate(User user) {
        //getAccountId 是唯一标识
        User dbUser = userMapper.findByAccount(user.getAccountId());
        if (dbUser == null) {
            userMapper.insert(user);
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
        }else {
            dbUser.setGmtModified(System.currentTimeMillis());
            dbUser.setAvatarUrl(user.getAvatarUrl());
            dbUser.setName(user.getName());
            dbUser.setToken(user.getToken());
            userMapper.update(dbUser);
        }
    }
}
