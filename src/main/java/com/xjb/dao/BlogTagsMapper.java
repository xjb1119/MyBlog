package com.xjb.dao;

import com.xjb.pojo.BlogTags;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Bo
 * @create 2021-04-23 10:41
 */
@Mapper
@Repository
public interface BlogTagsMapper {

    int saveBlogTags(Long blogId, Long tagId);

    @Delete("delete from t_blog_tags where blog_id = #{blogId}")
    int deleteBlogTags(Long blogId);

    @Select("select * from t_blog_tags where blog_id = #{blogId}")
    List<BlogTags> getByBlogId(Long blogId);

}
