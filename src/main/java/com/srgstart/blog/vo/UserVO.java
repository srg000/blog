package com.srgstart.blog.vo;

import com.srgstart.blog.entity.Blog;
import com.srgstart.blog.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author srgstart
 * @Create 2021/8/15 8:27
 * @Description userVO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVO extends User {

    /**
     * 一个用户可以有多篇博客
     */
    private List<Blog> blogList = new ArrayList<>();
}
