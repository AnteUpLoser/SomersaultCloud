package com.common.constant;

/*
    public static final int SUCCESS = 200;                  //ok && 返回有效响应数据
    public static final int CREATED_SUCCESS = 201;          //ok && 表示成功创建新资源
    public static final int NO_CONTENT_SUCCESS = 204;       //ok && 无返回内容
    public static final int BAD_REQUEST_FAILED = 400;       //客户端请求问题
    public static final int UNAUTHORIZED_FAILED = 401;      //请求没有身份凭证
    public static final int FORBIDDEN_FAILED = 403;         //服务器理解请求 但是因为客户端没有权限拒绝
    public static final int SERVER_ERROR = 500;             //服务器未知错误
    public static final int SERVICE_FAILED = 503;           //服务器无法处理(维护或过载)
*/

public enum ResultCode {
    //通用
    SUCCESS(200, "成功"),
    CREATE_SUCCESS(201,"操作成功"),
    NO_CONTENT_SUCCESS(204,"无内容成功"),
    FAILED(500, "操作失败"),
    UNAUTHORIZED(401, "暂未登录或token已经过期"),
    FORBIDDEN(403, "没有相关权限"),

    //自用
    VALIDATE_FAILED(405, "参数检验失败");

    private final int code;
    private final String message;

    ResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }




}
