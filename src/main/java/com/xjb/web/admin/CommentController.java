package com.xjb.web.admin;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xjb.pojo.Comment;
import com.xjb.service.BlogService;
import com.xjb.service.CommentService;
import com.xjb.vo.BlogIndex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * @author Bo
 * @create 2021-04-28 14:08
 */
@Controller
@RequestMapping("/admin")
public class CommentController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private CommentService commentService;

    @GetMapping("/comments")
    public String comments(@RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
                           Model model){

        PageHelper.startPage(pageNum,8,"create_time desc");
        List<Comment> list = commentService.getAllComments();
        PageInfo<Comment> pageInfo = new PageInfo<>(list);
        model.addAttribute("pageInfo",pageInfo);
        // 最新评论的博客id，用于给前端页面点详情时可以跳转
        Long newestBlogId = list.get(0).getBlogId();
        model.addAttribute("newestBlogId",newestBlogId);
        return "admin/comments";
    }


    @GetMapping("/comments/details/{blogId}")
    public String commentsDetails(@PathVariable("blogId") Long blogId,
                           Model model){
        commentService.updateComments(blogId);
        BlogIndex blog = blogService.getOneBlog(blogId);
        List<Comment> comments = commentService.getByBlogIdAllComment(blogId);
        model.addAttribute("blog",blog);
        model.addAttribute("comments",comments);
        return "admin/comments-details";
    }


    @GetMapping("/comments/details/{blogId}/delete")
    public String delete(@PathVariable("blogId") Long blogId,
                         @RequestParam("commentId") Long commentId,
                         @RequestParam(value = "rootCommentId",defaultValue = "-1" ) Long rootCommentId,
                         RedirectAttributes redirectAttributes){
        int i = commentService.deleteComment(commentId, rootCommentId);
        if ( i != 0 ){
            redirectAttributes.addFlashAttribute("message","删除成功");
        }else {
            redirectAttributes.addFlashAttribute("message","删除失败");
        }
        return "redirect:/admin/comments/details/" + blogId;
    }
}
