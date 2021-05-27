package com.xjb.service.impl;

import com.xjb.dao.UserMapper;
import com.xjb.pojo.User;
import com.xjb.service.UserService;
import com.xjb.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Bo
 * @create 2021-04-17 9:12
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Transactional
    @Override
    public User checkUser(String username, String password) {

        return userMapper.findByUsernameAndPassword(username, MD5Utils.code(password));
    }
}
