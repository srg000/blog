package com.srgstart.blog.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author srgstart
 * @Create 2021/8/16 14:56
 * @Description TODO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchBlogVO {
    private String title;
    private Long typeId;
    private Integer isRecommend;
    private Long userId;
}
