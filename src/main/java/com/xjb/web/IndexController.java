package com.xjb.web;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xjb.service.BlogService;
import com.xjb.service.TagService;
import com.xjb.service.TypeService;
import com.xjb.vo.BlogIndex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


import java.util.List;

/**
 * 返回首页控制器
 * @author Bo
 * @create 2021-04-15 16:51
 */
@Controller
public class IndexController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    /**
     * 返回index页面
     * @return
     */
    @GetMapping("/")
    public String index(@RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum,
                        Model model){
        PageHelper.startPage(pageNum,8,"update_time desc");
        //查询推荐的博客信息
        List<BlogIndex> list = blogService.getBlogPublishedIndex();
        PageInfo<BlogIndex> pageInfo = new PageInfo<>(list);

        model.addAttribute("pageInfo",pageInfo);
        //热门分类的数据
        model.addAttribute("types",typeService.getTypeDescIndex(5));
        //随机标签的数据
        model.addAttribute("tags",tagService.getTagRandomIndex(7));
        //热门推荐博客的数据
        model.addAttribute("recommendBlogs",blogService.getBlogRecommendDesc(5));
        return "index";
    }


    @GetMapping("/search")
    public String search(@RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum,
                         @RequestParam("query") String query,
                         Model model){
        PageHelper.startPage(pageNum,5,"update_time desc");
        //根据搜索栏查询博客信息，返回集合
        List<BlogIndex> list = blogService.getBlogVisitorsSearch(query);
        PageInfo<BlogIndex> pageInfo = new PageInfo<>(list);

        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("query",query);
        return "search";
    }



    @GetMapping("/blog/{blogId}")
    public String blog(@PathVariable("blogId") Long blogId,
                       Model model){
        //根据博客id查询某一博客的详细信息
        model.addAttribute("blog",blogService.getOneBlogShow(blogId));
        //根据博客id查询该博客所对应的所有标签
        model.addAttribute("tags",tagService.getTagNameByBlogId(blogId));
        return "blog";
    }


    @GetMapping("/footer/hotblog")
    public String hotblog(Model model){
        model.addAttribute("hotblogs",blogService.getBlogRecommendDesc(3));
        return "common :: hotblogList";
    }

}
