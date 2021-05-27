package com.xjb.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Bo
 * @create 2021-04-16 16:26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {

    private Long id;            //评论id
    private String nickname;    //评论名
    private String email;       //邮箱
    private String content;     //评论内容
    private String avatar;      //头像
    private Date createTime;    //评论时间
    private Long blogId;            //该评论对应的博客id
    private Long parentCommentId;  //此评论的父评论id
    private String parentCommentName;    //此评论的父评论名
    private boolean adminComment;  //是否为管理员的评论
    private Long rootCommentId;    //该评论对应的根评论id(便于前端页面的展示)

    private Blog blog;  //该评论对应的博客

    private List<Comment> replyComments = new ArrayList<>();    //此评论的多个子类评论
    private Comment parentComment;      //此评论的父类评论
}
