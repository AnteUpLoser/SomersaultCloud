package com.userlogin.pojo.dto;


import lombok.Data;


//由邮箱和密码登录
@Data
public class LoginDto {
    private String userEmail;
    private String password;
}
