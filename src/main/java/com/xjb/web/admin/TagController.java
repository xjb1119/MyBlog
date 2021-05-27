package com.xjb.web.admin;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xjb.pojo.Tag;
import com.xjb.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Bo
 * @create 2021-04-17 17:39
 */
@Controller
@RequestMapping("/admin")
public class TagController {

    @Autowired
    private TagService tagService;

    /**
     * 分页查询分类列表
     * @param model
     * @param pageNum
     * @return
     */
    @GetMapping("/tags")
    public String list(Model model,@RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum){
        //按照排序字段 倒序 排序
        String orderBy = "id desc";
        PageHelper.startPage(pageNum,8,orderBy);
        List<Tag> list = tagService.getAllTag();
        PageInfo<Tag> pageInfo = new PageInfo<>(list);
        model.addAttribute("pageInfo",pageInfo);
        return "admin/tags";
    }


    /**
     * 跳转到分类新增页面
     * @return
     */
    @GetMapping("/tags/input")
    public String input(){

        return "admin/tags-input";
    }

    /**
     * tag-input页面的提交功能(插入操作)
     * @param tag
     * @param attributes
     * @return
     */
    @PostMapping("/tags")
    public String save(Tag tag, RedirectAttributes attributes){
        //判断提交的信息是否为空串或纯空格；如果是，则不保存并返回错误信息
        if ( tag.getName().trim().isEmpty() ){
            attributes.addFlashAttribute("message","新增失败，内容不符合规则");
            return "redirect:/admin/tags";
        }
        //判断提交的信息是否已存在数据库；如果是，则不保存并返回错误信息
        if ( tagService.getTagName(tag.getName()) != null){
            attributes.addFlashAttribute("message","新增失败，内容已存在");
            return "redirect:/admin/tags";
        }
        if ( tagService.saveTag(tag) == 0 ){
            attributes.addFlashAttribute("message","新增失败");
        }else {
            attributes.addFlashAttribute("message","新增成功");
        }
        return "redirect:/admin/tags";
    }

    /**
     * 分类页面的删除功能
     * @param id
     * @param pageNum
     * @param attributes
     * @return
     */
    @GetMapping("tags/{id}/delete")
    public String delete(@PathVariable("id") Long id,
                         @RequestParam("pageNum") Integer pageNum,
                         RedirectAttributes attributes){
        Tag tag = tagService.getTagId(id);
        if (tag != null){
            int i = tagService.deleteTag(id);
            attributes.addFlashAttribute("message","删除成功");
        }else {
            attributes.addFlashAttribute("message","删除失败");
        }
        return "redirect:/admin/tags?pageNum=" + pageNum;
    }


    /**
     * 点击编辑后携带id去分类新增页面
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/tags/{id}/input")
    public String update(@PathVariable("id") Long id,
                         @RequestParam("name") String name,
                         @RequestParam("pageNum") Integer pageNum,
                         Model model){
        //查询出需要编辑的分类id，把它放在model传递去tags-input页面
        model.addAttribute("updateId",id);
        //查询出需要编辑的分类名，把它放在model传递去tags-input页面回显
        model.addAttribute("tagName",name);
        //查询出需要编辑的分类原页码
        model.addAttribute("pageNum",pageNum);
        return "admin/tags-input";
    }


    /**
     * tags-input页面的提交功能(更新操作)
     * 实现tags页面的编辑功能
     * @param updateId
     * @param tag
     * @param attributes
     * @return
     */
    @PostMapping("/tags/{updateId}")
    public String updateInput(@PathVariable("updateId") Long updateId,
                              HttpServletRequest request, Tag tag, RedirectAttributes attributes){
        //获取需要编辑的分类所在的页码数(需要从string转为integer)
        Integer pageNum = Integer.parseInt(request.getParameter("pageNum"));

        //判断提交的信息是否为空串或纯空格；如果是，则不保存并返回错误信息
        if ( tag.getName().trim().isEmpty() ){
            attributes.addFlashAttribute("message","更新失败，内容不符合规则");
            return "redirect:/admin/tags?pageNum=" + pageNum;
        }
        //判断提交的信息是否已存在数据库；如果是，则不保存并返回错误信息
        if ( tagService.getTagName(tag.getName()) != null){
            attributes.addFlashAttribute("message","更新失败，内容已存在");
            return "redirect:/admin/tags?pageNum=" + pageNum;
        }
        //设置tag的id为之前需要编辑的id
        tag.setId(updateId);
        //更新到数据库并查看返回值是否不为0，为0则错误
        if ( tagService.updateTag(tag) == 0 ){
            attributes.addFlashAttribute("message","更新失败");
        }else {
            attributes.addFlashAttribute("message","更新成功");
        }
        return "redirect:/admin/tags?pageNum=" + pageNum;
    }
}
