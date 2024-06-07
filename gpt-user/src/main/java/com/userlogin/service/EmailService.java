package com.userlogin.service;

import com.userlogin.pojo.EmailCode;
import com.userlogin.pojo.dto.EmailFindPwdDto;


public interface EmailService {
    //注册邮箱验证码的发送
    String sendRegisterMail(String mailAddress);

    //校验注册邮箱的验证码
    boolean recheckRegisterMailCode(EmailCode email);

    //发送找回密码邮箱
    String sendForgotPwdMail(String mailAddress);

    //验证找回密码邮箱验证码
    boolean recheckForgotPwdMailCode(EmailFindPwdDto emailFindPwdDto);

}
