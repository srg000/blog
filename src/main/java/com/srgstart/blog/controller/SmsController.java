package com.srgstart.blog.controller;

import com.srgstart.blog.result.ResultApi;
import com.srgstart.blog.service.SmsService;
import com.srgstart.blog.util.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

/**
 * @author srgstart
 * @Create 2021/8/15 14:02
 * @Description 手机短信controller
 */
@RestController
@RequestMapping("/sms")
public class SmsController {

    private static final Integer MIN = 5;

    @Autowired
    private SmsService smsService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @GetMapping("/sendCode/{phone}")
    public ResultApi<Object> sendSms(@PathVariable("phone") String phone) {
        // 从redis中获取验证码，如果能获取到，返回ok
        String code = redisTemplate.opsForValue().get(phone);
        // 判断验证码是否存在，如果存在，说明已经发送过验证码了，不需要再次发送
        if ( !StringUtils.isEmpty(code) ) {
            return ResultApi.ok();
        }

        // 如果验证码不存在，需要生成验证码
        code = RandomUtil.getSixBitRandom();

        // 调用service方法，通过整合短信服务进行发送
        Boolean isSend = smsService.send(phone, code, MIN);

        // 生成的验证码放入redis中，并设置有效时间
        if (isSend) {
            redisTemplate.opsForValue().set(phone,code,MIN, TimeUnit.MINUTES);
            return ResultApi.ok();
        } else {
            return ResultApi.fail().message("发送短信失败");
        }
    }
}
