package com.userlogin.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.userlogin.pojo.dto.LoginDto;


public interface LoginService extends IService<LoginDto> {
    /**
     * 普通用户登录
     */
    String userLogin(LoginDto user);

}
