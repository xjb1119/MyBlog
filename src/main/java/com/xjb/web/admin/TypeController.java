package com.xjb.web.admin;


import com.xjb.pojo.Type;
import com.xjb.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * @author Bo
 * @create 2021-04-17 17:39
 */
@Controller
@RequestMapping("/admin")
public class TypeController {

    @Autowired
    private TypeService typeService;

    /**
     * 分页查询分类列表
     * @param model
     * @param pageNum
     * @return
     */
    @GetMapping("/types")
    public String list(Model model,@RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum){
        //按照排序字段 倒序 排序
        String orderBy = "id desc";
        PageHelper.startPage(pageNum,8,orderBy);
        List<Type> list = typeService.getAllType();
        PageInfo<Type> pageInfo = new PageInfo<>(list);
        model.addAttribute("pageInfo",pageInfo);
        return "admin/types";
    }


    /**
     * 跳转到分类新增页面
     * @return
     */
    @GetMapping("/types/input")
    public String input(Model model){
        model.addAttribute("type",new Type());
        return "admin/types-input";
    }

    /**
     * types-input页面的提交功能(插入操作)
     * @param type
     * @param attributes
     * @return
     */
    @PostMapping("/types")
    public String save(@Valid Type type, BindingResult result, RedirectAttributes attributes){
        //判断提交的信息是否为空串或纯空格；如果是，则不保存并返回错误信息
//        if ( type.getName().trim().isEmpty() ){
//            attributes.addFlashAttribute("message","新增失败，内容不符合规则");
//            return "redirect:/admin/types";
//        }
        if ( result.hasErrors() ){
            return "admin/types-input";
        }

        //判断提交的信息是否已存在数据库；如果是，则不保存并返回错误信息
//        if ( typeService.getTypeName(type.getName()) != null){
//            attributes.addFlashAttribute("message","新增失败，内容已存在");
//            return "redirect:/admin/types";
//        }
        if ( typeService.getTypeName(type.getName()) != null){
            result.rejectValue("name","nameError","不能添加重复的分类");
            return "admin/types-input";
        }

        if ( typeService.saveType(type) == 0 ){
            attributes.addFlashAttribute("message","新增失败");
        }else {
            attributes.addFlashAttribute("message","新增成功");
        }
        return "redirect:/admin/types";
    }

    /**
     * 分类页面的删除功能
     * @param id
     * @param pageNum
     * @param attributes
     * @return
     */
    @GetMapping("types/{id}/delete")
    public String delete(@PathVariable("id") Long id,
                         @RequestParam("pageNum") Integer pageNum,
                         RedirectAttributes attributes){
        Type type = typeService.getTypeId(id);
        if (type != null){
            if( typeService.deleteType(id) != 0){
                attributes.addFlashAttribute("message","删除成功");
            }else {
                attributes.addFlashAttribute("message","删除失败");
            }
        }else {
            attributes.addFlashAttribute("message","删除失败");
        }
        return "redirect:/admin/types?pageNum=" + pageNum;
    }


    /**
     * 点击编辑后携带id去分类新增页面
     * @param id
     * @param name
     * @param pageNum
     * @param model
     * @return
     */
    @GetMapping("/types/{id}/input")
    public String update(@PathVariable("id") Long id,
                         @RequestParam("name") String name,
                         @RequestParam("pageNum") Integer pageNum,
                         Model model){
        model.addAttribute("type",new Type());
        //查询出需要编辑的分类id，把它放在model传递去types-input页面
        model.addAttribute("updateId",id);
        //查询出需要编辑的分类名，把它放在model传递去types-input页面回显
        model.addAttribute("typeName",name);
        //查询出需要编辑的分类原页码
        model.addAttribute("pageNum",pageNum);
        return "admin/types-input";
    }


    /**
     * types-input页面的提交功能(更新操作)
     * 实现types页面的编辑功能
     * @param type
     * @param result
     * @param updateId
     * @param request
     * @param attributes
     * @return
     */
    @PostMapping("/types/{updateId}")
    public String updateSave(@Valid Type type, BindingResult result,
                             @PathVariable("updateId") Long updateId,
                             @RequestParam("pageNum") Integer pageNum,
                             HttpServletRequest request,
                             RedirectAttributes attributes){
        //获取需要编辑的分类所在的页码数(需要从string转为integer)
//        Integer pageNum = Integer.parseInt(request.getParameter("pageNum"));

        //判断提交的信息是否为空串或纯空格；如果是，则不保存并返回错误信息
//        if ( type.getName().trim().isEmpty() ){
//            attributes.addFlashAttribute("message","更新失败，内容不符合规则");
//            return "redirect:/admin/types?pageNum=" + pageNum;
//        }

        //判断提交的信息中name是否为空(用@NotBlank注解标识了)，如果是空，把@NotBlank后面的message错误信息返回给页面
        if ( result.hasErrors() ){
            return "admin/types-input";
        }

        //判断提交的信息是否已存在数据库；如果是，则不保存并返回错误信息
        if ( typeService.getTypeName(type.getName()) != null){
//            attributes.addFlashAttribute("message","更新失败，内容已存在");
//            return "redirect:/admin/types?pageNum=" + 1;
            //手动设置错误信息
            result.rejectValue("name","nameError","不能添加重复的分类");
            return "admin/types-input";
        }

        //设置type的id为之前需要编辑的id
        type.setId(updateId);
        //更新到数据库并查看返回值是否不为0，为0则错误
        if ( typeService.updateType(type) == 0 ){
            attributes.addFlashAttribute("message","更新失败");
        }else {
            attributes.addFlashAttribute("message","更新成功");
        }
        return "redirect:/admin/types?pageNum=" + pageNum;
    }
}
