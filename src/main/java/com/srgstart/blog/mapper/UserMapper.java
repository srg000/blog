package com.srgstart.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.srgstart.blog.entity.User;
import com.srgstart.blog.vo.UserVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author srgstart
 * @Create 2021/8/15 8:14
 * @Description 用户的DAO层，使用了mybatis-plus
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    /**
     * 根据用户名和密码查询用户
     * @param username : 用户名
     * @param password : 密码
     * @return : 返回一个UserVO
     */
    User findUserByUserNameAndPassword(@Param("username") String username,
                                         @Param("password") String password);


    /**
     * 判断该用户名是否存在
     * @param nickName：提供的用户名
     * @return 存在就返回，不存在就返回空字符
     */
    String isExistNickname(@Param("nickName") String nickName);


    /**
     * 判断该手机号是否存在
     * @param phone：提供的手机号
     * @return 存在就返回，不存在就返回空字符
     */
    String isExistPhone(@Param("phone") String phone);

    /**
     * 根据用户id查询姓名
     * @param userId 用户id
     * @return 姓名
     */
    String findNameById(@Param("userId") Long userId);

    /**
     * 根据用户id查询用户
     * @param userId 用户id
     * @return 用户信息
     */
    User findUserById(@Param("userId") Long userId);

    /**
     * 根据用户id修改用户头像
     * @param id id
     * @param avatarUrl 要修改成的头像url
     * @return 是否修改成功
     */
    int updateAvatarUrlById(@Param("id") Long id,
                            @Param("avatarUrl") String avatarUrl);


    /**
     * 根据用户id修改用户昵称
     * @param nikeName 用户昵称
     * @param id 用户id
     * @return 是否修改成功
     */
    int updateUserById(@Param("nikeName") String nikeName,
                       @Param("id") Long id);

    /**
     * 根据用户id修改用户密码
     * @param newPassword 用户密码
     * @param id 用户id
     * @return 是否修改成功
     */
    int updateUserPwd(@Param("newPassword") String newPassword,
                      @Param("id") Long id);

}
