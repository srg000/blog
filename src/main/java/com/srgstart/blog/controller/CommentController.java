package com.srgstart.blog.controller;

import com.srgstart.blog.entity.Comment;
import com.srgstart.blog.entity.User;
import com.srgstart.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author srgstart
 * @Create 2021/8/22 9:08
 * @Description TODO
 */
@Controller
@RequestMapping("/comment")
public class CommentController {
    public static final String NO_LOGIN_ = "no_permission/";

    @Autowired
    private CommentService commentService;

    /**
     * 根据博客id查询该博客的所有评论
     * @param blogId 博客id
     * @param model 向页面携带数据
     * @return 返回到博客详情页面
     */
    @GetMapping("/returnComments/{blogId}")
    public String returnComments(@PathVariable Long blogId, Model model) {
        model.addAttribute("comments",commentService.getCommentsById(blogId));
        return NO_LOGIN_ +"blogDetail :: commentList";
    }

    /**
     * 保存用户评论的信息，然后访问上面的接口，回显评论区的内容
     * @param comment 表单数据
     * @return 上面的接口
     */
    @PostMapping("/commitComment")
    public String commitComment(Comment comment, HttpSession session) {
       User user =  (User) session.getAttribute("loginUser");
       comment.setAvatarUrl(user.getAvatarUrl());
       comment.setNikeName(user.getNikeName());
       commentService.saveComment(comment,user.getId());
       return "redirect:/comment/returnComments/" + comment.getBlogId();
    }

}
