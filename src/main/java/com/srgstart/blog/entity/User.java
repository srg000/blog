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
 * @Create 2021/8/13 23:00
 * @Description 用户表
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "t_user")
public class User {

    /**
     * 用户Id
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 用户的昵称
     */
    private String nikeName;
    /**
     * 用户的邮箱
     */
    private String phone;
    /**
     * 用户密码
     */
    private String password;
    /**
     * 用户的头像url
     */
    private String avatarUrl;

    private String registerCode;
    /**
     * 用户类型(0:普通用户，1：管理员)
     */
    private Integer type;
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
}
