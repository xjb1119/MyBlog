package com.xjb.service;

import com.xjb.pojo.User;

/**
 * @author Bo
 * @create 2021-04-17 9:11
 */
public interface UserService {

    /**
     * 检查用户名和密码是否正确
     * @param username
     * @param password
     * @return
     */
    User checkUser(String username,String password);
}
