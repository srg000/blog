package com.srgstart.blog.controller;

import com.srgstart.blog.entity.Blog;
import com.srgstart.blog.service.*;
import com.srgstart.blog.util.HtmlToPlainText;
import com.srgstart.blog.util.MarkdownToHtml;
import com.srgstart.blog.util.MarkdownUtils;
import com.srgstart.blog.vo.BlogVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;


/**
 * @author srgstart
 * @Create 2021/8/13 11:03
 * @Description 游客权限controller
 */
@Controller
public class IndexController {
    public static final String NO_LOGIN_ = "no_permission/";
    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private UserService userService;
    @Autowired
    private CommentService commentService;


    /**
     * @return : 指定no_permission下的页面
     */
    @GetMapping("/login")
    public String goLogin() {
        return  NO_LOGIN_ + "login";
    }

    /**
     * @return : 指定no_permission下的页面
     */
    @GetMapping("/register")
    public String goRegister() {
        return  NO_LOGIN_ + "register";
    }


    /**
     * 前往博客首页
     * @return index页面
     */
    @GetMapping("/index")
    public String goIndex() {
        return "redirect:/blog/getAllBlog/0/9";
    }

    /**
     * 前往博客详情页
     * @param blogId 博客id
     * @param model 携带数据到页面
     * @return blogDetail
     */
    @GetMapping("/getBlogById/{blogId}")
    public String getBlogById(@PathVariable Long blogId, Model model) {
        Blog blog = blogService.getBlogById(blogId);
        BlogVO blogVO = new BlogVO();
        BeanUtils.copyProperties(blog,blogVO);
        // 将type对象填充进来
        blogVO.setBlogType(typeService.getTypeById(blog.getTypeId()));

        // 将博客内容从markdown语法，转换为html
        String htmlContent = MarkdownUtils.markdownToHtmlExtensions(blogVO.getContent());

        blogVO.setContent(htmlContent);

        // 将每篇文章的作者添加到vo中
        blogVO.setUser(userService.getById((blogVO.getUserId())));

        // 把更新时间格式化
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);

        //java.util.Date对象
        Date date = null;
        try {
            date = (Date) sdf.parse(blog.getUpdateTime().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //2009-09-16 11:26:23
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = dateFormat.format(date);
        blogVO.setParseUpdateTime(format);

        model.addAttribute("blogDetail",blogVO);
        model.addAttribute("comments",commentService.getCommentsById(blogId));

        return NO_LOGIN_ + "blogDetail";
    }

    /**
     * 到关于作者（我）
     * @return aboutMe
     */
    @GetMapping("/aboutMe")
    public String aboutMe() {
        return NO_LOGIN_ + "aboutMe";
    }

    /**
     * 关于社团
     * @return aboutCommunity
     */
    @GetMapping("/aboutCommunity")
    public String aboutCommunity() {
        return NO_LOGIN_ + "aboutCommunity";
    }

    /**
     * 前往博客main页面
     * @return main
     */
    @GetMapping(value = {"/main","/"})
    public String goMain(Model model) {
        List<Blog> blogList = blogService.getBlogBySort(3);
        for (Blog blog : blogList) {
            // 将博客内容从markdown语法，转换为纯文本
            String htmlContent = MarkdownToHtml.convert(blog.getContent());
            String content = HtmlToPlainText.convert(htmlContent);
            // 不是全部显示文本，要截取显示
            String finalContent = content.substring(0,60) + "...";

            blog.setContent(finalContent);
        }
        model.addAttribute("blogList",blogList);
        return NO_LOGIN_ + "main";
    }

    /**
     * 注销
     * @return 注销成功后，返回的string
     */
    @GetMapping("/logout")
    @ResponseBody
    public int logout(HttpServletRequest request) {
        request.getSession().removeAttribute("loginUser");
        return 200;
    }

    @GetMapping("/test")
    public String test() {
        return NO_LOGIN_ + "archive";
    }
}
