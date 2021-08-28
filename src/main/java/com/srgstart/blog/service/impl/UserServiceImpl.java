package com.srgstart.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.srgstart.blog.entity.User;
import com.srgstart.blog.exception.BlogException;
import com.srgstart.blog.mapper.UserMapper;
import com.srgstart.blog.result.ResultApi;
import com.srgstart.blog.result.ResultCodeEnum;
import com.srgstart.blog.service.UserService;
import com.srgstart.blog.util.Md5Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * @author srgstart
 * @Create 2021/8/15 8:13
 * @Description 用户service实现类，实现用户部分的业务
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Transactional(rollbackFor = BlogException.class)
    @Override
    public ResultApi<User> findUser(String username, String password) {

        // 1、参数校验(用户名)
        if (StringUtils.isEmpty(username)) {
            return ResultApi.build(null, ResultCodeEnum.USERNAME_EMPTY);
        } else {
            boolean matches = username.matches("^[\\u4e00-\\u9fa5_a-zA-Z0-9_]{2,12}$");
            if (!matches) {
                return ResultApi.build(null,ResultCodeEnum.CHECK_USERNAME);
            }
        }

        // 参数校验(密码)
        if (StringUtils.isEmpty(password)) {
            return ResultApi.build(null, ResultCodeEnum.PASSWORD_EMPTY);
        } else {
            boolean matches = password.matches("\\w{6,16}");
            if (!matches) {
                return ResultApi.build(null,ResultCodeEnum.CHECK_PASS);
            }
        }

        // 2、对密码进行MD5加密，与数据库中加完密的密码进行比对
        String newPassword = null;
        try {
            newPassword = Md5Util.encodeByMd5(password);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 3、查询数据库，判断是否存在
        User user = userMapper.findUserByUserNameAndPassword(username, newPassword);
        if (user == null) {
            return ResultApi.build(null,ResultCodeEnum.USERNAME_OR_PASSWORD_WRONG);
        }

        return ResultApi.ok(user);
    }

    @Transactional(rollbackFor = BlogException.class)
    @Override
    public ResultApi<Object> userRegister(User user) {
        /*
        1、验证码验证
         */
        // 获取redis中的验证码
        String redisCode = redisTemplate.opsForValue().get(user.getPhone());
        System.out.println(redisCode);
        // 判断用户是否要求发送验证码
        if (StringUtils.isEmpty(redisCode)) {
            return ResultApi.build(null,ResultCodeEnum.NO_CODE);
        }

        // 判断验证码是否正确
        if ( !user.getRegisterCode().equals(redisCode) ) {
            return ResultApi.build(null,ResultCodeEnum.CODE_ERROR);
        }

        /*
        2、参数验证(要对参数的规则、参数是否为空进行判断)
         */
        // 用户名校验
        String nikeName = user.getNikeName();
        if (StringUtils.isBlank(nikeName)) {
            return ResultApi.build(null,ResultCodeEnum.NAME_EMPTY_);
        } else {
            boolean matches = nikeName.matches("^[\\u4e00-\\u9fa5_a-zA-Z0-9_]{3,12}$");
            if (!matches) {
                return ResultApi.build(null,ResultCodeEnum.CHECK_USERNAME);
            }
        }

        // 判断用户名是否已经存在
        String isExist = userMapper.isExistNickname(nikeName);
        if ( !StringUtils.isBlank(isExist) ) {
            return ResultApi.build(null,ResultCodeEnum.NAME_EXIT);
        }

        // 密码校验
        String password = user.getPassword();
        if (StringUtils.isEmpty(password)) {
            return ResultApi.build(null,ResultCodeEnum.PASSWORD_EMPTY);
        } else {
            boolean matches = password.matches("\\w{6,16}");
            if (!matches) {
                return ResultApi.build(null,ResultCodeEnum.CHECK_PASS);
            }
        }

        // 手机号码校验
        String phone = user.getPhone();
        if (StringUtils.isEmpty(phone)) {
            return ResultApi.build(null,ResultCodeEnum.PHONE_EMPTY_);
        } else {
            boolean matches = phone.matches("^(?:\\+?86)?1(?:3\\d{3}|5[^4\\D]\\d{2}|8\\d{3}|7(?:[01356789]\\d{2}|4(?:0\\d|1[0-2]|9\\d))|9[189]\\d{2}|6[567]\\d{2}|4(?:[14]0\\d{3}|[68]\\d{4}|[579]\\d{2}))\\d{6}$");
            if (!matches) {
                return ResultApi.build(null,ResultCodeEnum.PHONE_WRONG);
            }
        }

        // 判断手机号是否已经被占用
        String existPhone = userMapper.isExistPhone(phone);
        if (StringUtils.isNotBlank(existPhone)) {
            return ResultApi.build(null,ResultCodeEnum.PHONE_EXIT);
        }


        /*
        3、对用户密码进行加密
         */
        String newPassword = null;
        try {
            newPassword = Md5Util.encodeByMd5(password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 将加密后的密码放入user实体类中
        user.setPassword(newPassword);

        /*
        4、向数据库中插入数据
         */
         int flag = userMapper.insert(user);
        if (flag > 0) {
            return ResultApi.build(null,ResultCodeEnum.REGISTER_SUCCESS);
        }
        return ResultApi.build(null,ResultCodeEnum.REGISTER_FAIL);
    }

    @Override
    public int updateAvatarUrlById(Long id, String avatarUrl) {
        return userMapper.updateAvatarUrlById(id, avatarUrl);
    }

    @Override
    public User findUserById(Long userId) {
        return userMapper.findUserById(userId);
    }

    @Override
    public int updateUserById(String nikeName, Long id) {
        int flag = userMapper.updateUserById(nikeName, id);
        if (flag > 0) {
            return 200;
        } else  {
            return 500;
        }
    }

    @Override
    public int updateUserPwd(String newPassword, Long id) {
        int flag = userMapper.updateUserPwd(newPassword, id);
        if (flag > 0) {
            return 200;
        } else  {
            return 500;
        }
    }




}
