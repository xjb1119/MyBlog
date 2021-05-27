package com.xjb.service.impl;

import com.xjb.NotFoundException;
import com.xjb.dao.TagMapper;
import com.xjb.pojo.Tag;
import com.xjb.service.TagService;
import com.xjb.vo.TagIndex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Bo
 * @create 2021-04-18 15:20
 */
@Service
public class TagServiceImpl implements TagService{

    @Autowired
    private TagMapper tagMapper;

    @Transactional
    @Override
    public int saveTag(Tag tag) {
        return tagMapper.saveTag(tag);
    }

    @Transactional
    @Override
    public int deleteTag(Long id) {
        return tagMapper.deleteTag(id);
    }

    @Transactional
    @Override
    public int updateTag(Tag tag) {
        if ( tagMapper.getTagId(tag.getId()) == null ){
            throw new NotFoundException("找不到该类型");
        }else {
            return tagMapper.updateTag(tag);
        }
    }

    @Transactional
    @Override
    public Tag getTagId(Long id) {
        return tagMapper.getTagId(id);
    }

    @Transactional
    @Override
    public Tag getTagName(String name) {
        return tagMapper.getTagName(name);
    }

    @Transactional
    @Override
    public List<Tag> getAllTag() {
        return tagMapper.getAllTag();
    }


    /**
     * 根据传进来多个id连在一起的字符串查询这些id对应的标签信息，并以集合的方式返回
     * @param ids
     * @return
     */
    @Transactional
    @Override
    public List<Tag> getIdListTag(String ids) {
        return tagMapper.getIdListTag(convertToList(ids));
    }


    /**
     * 查询各标签所对应的博客数量、标签名、标签id，随机返回tagCount条数据
     * @param tagCount
     * @return
     */
    @Transactional
    @Override
    public List<TagIndex> getTagRandomIndex(Integer tagCount) {
        return tagMapper.getTagRandomIndex(tagCount);
    }

    /**
     * 根据博客id查询该博客的所有标签id和标签名，用于博客详情的标签展示
     * @param blogId
     * @return
     */
    @Transactional
    @Override
    public List<TagIndex> getTagNameByBlogId(Long blogId) {
        return tagMapper.getTagNameByBlogId(blogId);
    }

    /**
     * 查询各标签所对应的博客数量、标签名、标签id，降序返回tagCount条数据
     * @param tagCount
     * @return
     */
    @Override
    public List<TagIndex> getTagDescIndex(Integer tagCount) {
        return tagMapper.getTagDescIndex(tagCount);
    }


    // 方法欠妥
    // 按博客id的集合查询到集合中每个博客所对应的标签的集合
    // 返回一个map
    @Transactional
    @Override
    public Map<Long,List<TagIndex>> getMapTagByBlog(List<Long> blogIdList) {
        Map<Long,List<TagIndex>> map = new LinkedHashMap<>();
        for (Long id : blogIdList) {
            List<TagIndex> tagList = tagMapper.getTagNameByBlogId(id);
            map.put(id,tagList);
        }
        return map;
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



}
