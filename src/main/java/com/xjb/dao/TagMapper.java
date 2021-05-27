package com.xjb.dao;

import com.xjb.pojo.Tag;
import com.xjb.vo.TagIndex;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Bo
 * @create 2021-04-18 15:07
 */
@Mapper
@Repository
public interface TagMapper {

    int saveTag(Tag tag);

    @Delete("delete from t_tag where id = #{id}")
    int deleteTag(Long id);

    @Update("update t_tag set name = #{name} where id = #{id}")
    int updateTag(Tag tag);

    @Select("select * from t_tag where id = #{id}")
    Tag getTagId(Long id);

    @Select("select * from t_tag where name = #{name}")
    Tag getTagName(String name);

    @Select("select * from t_tag")
    List<Tag> getAllTag();

    //根据id组成的list集合查询对应的标签，并把这些标签封装成一个集合返回
    List<Tag> getIdListTag(List<Long> idList);

    //查询各标签所对应的博客数量、标签名、标签id，随机返回tagCount条数据
    List<TagIndex> getTagRandomIndex(Integer tagCount);

    //根据博客id查询该博客的所有标签id和标签名
    List<TagIndex> getTagNameByBlogId(Long blogId);

    //查询各标签所对应的博客数量、标签名、标签id，降序返回tagCount条数据
    List<TagIndex> getTagDescIndex(Integer tagCount);
}
