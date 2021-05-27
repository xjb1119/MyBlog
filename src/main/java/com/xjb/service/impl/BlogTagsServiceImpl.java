package com.xjb.service.impl;

import com.xjb.NotFoundException;
import com.xjb.dao.BlogTagsMapper;
import com.xjb.pojo.BlogTags;
import com.xjb.pojo.Tag;
import com.xjb.service.BlogTagsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Bo
 * @create 2021-04-23 10:52
 */
@Service
public class BlogTagsServiceImpl implements BlogTagsService {

    @Autowired
    private BlogTagsMapper blogTagsMapper;

    /**
     * 根据传进来的博客id和标签id存储到数据库表t_blog_tags中
     * @param blogId
     * @param tagIds
     * @return
     */
    @Transactional
    @Override
    public int saveBlogTags(Long blogId, String tagIds) {
        List<Long> tagIdList = convertToList(tagIds);
        for (int i = 0; i < tagIdList.size(); i++) {
            blogTagsMapper.saveBlogTags(blogId,tagIdList.get(i));
        }
        return 1;
    }

    /**
     * 根据传进来的博客id删除表t_blog_tags中数据
     * @param blogId
     * @return
     */
    @Transactional
    @Override
    public int deleteBlogTags(Long blogId) {
        if ( blogTagsMapper.getByBlogId(blogId) == null){
            throw new NotFoundException("该博客的标签不存在");
        }
        return blogTagsMapper.deleteBlogTags(blogId);
    }

    /**
     * 根据blogId查询数据库中该博客数据，并把这些标签id转成字符串类型（1，2，3）
     * @param blogId
     * @return
     */
    @Transactional
    @Override
    public String getByBlogId(Long blogId) {
        List<BlogTags> blogTags = blogTagsMapper.getByBlogId(blogId);
        String tagIds = listToString(blogTags);
        return tagIds;
    }

    //把字符串"1,2,3"装换成Long类型的集合
    private List<Long> convertToList(String ids){
        List<Long> list = new ArrayList<>();
        if (!"".equals(ids) && ids != null){
            String[] idArray = ids.split(",");
            for (int i = 0; i < idArray.length; i++) {
                list.add(new Long(idArray[i]));
            }
        }
        return list;
    }

    //把BlogTags集合中的tagId转成字符串1,2,3
    private String listToString(List<BlogTags> blogTags) {
        String tagIds = "";
        for (int i = 0; i < blogTags.size(); i++) {
            if ( i != blogTags.size()-1 ){
                tagIds = tagIds + (blogTags.get(i).getTagId()) + ",";
            }else {
                tagIds = tagIds + (blogTags.get(i).getTagId());
            }
        }
        return tagIds;
    }
}
