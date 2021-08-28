package com.srgstart.blog.vo;

import com.srgstart.blog.entity.Blog;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author srgstart
 * @Create 2021/8/23 22:00
 * @Description TODO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArchivesVO {
    /**
     * 归档年份划分
     */
    private String year;
    /**
     * 每个年份对应的博客数量
     */
    private Integer blogCount;
}
