package com.userlogin.pojo.dto;

import lombok.Data;

@Data
public class EmailFindPwdDto {
    private String address;
    private String emailCode;
    private String resetPwd;
}
