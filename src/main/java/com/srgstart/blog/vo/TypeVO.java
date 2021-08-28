package com.srgstart.blog.vo;

import com.srgstart.blog.entity.Blog;
import com.srgstart.blog.entity.Type;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author srgstart
 * @Create 2021/8/13 22:47
 * @Description 博客类型表VO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TypeVO extends Type {

    /**
     * 一个分类下可以有多个博客
     */
    private List<Blog> blogList = new ArrayList<>();
}
