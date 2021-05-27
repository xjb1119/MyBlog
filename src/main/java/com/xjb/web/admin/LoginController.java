package com.xjb.web.admin;

import com.xjb.pojo.User;
import com.xjb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

/**
 * @author Bo
 * @create 2021-04-17 9:18
 */
@Controller
@RequestMapping("/admin")
public class LoginController implements HandlerInterceptor {

    @Autowired
    private UserService userService;


    /**
     * 访问/admin直接跳转到管理后台登录页
     * @return
     */
    @GetMapping
    public String loginPage(){

        return "admin/login";
    }


    /**
     * 解决直接在浏览器中输入http://localhost:8080/admin/login访问出错
     * 因为在浏览器中直接输入为get请求，进不去postLogin方法中，会报错
     * 这个方法的作用是让页面跳转到login.html再重新填写表单再以post方式提交
     * @return
     */
    @GetMapping("/login")
    public String getLogin(){

        return "admin/login";
    }

    /**
     * 检查登录的用户名和密码是否正确
     * @param username
     * @param password
     * @return
     */
    @PostMapping("/login")
    public String postLogin(@RequestParam("username") String username,
                            @RequestParam("password") String password,
                            HttpSession session,
                            RedirectAttributes attributes,
                            Model model){
        User user = userService.checkUser(username, password);
//        if (user != null){
//            //登录成功，把用户信息放在session中返回给页面
//            user.setPassword(null);
//            session.setAttribute("user",user);
//            return "admin/index";
//        }else {
//            //登录失败，把错误信息放在attributes中重定向回admin，让它跳转回登录页。
//            //用model存错误信息重定向后失效
//            attributes.addFlashAttribute("message","用户名或密码错误");
//            return "redirect:/admin";
//        }
        if (user != null){
            //登录成功，把用户信息放在session中返回给页面
            user.setPassword(null);
            session.setAttribute("user",user);
            return "redirect:/admin/index";
        }else {
            //登录失败，把错误信息放在model中请求转发回admin
            model.addAttribute("message","用户名或密码错误");
            return "admin/login";
        }
    }


    /**
     * 注销
     * @param session
     * @return
     */
    @GetMapping("/logout")
    public String logout(HttpSession session){
        //清空session中的已登录用户信息，再重定向回登录页面
        session.removeAttribute("user");
        return "redirect:/";
    }


    /**
     * 去后台管理主页面
     * @return
     */
    @GetMapping("/index")
    public String index(){

        return "admin/index";
    }


}
