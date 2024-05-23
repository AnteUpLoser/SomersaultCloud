package com.userlogin.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class User {
    //用户id
    @TableId(type = IdType.AUTO)
    private Integer userId;
    //用户名
    private String username;
    //用户密码
    private String password;
    //gender(可null)


    //用户邮箱(可null)
    private String userEmail;
    //bio(可null)

    //ISSUE 用户手机号码（网站手机短信发送需要域名） 待定
}
