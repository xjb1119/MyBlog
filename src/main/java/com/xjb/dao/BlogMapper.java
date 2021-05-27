package com.xjb.dao;

import com.xjb.pojo.Blog;
import com.xjb.vo.BlogIndex;
import com.xjb.vo.BlogManage;
import com.xjb.vo.BlogSearch;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Bo
 * @create 2021-04-18 19:08
 */
@Mapper
@Repository
public interface BlogMapper {

    //新增博客
    int saveBlog(Blog blog);

    //删除博客
    @Delete("delete from t_blog where id = #{id}")
    int deleteBlog(Long id);

    //更新博客
    int updateBlog(Blog blog);

    //根据id查询一条博客信息
    @Select("select * from t_blog where id = #{id}")
    Blog getBlogId(Long id);

    //查询所有博客的信息
    @Select("select * from t_blog")
    List<Blog> getAllBlog();

    //查询所有的博客信息(id、标题、推荐、状态、更新时间、分类id、分类名)，用于博客管理页面的显示
    List<BlogManage> getBlogManage();


    //(管理员)查询符合搜索栏条件的博客信息(id、标题、推荐、状态、更新时间、分类id、分类名),用于博客管理页面点击搜索后的显示
    List<BlogManage> getBlogAdminSearch(BlogSearch blogSearch);


    //根据标题名查询一条博客信息
    @Select("select * from t_blog where title = #{title}")
    Blog getBlogTitle(String title);

    //查询所有已发布的博客；用于首页展示博客简易信息（包括用户名头像等等）。
    List<BlogIndex> getBlogPublishedIndex();

    //查询所有已发布且推荐的博客，按浏览次数降序返回blogCount条信息；用于首页展示热门博客信息。
    List<BlogIndex> getBlogRecommendDesc(Integer blogCount);

    //(访客)根据query模糊查询已发布的博客(标题/描述/文章)中与其相对应的博客信息并返回。
    List<BlogIndex> getBlogVisitorsSearch(String query);

    //根据博客id查询一条已发布的博客详细信息，包括用户名头像等等，用于博客详情页面的展示
    BlogIndex getOneBlogShow(Long blogId);

    //更新博客浏览次数，每次加一
    @Update("update t_blog set views = views+1 where id = #{id}")
    int updateViews(Long id);

    //根据分类id查询对应的所有已发布博客信息
    List<BlogIndex> getBlogByTypeId(Long typeId);

    //根据标签id查询对应的所有已发布博客信息
    List<BlogIndex> getBlogByTagId(Long tagId);

    //查询所有已发布博客的年份，返回一个年份的集合
    List<String> getAllYearDesc();

    //根据年份查询该年发布的博客
    List<Blog> getBlogByYear(String year);




}
