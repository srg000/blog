package com.srgstart.blog.controller;

import com.srgstart.blog.entity.Blog;
import com.srgstart.blog.entity.User;
import com.srgstart.blog.result.ResultApi;
import com.srgstart.blog.result.ResultCodeEnum;
import com.srgstart.blog.service.BlogService;
import com.srgstart.blog.service.UserService;
import com.srgstart.blog.util.Md5Util;
import com.srgstart.blog.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * @author srgstart
 * @Create 2021/8/14 18:18
 * @Description 管理用户权限的controller
 */
@Controller
@RequestMapping("/user")
public class UserController {
    public static final String HAVE_LOGIN_ = "have_permission/";
    public static final int MAX_COUNT = 5;

    @Autowired
    private UserService userService;
    @Autowired
    private BlogService blogService;

    /**
     * @param url: 根据html文件名来映射url
     * @return : 指定have_permission下的页面
     */
    @GetMapping("/{url}")
    public String goUrl(@PathVariable String url) {
        return HAVE_LOGIN_ + url;
    }


    /**
     * 用户登录接口
     * @param username：用户名
     * @param password：密码
     * @param session：将用户对象放入session域中
     * @return ：跳转的页面
     */
    @PostMapping("/userLogin")
    public String userLogin(@RequestParam String username,
                            @RequestParam String password,
                            HttpSession session) {
        // 调用service业务方法
        ResultApi<User> resultApi = userService.findUser(username, password);
        // 出现问题
        if (resultApi.getData() == null) {
            session.setAttribute("resultApi",resultApi);
            // 设置session有效期：1h
            session.setMaxInactiveInterval(30);
            return "redirect:/login";
        }

        // no_problem, 把user对象，放入session域中
        session.setAttribute("loginUser",resultApi.getData());
        // 设置session有效期：1h
        session.setMaxInactiveInterval(60*60);
        return "redirect:/index";
    }


    /**
     * 用户注册
     * @param user：注册表单数据
     * @param session：将用户对象放入session域中
     * @return message消息
     */
    @PostMapping("/userRegister")
    public String userRegister(User user, HttpSession session) {
        // 调用service业务方法
        ResultApi<Object> resultApi = userService.userRegister(user);
        // 如果填写的表单数据不合法，就返回message信息
        if (! ResultCodeEnum.REGISTER_SUCCESS.getCode().equals(resultApi.getCode()) ) {
            session.setAttribute("resultApi",resultApi);
            session.setMaxInactiveInterval(30);
            return "redirect:/register";
        }

        // no_problem :login页面
        return "redirect:/login";
    }


    @GetMapping("/myself/{userId}")
    public String myself(@PathVariable Long userId,
                         Model model) {
        // 拿到个人的5篇文章，根据浏览记录排序
        List<Blog> blogList = blogService.getPerBlogBySort(MAX_COUNT, userId);
        model.addAttribute("blogList",blogList);

        User user = userService.findUserById(userId);
        String phone = user.getPhone();
        String newPhone = phone.substring(0,3) + "****" + phone.substring(7,phone.length());
        user.setPhone(newPhone);
        model.addAttribute("user",user);

        Map<String, String> userBlogInfo = blogService.findUserBlogInfo(userId);
        model.addAttribute("views",userBlogInfo.get("views"));
        model.addAttribute("count",userBlogInfo.get("count"));

        return HAVE_LOGIN_ + "mySelf";
    }


    @PostMapping("/updateUserById")
    @ResponseBody
    public int updateUserById(String nikeName,Long id,HttpSession session) {
        User user = (User) session.getAttribute("loginUser");
        user.setNikeName(nikeName);
        session.setAttribute("loginUser",user);
        // 设置session有效期：1h
        session.setMaxInactiveInterval(60*60);
        return userService.updateUserById(nikeName, id);
    }

    @PostMapping("/checkPassword")
    @ResponseBody
    public int passwordIsCurrent(String password,HttpSession session) {
        User user = (User) session.getAttribute("loginUser");
        User userById = userService.findUserById(user.getId());
        String passwordByMd5 = null;
        try {
            passwordByMd5 = Md5Util.encodeByMd5(password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (userById.getPassword().equals(passwordByMd5)) {
            return 200;
        } else {
            return 500;
        }

    }

    @PostMapping("/updateUserPwd")
    @ResponseBody
    public int updateUserPwd(String oldPassword,String newPassword,HttpSession session) {
        User user = (User) session.getAttribute("loginUser");
        // 对用户的密码进行md5加密，再保存到数据库中。
        String passwordByMd5 = null;
        String oldPasswordByMd5 = null;
        try {
            passwordByMd5 = Md5Util.encodeByMd5(newPassword);
            oldPasswordByMd5 = Md5Util.encodeByMd5(oldPassword);
        } catch (Exception e) {
            e.printStackTrace();
        }
        User userById = userService.findUserById(user.getId());
        if (userById.getPassword().equals(oldPasswordByMd5)) {
            int flag = userService.updateUserPwd(passwordByMd5, user.getId());
            if (flag == 200) {
                // 消除session中的用户信息，重新登录
                session.removeAttribute("loginUser");
            }
            return flag;
        } else {
            return 500;
        }
    }

    @PostMapping("/updateSessionInfo")
    @ResponseBody
    public int updateSessionInfo(String imgUrl,HttpSession session) {
        User user = (User) session.getAttribute("loginUser");
        user.setAvatarUrl(imgUrl);
        session.setAttribute("loginUser",user);
        // 设置session有效期：1h
        session.setMaxInactiveInterval(60*60);
        return 200;
    }


}
