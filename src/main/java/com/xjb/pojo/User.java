package com.xjb.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Bo
 * @create 2021-04-16 16:33
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private Long id;    //用户id
    private String nickname;    //用户名字
    private String username;    //用户名
    private String password;    //密码
    private String email;   //邮箱
    private String avatar;  //头像
    private Integer type;   //用户类型
    private Date createTime;    //创建时间
    private Date updateTime;    //更新时间

    private List<Blog> blogs = new ArrayList<>();   //该用户对应的所有博客
}
