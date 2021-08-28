package com.srgstart.blog.util;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author srgstart
 * @Create 2021/4/5 16:40
 * @Description TODO
 */
@Component
public class ConstantPropertiesUtils implements InitializingBean {

    @Value("${sms.appId}")
    private Integer appId;

    @Value("${sms.appKey}")
    private String appKey;

    @Value("${sms.templateId}")
    private Integer templateId;

    @Value("${sms.sign}")
    private String sign;

    public static Integer APP_ID;
    public static String APP_KEY;
    public static Integer TEMPLATE_ID;
    public static String SIGN;

    @Override
    public void afterPropertiesSet() throws Exception {
        APP_ID = appId;
        APP_KEY = appKey;
        TEMPLATE_ID = templateId;
        SIGN = sign;
    }
}
