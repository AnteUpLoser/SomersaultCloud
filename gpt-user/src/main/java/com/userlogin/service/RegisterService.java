package com.userlogin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.userlogin.pojo.dto.RegisterDto;


public interface RegisterService extends IService<RegisterDto> {
    /**
     * 用户注册
     * @return jwt
     */
    String userRegister(RegisterDto registerDto);

}
