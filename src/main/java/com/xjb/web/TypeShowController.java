package com.xjb.web;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xjb.service.BlogService;
import com.xjb.service.TypeService;
import com.xjb.vo.BlogIndex;
import com.xjb.vo.TypeIndex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author Bo
 * @create 2021-04-26 19:52
 */
@Controller
public class TypeShowController {

    @Autowired
    private TypeService typeService;

    @Autowired
    private BlogService blogService;


    /**
     * 注解 @GetMapping(value = {"/types","/types/{id}"}) 表示可以同时处理路径为/types和/types/1(等带id的路径)
     * 注解 @PathVariable(value = "id",required = false) Long id 因为这里设置了required = false，即是当没有路径id时，id为null
     *          此方法的处理正常进行，required默认为true
     * @param pageNum
     * @param id
     * @param model
     * @return
     */
    @GetMapping(value = {"/types","/types/{id}"})
    public String type(@RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum,
                       @PathVariable(value = "id",required = false) Long id,
                       Model model){
        // 查询各分类所对应的已发布博客数量，并按博客数量倒叙排序返回，传10000表示查10000条
        List<TypeIndex> types = typeService.getTypeDescIndex(10000);
        // 判断是否从导航栏进入分类页，如果是则id为null
        if ( id == null){
            // 给id赋值为查询到的第一个分类id(因为此时展示的就是第一个分类的博客)
            id = types.get(0).getTypeId();
        }
        // 按分类id查询已发布的博客信息并进行分页
        PageHelper.startPage(pageNum,5,"update_time desc");
        List<BlogIndex> list = blogService.getBlogByTypeId(id);
        PageInfo pageInfo = new PageInfo(list);
        //返回数据给前端页面，已选中的id也要传回去
        model.addAttribute("types",types);
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("activeTypeId",id);

        return "types";
    }
}
