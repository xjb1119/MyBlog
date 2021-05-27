package com.xjb.dao;

import com.xjb.pojo.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Bo
 * @create 2021-04-25 13:28
 */
@Mapper
@Repository
public interface CommentMapper {

    //新增评论
    int saveComment(Comment comment);

    //根据博客id查询该博客所有评论信息（包括父评论）
    List<Comment> getByBlogIdAllComment(Long blogId);

    //查询此评论id的父级对象，根据此评论的父评论id查询父评论的详细信息----->>子评论 找 父评论
    Comment getParentComment(Long parentCommentId);

    //根据评论id(根id)查询回复此评论的所有评论对象集合（在同一篇博客下）----->>根评论 找 所有子评论
    List<Comment> getAllReplyRootComment(@Param("blogId") Long blogId,@Param("rootCommentId") Long rootCommentId);

    //根据博客id查询该博客有多少条评论次数，并更新博客表中的评论次数
    int updateComments(Long blogId);


    // 删除评论，传两个参数进来，需要删除的评论id和它的根评论id(默认-1)
    // 如果传进来的根评论id不为-1，则只删除该评论id的评论；
    // 如果根评论id为-1，则删除该评论id的评论和所有根评论id为该评论id的评论
    int deleteComment(@Param("commentId") Long commentId,@Param("rootCommentId") Long rootCommentId);

    // 查询所有评论
    @Select("select * from t_comment")
    List<Comment> getAllComments();
}
