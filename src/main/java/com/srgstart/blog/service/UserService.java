package com.srgstart.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.srgstart.blog.entity.User;
import com.srgstart.blog.result.ResultApi;
import com.srgstart.blog.vo.UserVO;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * @author srgstart
 * @Create 2021/8/15 8:13
 * @Description 用户service实现类，定义业务接口方法
 */
public interface UserService extends IService<User> {
    /**
     * 根据username和password查询用户
     * @param username：用户名
     * @param password：密码
     * @return ：userVO对象
     */
    ResultApi<User> findUser(@Param("username") String username, @Param("password") String password);


    /**
     * 用户注册
     * @param user：注册表单数据
     * @return message消息
     */
    ResultApi<Object> userRegister(User user);

    /**
     * 根据用户id修改用户头像
     * @param id id
     * @param avatarUrl 要修改成的头像url
     * @return 是否修改成功
     */
    int updateAvatarUrlById(Long id, String avatarUrl);

    /**
     * 根据用户id查询用户
     * @param userId 用户id
     * @return 用户信息
     */
    User findUserById(@Param("userId") Long userId);

    /**
     * 根据用户id修改用户昵称
     * @param nikeName 用户昵称
     * @param id 用户id
     * @return 是否修改成功
     */
    int updateUserById(String nikeName, Long id);

    /**
     * 根据用户id修改用户密码
     * @param newPassword 用户密码
     * @param id 用户id
     * @return 是否修改成功
     */
    int updateUserPwd(@Param("newPassword") String newPassword,
                      @Param("id") Long id);


}
