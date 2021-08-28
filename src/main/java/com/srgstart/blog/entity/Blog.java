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
 * @Create 2021/8/13 21:17
 * @Description 博客类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "t_blog")
public class Blog {

    /**
     * 博客主键id
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 博客标题
     */
    private String title;
    /**
     * 博客内容
     */
    private String content;
    /**
     * 用户id
     */
    private Long userId;
    /**
    /**
     * 博客图片
     */
    private String firstPicture;
    /**
     * 博客分类Id
     */
    private Long typeId;
    /**
     * 博客标识：原创、转载、翻译
     */
    private String flag;
    /**
     * 博客浏览量（默认为0）
     */
    private Integer views;
    /**
     * 是否在博客页面开启打赏功能（1:开启, 0:禁止，默认为1，）
     */
    private Integer isAppreciate;
    /**
     * 是否在博客页面开启转载声明功能（1:开启, 0:禁止，默认为1，）
     */
    private Integer shareStatement;
    /**
     * 是否在博客页面开启评论功能（1:开启, 0:禁止，默认为1，）
     */
    private Integer isComment;
    /**
     * 博客的状态：保存(未发布)、发布（2:发布, 1:仅保存，默认为0，）
     */
    private Integer status;
    /**
     * 是否开启推荐功能（1:开启, 0:禁止，默认为1，）
     */
    private Integer isRecommend;
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
