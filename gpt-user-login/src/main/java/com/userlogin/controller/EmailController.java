package com.userlogin.controller;


import com.common.constant.ResultCode;
import com.userlogin.pojo.Email;
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



    @PostMapping("/send/register/email")
    public R<String> sendRegisterEmail(@RequestBody Email email){
        String checkCode = emailService.sendRegisterMail(email.getAddress());
        if(checkCode != null){
            return R.success(ResultCode.CREATE_SUCCESS,"发送成功",checkCode);
        }
        return R.error(ResultCode.FAILED,"发送邮件失败");
    }

    @PostMapping("/recheck/register/email")
    public R<String> recheckRegisterEmail(@RequestBody Email email){
        if(emailService.checkRegisterMail(email)){
            return R.success(ResultCode.NO_CONTENT_SUCCESS,"邮件验证码正确",null);
        }
        return R.failed(ResultCode.VALIDATE_FAILED,"邮件验证码错误",null);
    }
}
