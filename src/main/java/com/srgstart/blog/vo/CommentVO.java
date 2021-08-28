package com.srgstart.blog.vo;

import com.srgstart.blog.entity.Blog;
import com.srgstart.blog.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author srgstart
 * @Create 2021/8/13 22:56
 * @Description 评论表VO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentVO extends Comment{
    /**
     * 一条评论只能对应一篇博客
     */
    private Blog blog;

    /**
     * 一条评论又可以包含多个回复评论
     */
    private List<Comment> replyComments = new ArrayList<>();


}
