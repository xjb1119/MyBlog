package com.xjb.service;

import com.xjb.pojo.Blog;
import com.xjb.vo.BlogIndex;
import com.xjb.vo.BlogManage;
import com.xjb.vo.BlogSearch;

import java.util.List;
import java.util.Map;

/**
 * @author Bo
 * @create 2021-04-18 19:02
 */
public interface BlogService {


    int saveBlog(Blog blog);

    int deleteBlog(Long id);

    int updateBlog(Blog blog);

    Blog getBlogId(Long id);

    List<Blog> getAllBlog();

    List<BlogManage> getBlogManage();

    //查询符合搜索栏条件的博客信息(id、标题、推荐、状态、更新时间、分类id、分类名),用于博客管理页面点击搜索后的显示
    List<BlogManage> getBlogAdminSearch(BlogSearch blogSearch);

    Blog getBlogTitle(String title);

    //查询所有已发布的博客；用于首页展示博客简易信息（包括用户名头像等等）
    List<BlogIndex> getBlogPublishedIndex();

    //查询所有已发布且推荐的博客，按浏览次数降序返回blogCount条信息；用于首页展示热门博客信息。
    List<BlogIndex> getBlogRecommendDesc(Integer blogCount);


    //首页搜索栏的功能，根据输入的query模糊查询博客(标题/描述/文章)中与其相对应的已发布博客信息并返回
    List<BlogIndex> getBlogVisitorsSearch(String query);

    //根据博客id查询一条已发布博客详细信息，包括用户名头像等等，用于博客详情页面的展示
    BlogIndex getOneBlogShow(Long blogId);

    //根据分类id查询对应的所有已发布博客信息
    List<BlogIndex> getBlogByTypeId(Long typeId);

    //根据标签id查询对应的所有已发布博客信息
    List<BlogIndex> getBlogByTagId(Long tagId);

    //根据已发布的博客所有年份查询该年份下已发布的博客信息，string时年份，list是该年份对应所有的博客集合
    Map<String,List<Blog>> getArchives();


    //根据博客id查询一条已发布博客详细信息，包括用户名头像等等，用于博客评论管理删除的展示
    BlogIndex getOneBlog(Long blogId);

}
