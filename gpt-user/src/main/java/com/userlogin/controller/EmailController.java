package com.userlogin.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.common.constant.ResultCode;
import com.userlogin.dao.UserDao;
import com.userlogin.pojo.EmailCode;
import com.userlogin.pojo.User;
import com.userlogin.pojo.dto.EmailFindPwdDto;
import com.userlogin.service.EmailService;
import com.common.pojo.R;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class EmailController {
    @Resource
    private EmailService emailService;
    @Resource
    private UserDao userDao;



    @PostMapping("/send/register/email")
    public R<String> sendRegisterEmail(@RequestBody EmailCode email){
        String checkCode = emailService.sendRegisterMail(email.getAddress());
        if(checkCode != null){
            return R.success(ResultCode.CREATE_SUCCESS,"发送成功",checkCode);
        }
        return R.error(ResultCode.FAILED,"发送邮件失败");
    }

    @PostMapping("/recheck/register/email")
    public R<String> recheckRegisterEmail(@RequestBody EmailCode email){
        if(emailService.recheckRegisterMailCode(email)){
            return R.success(ResultCode.NO_CONTENT_SUCCESS,"邮件验证码正确",null);
        }
        return R.failed(ResultCode.VALIDATE_FAILED,"邮件验证码错误",null);
    }

    @PostMapping("/send/forgotPwd/email")
    public R<String> sendFindPwdEmail(@RequestBody EmailCode email){
        if(userDao.selectOne(new QueryWrapper<User>().eq("user_email",email.getAddress())) == null)
            return R.failed(ResultCode.PARAMETER_NOT_EXIST, "邮箱未注册", email.getAddress());

        String checkCode = emailService.sendForgotPwdMail(email.getAddress());
        if(checkCode != null){
            return R.success(ResultCode.CREATE_SUCCESS,"发送成功",checkCode);
        }
        return R.error(ResultCode.FAILED,"发送邮件失败");
    }


    @PostMapping("/recheck/forgotPwd/email")
    public R<String> recheckForgotPwdEmail(@RequestBody EmailFindPwdDto emailFindPwdDto){
        if(emailService.recheckForgotPwdMailCode(emailFindPwdDto)){
            return R.success(ResultCode.NO_CONTENT_SUCCESS,"密码修改成功",null);
        }
        return R.error(ResultCode.VALIDATE_FAILED,"邮件验证码错误");
    }
}
