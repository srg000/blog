package com.srgstart.blog.service.impl;

import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.srgstart.blog.service.SmsService;
import com.srgstart.blog.util.ConstantPropertiesUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @author srgstart
 * @Create 2021/4/5 16:49
 * @Description TODO
 */
@Service
public class SmsServiceImpl implements SmsService {


    /**
     * 发送手机验证码
     * @param phone :手机号
     * @param code 验证码
     * @param min 设置有效时间
     * @return boolean
     */
    @Override
    public Boolean send(String phone, String code, Integer min) {
        //判断手机号是否为空
        if (StringUtils.isEmpty(phone)) {
            return false;
        }

        //整合腾讯云短信服务
        try {
            // 填写短信模板中的参数
            String[] params = {code, Integer.toString(min)};

            // 发送指定参数
            SmsSingleSender ssender = new SmsSingleSender(ConstantPropertiesUtils.APP_ID, ConstantPropertiesUtils.APP_KEY);
            SmsSingleSenderResult result = ssender.sendWithParam(
                    "86", phone,
                    ConstantPropertiesUtils.TEMPLATE_ID,params,ConstantPropertiesUtils.SIGN,"","");

            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
}
