package com.common.constant;

/**
 * Redis的常量Key的命名
 */

public final class RedisConstants {
    /** 注册图片验证码 */
    public static final String REGISTER_IMG_CODE = "checkCode:register:img:";
    /** 注册邮箱验证码 */
    public static final String REGISTER_EMAIL_CODE = "checkCode:register:email:";
    /** 忘记密码邮箱验证码 */
    public static final String FIND_PWD_EMAIL_CODE = "checkCode:findPwd:email:";
    /** 用户登录状态 */
    public static final String USER_TOKEN = "user:token:";
    /** 标签机器人标签 */
    public static final String BOT_LABELS = "bot:labels:";

    public static final String CHAT = "chat:";
    /** 机器人配置 */
    public static final String BOT_CONFIG_CACHE = "bot:config:id:";









    private RedisConstants(){}
}