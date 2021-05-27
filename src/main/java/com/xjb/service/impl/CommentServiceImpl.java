package com.xjb.service.impl;

import com.xjb.dao.CommentMapper;
import com.xjb.pojo.Blog;
import com.xjb.pojo.Comment;
import com.xjb.service.CommentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Bo
 * @create 2021-04-25 15:52
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Transactional
    @Override
    public int saveComment(Comment comment) {
        //获取该评论的父id(前端已设初始值为-1)
//        Long ParentCommentId = comment.getParentCommentId();
//        if ( ParentCommentId != -1){
//            //父评论不为空
//            //根据父评论id查询，返回父评论的comment对象
//            Comment parentComment = commentMapper.getParentComment(ParentCommentId);
//            //把父评论对象set进新增的评论对象里
//            comment.setParentComment(parentComment);
//        }else {
//            //父评论为空，改变父评论id的初始值，改为null，因为后面要存进数据库中
////            comment.setParentCommentId(null);
//
//            //后来数据库改成了查询根评论论时只查询parentCommentId=-1的;
//        }
        // 设置评论创建时间
        comment.setCreateTime(new Date());
        //新增评论
        int i = commentMapper.saveComment(comment);
        if ( i != 0 ){
            // 新增评论已成功，更新博客评论次数
            commentMapper.updateComments(comment.getBlogId());
        }
        return i;
    }


    /**
     * 处理方法不太适合，因为for循环导致操作过多次数据库，暂时未想到好的解决办法
     * @param blogId
     * @return
     */
    @Transactional
    @Override
    public List<Comment> getByBlogIdAllComment(Long blogId) {
        List<Comment> rootList = commentMapper.getByBlogIdAllComment(blogId);
        for (int i = 0; i < rootList.size(); i++) {
            Long rootCommentId = rootList.get(i).getId();
            List<Comment> replyList = commentMapper.getAllReplyRootComment(blogId, rootCommentId);
            rootList.get(i).setReplyComments(replyList);
        }
        return eachComment(rootList);
    }

    @Transactional
    @Override
    public int updateComments(Long blogId) {
        return commentMapper.updateComments(blogId);
    }

    /**
     * 删除评论，传两个参数进来，需要删除的评论id和它的根评论id(默认-1)
     * 如果传进来的根评论id不为-1，则只删除该评论id的评论；
     * 如果根评论id为-1，则删除该评论id的评论和所有根评论id为该评论id的评论
     * @param commentId
     * @param rootCommentId
     * @return
     */
    @Transactional
    @Override
    public int deleteComment(Long commentId, Long rootCommentId) {
        return commentMapper.deleteComment(commentId,rootCommentId);
    }

    @Transactional
    @Override
    public List<Comment> getAllComments() {
        return commentMapper.getAllComments();
    }


    /**
     * 循环每个顶级的评论节点
     * @param comments
     * @return
     */
    private List<Comment> eachComment(List<Comment> comments) {
        List<Comment> commentsView = new ArrayList<>();
        for (Comment comment : comments) {
            Comment c = new Comment();
            BeanUtils.copyProperties(comment,c);
            commentsView.add(c);
        }
        //合并评论的各层子代到第一级子代集合中
        combineChildren(commentsView);
        return commentsView;
    }

    /**
     *
     * @param comments root根节点，blog不为空的对象集合
     * @return
     */
    private void combineChildren(List<Comment> comments) {

        for (Comment comment : comments) {
            List<Comment> replys1 = comment.getReplyComments();
            for(Comment reply1 : replys1) {
                //循环迭代，找出子代，存放在tempReplys中
                recursively(reply1);
            }
            //修改顶级节点的reply集合为迭代处理后的集合
            comment.setReplyComments(tempReplys);
            //清除临时存放区
            tempReplys = new ArrayList<>();
        }
    }

    //存放迭代找出的所有子代的集合
    private List<Comment> tempReplys = new ArrayList<>();
    /**
     * 递归迭代，剥洋葱
     * @param comment 被迭代的对象
     * @return
     */
    private void recursively(Comment comment) {
        tempReplys.add(comment);//顶节点添加到临时存放集合
        if (comment.getReplyComments().size()>0) {
            List<Comment> replys = comment.getReplyComments();
            for (Comment reply : replys) {
                tempReplys.add(reply);
                if (reply.getReplyComments().size()>0) {
                    recursively(reply);
                }
            }
        }
    }

}
