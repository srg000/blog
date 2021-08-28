package com.srgstart.blog.result;

import lombok.Getter;

/**
 * @author srgstart
 * @Create 2021/8/13 11:05
 * @Description 统一返回结果状态信息类：2xx:成功。3xx: 提示。 5xx: 报错。404：找不到。
 */
@Getter
public enum ResultCodeEnum {
    /**
     * 请求未找到
     */
    NOT_FOUND(404,"请求未能找到！"),
    /**
     * 操作成功
     */
    SUCCESS(200,"操作成功"),
    /**
     * 操作失败
     */
    FAIL(300, "操作失败"),


    /*================用户部分====================*/
    /**
     * 用户名不能为空
     */
    USERNAME_EMPTY(301,"用户名不能为空"),
    /**
     * 密码不能为空
     */
    PASSWORD_EMPTY(302,"密码不能为空"),
    /**
     * 密码由6-16位数字、字母组成
     */
    CHECK_PASS(303,"密码由6-16位数字、字母组成"),
    /**
     * 用户有3-12位数字、字母组成
     */
    CHECK_USERNAME(304,"用户由2-12位数字、字母、中文组成"),
    /**
     * 用户名或者密码错误
     */
    USERNAME_OR_PASSWORD_WRONG(305,"用户名或者密码错误"),
    /**
     * 验证码错误
     */
    CODE_ERROR(306, "验证码错误"),
    /**
     * 未请求发送验证码
     */
    NO_CODE(307,"未请求发送验证码"),
    /**
     * 该用户名已被占用
     */
    NAME_EXIT(308,"该用户名已被占用"),
    /**
     * 用户名称不能为空
     */
    NAME_EMPTY_(309,"用户名称不能为空！"),
    /**
     * 该手机号已被占用
     */
    PHONE_EXIT(310,"该手机号已被占用"),
    /**
     * 该邮箱已被占用
     */
    EMAIL_EXIT(311,"该邮箱已被占用"),
    /**
     * 手机号不能为空
     */
    PHONE_EMPTY_(312,"手机号不能为空！"),
    /**
     * 手机号非法
     */
    PHONE_WRONG(312,"手机号非法！"),
    /**
     * 注册成功
     */
    REGISTER_SUCCESS(201, "注册成功"),

    /**
     * 注册失败
     */
    REGISTER_FAIL(313, "注册失败")

    ;


    private final Integer code;
    private final String message;


    ResultCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
