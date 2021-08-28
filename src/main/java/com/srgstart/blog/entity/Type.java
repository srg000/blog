package com.srgstart.blog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author srgstart
 * @Create 2021/8/13 22:47
 * @Description 博客类型表
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "t_type")
public class Type {

    /**
     * 类型主键id
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 类型名
     */
    private String name;
    /**
     * 该分类下博客的数量
     */
    private Integer blogCount;
    /**
     * 逻辑删除，1：已删除，0：存在，默认0；
     */
    private Integer isDeleted;
    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
}
