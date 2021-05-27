package com.xjb.service.impl;

import com.xjb.NotFoundException;
import com.xjb.dao.BlogMapper;
import com.xjb.pojo.Blog;
import com.xjb.util.MarkdownUtils;
import com.xjb.vo.BlogIndex;
import com.xjb.vo.BlogManage;
import com.xjb.service.BlogService;
import com.xjb.vo.BlogSearch;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.*;

/**
 * @author Bo
 * @create 2021-04-18 19:05
 */
@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogMapper blogMapper;

    @Transactional
    @Override
    public int saveBlog(Blog blog) {
        //给blog对象赋值(创建时间、更新时间、浏览次数、评论数)
        blog.setCreateTime(new Date());
        blog.setUpdateTime(new Date());
        blog.setViews(0);
        blog.setCommentCount(0);
        return blogMapper.saveBlog(blog);
    }

    @Transactional
    @Override
    public int deleteBlog(Long id) {
        return blogMapper.deleteBlog(id);
    }

    @Transactional
    @Override
    public int updateBlog(Blog blog) {
        if ( blogMapper.getBlogId(blog.getId()) == null ){
            throw new NotFoundException("该博客不存在");
        }
        blog.setUpdateTime(new Date());
        return blogMapper.updateBlog(blog);
    }

    @Transactional
    @Override
    public Blog getBlogId(Long id) {
        return blogMapper.getBlogId(id);
    }

    @Transactional
    @Override
    public List<Blog> getAllBlog() {
        return blogMapper.getAllBlog();
    }

    @Transactional
    @Override
    public List<BlogManage> getBlogManage() {
        return blogMapper.getBlogManage();
    }

    /**
     * 首页搜索栏的功能，根据输入的query模糊查询博客(标题/描述/文章)中与其相对应的已发布博客信息并返回
     * @param query
     * @return
     */
    @Transactional
    @Override
    public List<BlogIndex> getBlogVisitorsSearch(String query) {
        //模糊查询的处理
        query = "%" + query + "%";
        return blogMapper.getBlogVisitorsSearch(query);
    }

    /**
     * 用于博客管理页面点击搜索后的显示
     * @param blogSearch 搜索栏条件封装成的对象(标题、分类id、分类名)
     * @return 符合搜索栏条件的博客信息(id、标题、推荐、状态、更新时间、分类id、分类名)的集合
     */
    @Transactional
    @Override
    public List<BlogManage> getBlogAdminSearch(BlogSearch blogSearch) {
        //判断title的值是否纯空格，如果是纯空格联合其它条件查询会出现空的返回结果
        //这里纯空格的title设置其值为空串，这样sql语句就会跳过检查是否有纯空格的标题，从而有返回结果。
        if ( blogSearch.getTitle().trim().isEmpty() ){
            blogSearch.setTitle("");
        }
        List<BlogManage> blogSearchList = blogMapper.getBlogAdminSearch(blogSearch);
        return blogSearchList;
    }

    @Transactional
    @Override
    public Blog getBlogTitle(String title) {
        return blogMapper.getBlogTitle(title);
    }


    /**
     * 查询所有已发布的博客；用于首页展示博客简易信息（包括用户名头像等等）
     * @return
     */
    @Transactional
    @Override
    public List<BlogIndex> getBlogPublishedIndex() {

        return blogMapper.getBlogPublishedIndex();
    }

    /**
     * 查询所有已发布且推荐的博客，按浏览次数降序返回blogCount条信息；用于首页展示热门博客信息。
     * @param blogCount
     * @return
     */
    @Transactional
    @Override
    public List<BlogIndex> getBlogRecommendDesc(Integer blogCount) {
        return blogMapper.getBlogRecommendDesc(blogCount);
    }


    /**
     * 根据博客id查询一条已发布的博客详细信息，包括用户名头像等等，用于博客详情页面的展示
     * 并将content中的内容转成html格式，便于前端页面Markdown编辑器的展示
     * 调用此方法证明访问一次博客详情页面，该博客的浏览次数加一
     * @param blogId
     * @return
     */
    @Transactional
    @Override
    public BlogIndex getOneBlogShow(Long blogId) {
        BlogIndex blogIndex = blogMapper.getOneBlogShow(blogId);
        if ( blogIndex == null ){
            throw new NotFoundException("该博客不存在");
        }
        // 更新博客浏览次数，让浏览次数加1
        blogMapper.updateViews(blogId);

        // 避免Markdown编辑器处理后content变成html格式存储到数据库中，影响后续操作。
        BlogIndex b = new BlogIndex();
        BeanUtils.copyProperties(blogIndex,b);
        String content = b.getContent();
        String markdown = MarkdownUtils.markdownToHtmlExtensions(content);
        b.setContent(markdown);
        return b;
    }

    /**
     * 根据分类id查询对应的所有已发布博客信息
     * @param typeId
     * @return
     */
    @Transactional
    @Override
    public List<BlogIndex> getBlogByTypeId(Long typeId) {
        List<BlogIndex> list = blogMapper.getBlogByTypeId(typeId);
        //该分类id对应的已发布博客数量为0
        if ( list.size() == 0 ){
            throw new NotFoundException("分类不存在或博客为空");
        }
        return list;
    }

    @Transactional
    @Override
    public List<BlogIndex> getBlogByTagId(Long tagId) {
        List<BlogIndex> list = blogMapper.getBlogByTagId(tagId);
        //该标签id对应的已发布博客数量为0
        if ( list.size() == 0 ){
            throw new NotFoundException("标签不存在或博客为空");
        }
        return list;
    }


    /**
     * for循环操作数据库不妥，后续应该想更好的办法解决
     * 先查询到哪些年份有已发布的博客，返回一个年份(倒叙)集合
     * 然后根据年份集合查询集合中每个年份对应的所有已发布博客信息(按更新时间倒叙排)
     * 最终返回一个map集合
     * @return
     */
    @Transactional
    @Override
    public Map<String, List<Blog>> getArchives() {
        List<String> years = blogMapper.getAllYearDesc();
        // Hashmap是无序的，需要用LinkedHashMap
        Map<String, List<Blog>> map = new LinkedHashMap<>();
        for (String year : years) {
            List<Blog> blogList = blogMapper.getBlogByYear(year);
            map.put(year,blogList);
        }
        return map;
    }

    @Transactional
    @Override
    public BlogIndex getOneBlog(Long blogId) {
        BlogIndex blogIndex = blogMapper.getOneBlogShow(blogId);
        if ( blogIndex == null ){
            throw new NotFoundException("该博客不存在");
        }
        return blogIndex;
    }


}
