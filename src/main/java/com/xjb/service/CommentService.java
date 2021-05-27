package com.xjb.service;

import com.xjb.pojo.Comment;


import java.util.List;

/**
 * @author Bo
 * @create 2021-04-25 15:50
 */
public interface CommentService {


    //新增评论
    int saveComment(Comment comment);

    //查询该博客下的所有评论
    List<Comment> getByBlogIdAllComment(Long blogId);

    //根据博客id查询该博客有多少条评论次数，并更新博客表中的评论次数
    int updateComments(Long blogId);

    // 删除评论，传两个参数进来，需要删除的评论id和它的根评论id(默认-1)
    // 如果传进来的根评论id不为-1，则只删除该评论id的评论；
    // 如果根评论id为-1，则删除该评论id的评论和所有根评论id为该评论id的评论
    int deleteComment(Long commentId,Long rootCommentId);

    // 查询所有评论
    List<Comment> getAllComments();
}
