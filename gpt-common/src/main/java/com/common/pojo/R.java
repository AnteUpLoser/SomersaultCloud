package com.common.pojo;


import com.common.constant.ResultCode;
import lombok.Data;

/**
 * 通用返回结果，服务端响应的数据最终都会封装成此对象
 * @param <T>
 */

@Data
public final class R<T> {

    private Integer code; //返回响应码;

    private String msg; //携带的响应信息;

    private T data; //响应数据

    /**
     * 防止私有化实例
     */
    private R(){}

    /**
     * 自定义有参构造方法
     */
    public R(int code, String message, T data) {
        this.code = code;
        this.msg = message;
        this.data = data;
    }


    /**
     * 成功请求响应且没有data
     * &&自定义提示信息
     */
    public static <T> R<T> success() {
        ResultCode code = ResultCode.CREATE_SUCCESS;
        return new R< >(code.getCode(),code.getMessage(),null);
    }



    /**
     * 成功请求响应且有data
     * &&自定义提示信息
     */
    public static <T> R<T> success(T data) {
        ResultCode code = ResultCode.SUCCESS;
        return new R<>(code.getCode(), code.getMessage(), data);
    }
    public static <T> R<T> success(T data,String msg){
        return new R<>(ResultCode.SUCCESS.getCode(), msg, data);
    }
    public static <T> R<T> success(ResultCode resultCode, String msg, T data){
        return new R<>(resultCode.getCode(), msg, data);
    }

    /**
     *
     * 自定义错误响应
     */
    public static <T> R<T> error(ResultCode resultCode,String msg) {
        return new R<>(resultCode.getCode(), msg,null);
    }
    public static <T> R<T> failed(ResultCode resultCode, String msg, T data){
        return new R<>(resultCode.getCode(), msg, data);
    }





}

