package com.xjb.web;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xjb.service.BlogService;
import com.xjb.service.TagService;
import com.xjb.vo.BlogIndex;
import com.xjb.vo.TagIndex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Bo
 * @create 2021-04-26 19:52
 */
@Controller
public class TagShowController {

    @Autowired
    private TagService tagService;

    @Autowired
    private BlogService blogService;


    @GetMapping(value = {"/tags","/tags/{id}"})
    public String tag(@RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum,
                       @PathVariable(value = "id",required = false) Long id,
                       Model model){
        // 查询各标签所对应的已发布博客数量，并按博客数量倒叙排序返回，传10000表示查10000条
        List<TagIndex> tags = tagService.getTagDescIndex(10000);
        // 判断是否从导航栏进入标签页，如果是则id为null
        if ( id == null){
            // 给id赋值为查询到的第一个标签id(因为此时展示的就是第一个标签的博客)
            id = tags.get(0).getTagId();
        }
        // 按标签id查询已发布的博客信息并进行分页
        PageHelper.startPage(pageNum,5,"update_time desc");
        List<BlogIndex> list = blogService.getBlogByTagId(id);
        PageInfo pageInfo = new PageInfo(list);
        //返回数据给前端页面，已选中的id也要传回去
        model.addAttribute("tags",tags);
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("activeTagId",id);

        // 解决方法欠妥
        List<Long> blogIdList = new ArrayList<>();
        for (BlogIndex blogIndex : list) {
            blogIdList.add(blogIndex.getBlogId());
        }
        Map<Long, List<TagIndex>> map = tagService.getMapTagByBlog(blogIdList);
        model.addAttribute("tagMap", map);


        return "tags";
    }
}
