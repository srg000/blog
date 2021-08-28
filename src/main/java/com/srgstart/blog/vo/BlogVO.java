package com.srgstart.blog.vo;

import com.srgstart.blog.entity.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author srgstart
 * @Create 2021/8/13 21:17
 * @Description 博客类VO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlogVO extends Blog {
    /**
     * 博客分类，一个博客只有一个分类
     */
    private Type blogType;

    /**
     * 博客标签：一个博客可以有多个标签
     */
    private List<Tag> tagList = new ArrayList<>();

    /**
     * 博客所属人，一个博客归属一个用户
     */
    private User user;

    /**
     * 一篇博客可以有多条评论
     */
    private List<Comment> commentList = new ArrayList<>();

    private String parseUpdateTime;

    private String tagIds;





}
