package com.xjb.web;

import com.xjb.pojo.Comment;
import com.xjb.pojo.User;
import com.xjb.service.BlogService;
import com.xjb.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

/**
 * @author Bo
 * @create 2021-04-25 12:51
 */
@Controller
public class CommentShowController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private BlogService blogService;

    @Value("${comment.avatar}")
    private String avatar;


    /**
     *
     * 当下面post方法处理好了后请求重定向到/comments/{blogId}或者当页面第一次加载后就会来到这个方法
     * 把查询到的该博客下所有评论信息都放在comments集合里(service层已经处理好评论回复层次关系了)，放在model传给前端页面
     * 只更新页面的commentList部分
     * @param blogId
     * @param model
     * @return
     */
    @GetMapping("/comments/{blogId}")
    public String comments(@PathVariable("blogId") Long blogId, Model model){
        //查询该博客id的所有评论信息
        model.addAttribute("comments",commentService.getByBlogIdAllComment(blogId));
        return "blog :: commentList";
    }


    /**
     * 新增博客评论，在输入框点评论提交后，来到这个方法
     * 还加了判断是否为管理员评论
     * @param comment
     * @param session
     * @return
     */
    @PostMapping("/comments")
    public String post(Comment comment, HttpSession session) {
        // 获取登录的用户信息
        User user = (User) session.getAttribute("user");
        // 判断是否有用户登录
        if (user != null){
            // 用户已经登录
            // 设置该评论的头像为用户的头像、设置该评论为管理员评论
            comment.setAvatar(user.getAvatar());
            comment.setAdminComment(true);
        }else {
            // 没有用户登录
            // 设置评论的头像为游客头像、默认评论为不为管理员评论
            comment.setAvatar(avatar);
        }
        //新增评论
        commentService.saveComment(comment);
        //查到该评论所属于的博客id，重定向回博客详情
        return "redirect:/comments/" + comment.getBlogId();
    }
}
