package com.userlogin.pojo.dto;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "user_info")
public class RegisterDto {
    private String username;
    private String password;
    private String userEmail;

}
