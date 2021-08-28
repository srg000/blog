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
 * @Create 2021/8/13 22:56
 * @Description 评论表
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "t_comment")
public class Comment {

    /**
     * 评论Id
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 该条评论对应的博客
     */
    private Long blogId;
    /**
     * 评论人的昵称
     */
    private String nikeName;
    /**
     * 评论人的邮箱
     */
    private String email;
    /**
     * 评论人评论的内容
     */
    private String content;
    /**
     * 评论人的头像url
     */
    private String avatarUrl;
    /**
     * 逻辑删除（1：已删除，0：未删除，默认为0）
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

    /**
     * 该条评论的级别（默认为1，表示第一层评论者，有自评论者依次加1）
     */
    private Integer level;

    /**
     * 该条评论的父Id(默认为0，)
     */
    private Long parentId;

    /**
     * 被回复人的昵称
     */
    private String replyName;

    /**
     * 是否是博主的评论
     */
    private Integer isBlogger;


}
