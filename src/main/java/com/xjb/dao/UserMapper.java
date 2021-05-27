package com.xjb.dao;

import com.xjb.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author Bo
 * @create 2021-04-17 10:40
 */
@Mapper
@Repository
public interface UserMapper {
    /**
     * 查询用户名和密码
     * @param username
     * @param password
     * @return
     */
    User findByUsernameAndPassword(String username,String password);
}
