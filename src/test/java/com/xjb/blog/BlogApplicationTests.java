package com.xjb.blog;

import com.xjb.dao.BlogMapper;
import com.xjb.dao.BlogTagsMapper;
import com.xjb.dao.CommentMapper;

import com.xjb.pojo.Blog;
import com.xjb.pojo.BlogTags;
import com.xjb.pojo.Comment;
import com.xjb.service.*;
import com.xjb.vo.BlogIndex;
import com.xjb.vo.BlogManage;
import lombok.extern.slf4j.Slf4j;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@SpringBootTest
class BlogApplicationTests {

    @Autowired
    DataSource dataSource;

    @Autowired
    BlogMapper blogMapper;

    @Autowired
    TagService tagService;

    @Autowired
    TypeService typeService;

    @Autowired
    BlogTagsMapper blogTagsMapper;

    @Autowired
    BlogService blogService;

    @Autowired
    CommentMapper commentMapper;

    @Autowired
    CommentService commentService;

    @Test
    void contextLoads() {
        log.info("数据源：{}",dataSource.getClass());
    }

    @Test
    void test1(){
        List<BlogManage> blogManage = blogMapper.getBlogManage();
        System.out.println(blogManage);
    }

    @Test
    void test2(){
        String s = "     ";
        System.out.println(s);
        System.out.println(s.isEmpty());
        System.out.println(s.trim());
        System.out.println(s.trim().isEmpty());
        System.out.println("".equals(s));
        System.out.println(s==null);
    }

    @Test
    void test3(){
        System.out.println(tagService.getIdListTag("1,2,3"));
//        System.out.println(convertToList("1,2,3"));
    }

    @Test
    void test4(){
        System.out.println(blogTagsMapper.getByBlogId(12L));
        List<BlogTags> byBlogId = blogTagsMapper.getByBlogId(12L);
        String tagIds = "";
        for (int i = 0; i < byBlogId.size(); i++) {
            if ( i != byBlogId.size()-1 ){
                tagIds = tagIds + (byBlogId.get(i).getTagId()) + ",";
            }else {
                tagIds = tagIds + (byBlogId.get(i).getTagId());
            }
        }
        System.out.println(tagIds);

    }

    @Test
    void test5(){

//        System.out.println(typeService.getTypeDescIndex(2));
//        System.out.println(tagService.getTagRandomIndex(3));

//        List<BlogIndex> list = blogService.getBlogRandomIndex();
//        for (BlogIndex blogIndex : list) {
//            System.out.println(blogIndex);
//        }

        List<BlogIndex> list = blogService.getBlogRecommendDesc(5);
        for (BlogIndex blogIndex : list) {
            System.out.println(blogIndex);
        }
    }

    @Test
    void test6(){
        String query = "标题";
//        query = "%" + query + "%";
        System.out.println(query);
        List<BlogIndex> list = blogService.getBlogVisitorsSearch(query);
//        List<BlogIndex> list = blogMapper.getBlogVisitorsSearch(query);
        for (BlogIndex blogIndex : list) {
            System.out.println(blogIndex);
        }
    }

    @Test
    void test7(){
//        List<Comment> list = commentMapper.getByBlogIdAllComments(1l);
//        for (Comment comment : list) {
//            System.out.println(comment);
//        }

//        List<Comment> list = commentMapper.getByBlogIdAllComment(6L);
//        List<Comment> list = commentMapper.getAllReplyComment(6L, 4L);
        List<Comment> list = commentService.getByBlogIdAllComment(6L);
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }


    }

    @Test
    void test8(){
//        List<BlogIndex> blogByTypeId = blogService.getBlogByTypeId(100L);
//        List<BlogIndex> list = blogMapper.getBlogByTagId(7L);
//        System.out.println(blogService.getArchives());
        List<String> list = new ArrayList<String>();
        Map<String,List<Blog>> map = new HashMap<>();
        list.add("2021");
        list.add("2020");
        list.add("2019");
        list.add("2018");
        for (String s : list) {
            List<Blog> blogList = blogMapper.getBlogByYear(s);
            for (Blog blog : blogList) {
                System.out.println(blog);
            }
            map.put(s,blogList);
        }
        for (Map.Entry<String,List<Blog>> entry : map.entrySet()){
            System.out.println(entry);
        }
    }

    @Test
    void test9(){
        System.out.println(commentMapper.deleteComment(69L, -1L));
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
