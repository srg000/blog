package com.srgstart.blog.exception;

import com.srgstart.blog.result.ResultCodeEnum;
import lombok.Data;

/**
 * @author srgstart
 * @Create 2021/8/13 11:12
 * @Description 自定义全局异常类
 */
@Data
public class BlogException extends RuntimeException{

    /**
     * 异常码
     */
    private Integer code;

    /**
     * 通过状态码和错误消息创建异常对象
     * @param code
     * @param message
     */
    public BlogException(Integer code, String message) {
        super(message);
        this.code = code;
    }


    /**
     * 通过枚举类型创建异常对象
     * @param resultCodeEnum
     */
    public BlogException(ResultCodeEnum resultCodeEnum) {
        super(resultCodeEnum.getMessage());
        this.code = resultCodeEnum.getCode();
    }


    /**
     * 重写toString 方法，把message加上。
     * @return
     */
    @Override
    public String toString() {
        return "BlogException{" +
                "code=" + code +
                ", message=" + this.getMessage() +
                '}';
    }
}
