package com.srgstart.blog.vo;

import com.srgstart.blog.entity.Blog;
import com.srgstart.blog.entity.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author srgstart
 * @Create 2021/8/13 22:47
 * @Description 博客标签表VO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagVO extends Tag {

    /**
     * 一个标签可以有多个博客
     */
    private List<Blog> blogList = new ArrayList<>();
}
