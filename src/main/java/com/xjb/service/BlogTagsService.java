package com.xjb.service;

import com.xjb.pojo.BlogTags;

import java.util.List;

/**
 * @author Bo
 * @create 2021-04-23 10:51
 */
public interface BlogTagsService {

    int saveBlogTags(Long blogId, String tagId);

    int deleteBlogTags(Long blogId);

    String getByBlogId(Long blogId);
}
