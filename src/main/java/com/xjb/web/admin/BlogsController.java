package com.xjb.web.admin;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xjb.pojo.Blog;
import com.xjb.pojo.User;
import com.xjb.service.BlogTagsService;
import com.xjb.service.TagService;
import com.xjb.vo.BlogManage;
import com.xjb.service.BlogService;
import com.xjb.service.TypeService;
import com.xjb.vo.BlogSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

/**
 * @author Bo
 * @create 2021-04-17 11:59
 */
@Controller
@RequestMapping("/admin")
public class BlogsController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private BlogTagsService blogTagsService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;


    /**
     * 全部的博客管理列表
     * @param pageNum
     * @param model
     * @return
     */
    @GetMapping("/blogs")
    public String blogs(@RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum,
                        Model model){
        //按照排序字段 倒叙 排序
        String orderBy = "update_time desc";
        //startPage的三个参数分别为当前页码、每页条数、排列顺序
        PageHelper.startPage(pageNum,7,orderBy);
        //查询所有数据并封装成集合
        List<BlogManage> list = blogService.getBlogManage();
        //再通过封装成当页的数据构成的集合
        PageInfo<BlogManage> pageInfo = new PageInfo<>(list);
        //查询出所有分类并放在model传递给页面，用于分类下拉框的数据显示
        model.addAttribute("types",typeService.getAllType());
        //把当页的数据放在model传递给页面，在博客管理页面显示数据
        model.addAttribute("pageInfo",pageInfo);
        //用于前端页面判断使用哪一个分页(区分分页请求带不带搜索栏数据)
        model.addAttribute("isSearch", false);
        return "admin/blogs";
    }


    /**
     * 根据搜索栏条件筛选后的博客管理列表
     * @param pageNum
     * @param model
     * @param blogSearch
     * @return
     */
    @PostMapping("/blogs/search")
    public String search(@RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum,
                         Model model, BlogSearch blogSearch){
        String orderBy = "update_time desc";
        PageHelper.startPage(pageNum,7,orderBy);
        List<BlogManage> list = blogService.getBlogAdminSearch(blogSearch);
        PageInfo<BlogManage> pageInfo = new PageInfo<>(list);
        model.addAttribute("pageInfo",pageInfo);
        //用于前端页面判断使用哪一个分页(区分分页请求带不带搜索栏数据)
        model.addAttribute("isSearch",true);
        //返回admin文件夹下的blogs页面下的blogList片段
        //即页面除开blogList片段外其它数据不变，多用于刷新表格(页面刷新只刷新表格数据)；功能由Springboot、MVC和thy模板的fragment一起实现
        //用于博客管理页面点击搜索或上下页时，页面数据不会出错(保持原页面选的标题、分类、是否推荐等数据)
        return "admin/blogs :: blogList";
    }

    /**
     * 跳转到博客新增页面
     * @return
     */
    @GetMapping("/blogs/input")
    public String input(Model model){
        model.addAttribute("blog",new Blog());
        //把所以分类和标签放在model里传递给前端页面显示
        model.addAttribute("types",typeService.getAllType());
        model.addAttribute("tags",tagService.getAllTag());
        return "admin/blogs-input";
    }

    /**
     * 新增博客操作
     * @param blog
     * @param result
     * @param tagIds
     * @param session
     * @param attributes
     * @return
     */
    @PostMapping("/blogs")
    public String save(@Valid Blog blog, BindingResult result,
                       @RequestParam("tagIds") String tagIds,
                       HttpSession session, RedirectAttributes attributes,Model model){
        // 后端非空的验证
        // 判断标题、内容、首图地址是否为空，为空则回到博客发布页面并提示错误信息。
        if ( result.hasErrors() ){
            model.addAttribute("types",typeService.getAllType());
            model.addAttribute("tags",tagService.getAllTag());
            return "admin/blogs-input";
        }
        // 查询一下新增的博客标题是否已经存在
        if ( blogService.getBlogTitle(blog.getTitle()) != null){
            return "admin/blogs-input";
        }
        // 从session域中提取出目前登录的该用户信息，并给blog对象赋值
        User user = (User) session.getAttribute("user");
        blog.setUserId(user.getId());

        // 新增博客，并通过返回值验证操作结果
        if ( blogService.saveBlog(blog) == 0){
            attributes.addFlashAttribute("message","操作失败");
        }else {
            attributes.addFlashAttribute("message","操作成功");

            // 插入操作 获取页面中所选的标签id,和刚才存储到数据库的博客id一起储存到表t_blog_tags中
            Blog saveBlog = blogService.getBlogTitle(blog.getTitle());
            blogTagsService.saveBlogTags(saveBlog.getId(), tagIds);
        }
        return "redirect:/admin/blogs";
    }

    /**
     * 处理点击编辑后跳转到博客新增页面，并把需要编辑的博客对象放在model里
     * @param id
     * @param model
     * @return
     */
    @GetMapping("blogs/{id}/input")
    public String update(@PathVariable("id") Long id, Model model){
        model.addAttribute("blog",blogService.getBlogId(id));
        //把所以分类和标签放在model里传递给前端页面显示，因为编辑可能要修改分类或者标题（下拉框需要数据显示）
        model.addAttribute("types",typeService.getAllType());
        model.addAttribute("tags",tagService.getAllTag());
        //把需要修改的博客所对应的标签id（以1，2，3）放在model里
        model.addAttribute("tagIds",blogTagsService.getByBlogId(id));
        return "admin/blogs-input";
    }


    /**
     * 删除文章
     * @param id
     * @param pageNum
     * @param attributes
     * @return
     */
    @GetMapping("blogs/{id}/delete")
    public String delete(@PathVariable("id") Long id,
                         @RequestParam("pageNum") Integer pageNum,
                         RedirectAttributes attributes){
        if ( blogService.getBlogId(id) != null){
            //把表t_blog_tags中的该博客id信息删除掉（小bug）
            blogTagsService.deleteBlogTags(id);
            if (blogService.deleteBlog(id) != 0 ){
                attributes.addFlashAttribute("message","删除成功");
            }else {
                attributes.addFlashAttribute("message","删除失败");
            }
        }else {
            attributes.addFlashAttribute("message","删除失败");
        }
        return "redirect:/admin/blogs?pageNum=" + pageNum;
    }


    /**
     * 更新博客操作
     * @param blog
     * @param result
     * @param tagIds
     * @param attributes
     * @return
     */
    @PostMapping("/blogs/update")
    public String update(@Valid Blog blog, BindingResult result,
                         @RequestParam("tagIds") String tagIds,
                         RedirectAttributes attributes,Model model){
        if ( result.hasErrors() ){
            model.addAttribute("types",typeService.getAllType());
            model.addAttribute("tags",tagService.getAllTag());
            return "admin/blogs-input";
        }
        if ( blogService.updateBlog(blog) == 0){
            attributes.addFlashAttribute("message","操作失败");
        }else {
            attributes.addFlashAttribute("message","操作成功");

            blogTagsService.deleteBlogTags(blog.getId());
            blogTagsService.saveBlogTags(blog.getId(), tagIds);
        }
        return "redirect:/admin/blogs";
    }
}
