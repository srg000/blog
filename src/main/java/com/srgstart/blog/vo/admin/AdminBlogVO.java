package com.srgstart.blog.vo.admin;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author srgstart
 * @Create 2021/8/26 22:44
 * @Description TODO
 */
@Data
@AllArgsConstructor
public class AdminBlogVO {
    private String createTime;
    private String title;
    private String author;
    private String type;
}
