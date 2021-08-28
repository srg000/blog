package com.srgstart.blog.service;

/**
 * @author  srgstart
 * @Create 2021/8/15 16:47
 * @Description 向指定的手机用户发送随机的验证码
 */
public interface SmsService {
    /**
     * 发送手机验证码
     * @param phone :手机号
     * @param code 验证码
     * @param min 有效时间
     * @return boolean
     */
    Boolean send(String phone, String code, Integer min);
}
