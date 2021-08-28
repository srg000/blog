package com.srgstart.blog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author srgstart
 * @Create 2021/8/17 20:55
 * @Description TODO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "t_blog_tag")
public class BlogAndTag {

    @TableId(type = IdType.AUTO)
    private Long id;
    private Long blogId;
    private Long tagId;
}
