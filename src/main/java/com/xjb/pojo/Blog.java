package com.xjb.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Bo
 * @create 2021-04-16 15:57
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Blog {

    private Long id;

    @NotBlank(message = "博客标题不能为空")
    private String title;    //标题

    @NotBlank(message = "博客内容不能为空")
    private String content;    //内容

    @NotBlank(message = "博客首图不能为空")
    private String firstPicture;    //首图

    @NotBlank(message = "博客描述不能为空")
    private String description;     //博客描述

    private String flag;    //标记(原创/转载/翻译)
    private Integer views;    //浏览次数
    private Integer commentCount;    //评论次数
    private boolean appreciation;    //赞赏开启
    private boolean shareStatement;    //版权开启
    private boolean commentabled;    //评论开启
    private boolean published;    //发布状态(发布or草稿) 1为发布
    private boolean recommend;    //推荐开启 1为true
    private Date createTime;    //创建时间
    private Date updateTime;    //更新时间

    private Long typeId;    //分类id
    private Long userId;    //用户id

    //上面的是数据库表中的字段
    //-------分割线--------//
    //下面的不是数据库表中的字段


    private Type type;  //该博客对应的分类
    private User user;  //该博客对应的用户
    private List<Tag> tags = new ArrayList<>();     //该博客对应的所有标签
    private List<Comment> comments = new ArrayList<>();     //该博客对应的所有评论
}
