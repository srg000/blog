package com.srgstart.blog.result;


import lombok.Data;

import java.io.Serializable;

/**
 * 全局统一返回结果类
 * @author srgstart
 */
@Data
public class ResultApi<T> implements Serializable {

    private Integer code;

    private String message;

    private T data;

    public ResultApi(){}

    protected static <T> ResultApi<T> build(T data) {
        ResultApi<T> resultApi = new ResultApi<T>();
        if (data != null) {
            resultApi.setData(data);
        }
        return resultApi;
    }

    public static <T> ResultApi<T> build(T body, ResultCodeEnum resultCodeEnum) {
        ResultApi<T> resultApi = build(body);
        resultApi.setCode(resultCodeEnum.getCode());
        resultApi.setMessage(resultCodeEnum.getMessage());
        return resultApi;
    }

    public static <T> ResultApi<T> build(Integer code, String message) {
        ResultApi<T> resultApi = build(null);
        resultApi.setCode(code);
        resultApi.setMessage(message);
        return resultApi;
    }

    public static<T> ResultApi<T> ok(){
        return ResultApi.ok(null);
    }

    /**
     * 操作成功
     * @param data:传入的数据
     * @param <T> ：泛型
     * @return 返回一个ResultApi
     */
    public static<T> ResultApi<T> ok(T data){
        ResultApi<T> resultApi = build(data);
        return build(data, ResultCodeEnum.SUCCESS);
    }

    public static<T> ResultApi<T> fail(){
        return ResultApi.fail(null);
    }

    /**
     * 操作失败
     * @param data:传入的数据
     * @param <T> ：泛型
     * @return 返回一个ResultApi
     */
    public static<T> ResultApi<T> fail(T data){
        ResultApi<T> resultApi = build(data);
        return build(data, ResultCodeEnum.FAIL);
    }

    public static<T> ResultApi<T> fail(T data,ResultCodeEnum resultCodeEnum){
        ResultApi<T> resultApi = build(data);
        resultApi.setCode(resultCodeEnum.getCode());
        resultApi.setMessage(resultCodeEnum.getMessage());
        return resultApi;
    }

    public ResultApi<T> message(String msg){
        this.setMessage(msg);
        return this;
    }

    public ResultApi<T> code(Integer code){
        this.setCode(code);
        return this;
    }

    public boolean isOk() {
        return this.getCode().intValue() == ResultCodeEnum.SUCCESS.getCode().intValue();
    }
}
