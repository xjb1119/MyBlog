package com.xjb.service;

import com.xjb.pojo.Tag;
import com.xjb.vo.TagIndex;

import java.util.List;
import java.util.Map;

/**
 * @author Bo
 * @create 2021-04-18 15:17
 */
public interface TagService {

    int saveTag(Tag tag);

    int deleteTag(Long id);

    int updateTag(Tag tag);

    Tag getTagId(Long id);

    Tag getTagName(String name);

    List<Tag> getAllTag();

    List<Tag> getIdListTag(String ids);

    //查询各标签所对应的博客数量，随机返回tagCount条数据
    List<TagIndex> getTagRandomIndex(Integer tagCount);

    //根据博客id查询该博客的所有标签id和标签名
    List<TagIndex> getTagNameByBlogId(Long blogId);

    //查询各标签所对应的博客数量、标签名、标签id，降序返回tagCount条数据
    List<TagIndex> getTagDescIndex(Integer tagCount);


    Map<Long,List<TagIndex>> getMapTagByBlog(List<Long> blogId);


}
