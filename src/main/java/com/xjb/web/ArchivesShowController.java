package com.xjb.web;

import com.xjb.pojo.Blog;
import com.xjb.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

/**
 * @author Bo
 * @create 2021-04-27 13:52
 */
@Controller
public class ArchivesShowController {

    @Autowired
    private BlogService blogService;

    @GetMapping("/archives")
    public String archive(Model model){
        // 按年份倒叙查询每个年份相对于博客信息
        Map<String, List<Blog>> archiveMap = blogService.getArchives();
        // 查询到所有已发布的博客数量
        int blogCount = blogService.getBlogPublishedIndex().size();
        model.addAttribute("archiveMap",archiveMap);
        model.addAttribute("blogCount",blogCount);
        return "archives";
    }

}
