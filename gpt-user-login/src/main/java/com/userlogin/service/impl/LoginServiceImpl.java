package com.userlogin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.common.constant.RedisConstants;
import com.userlogin.pojo.dto.LoginDto;
import com.userlogin.service.LoginService;
import com.common.redis.RedisService;
import com.common.utils.jwt.JwtUtil;
import com.userlogin.dao.LoginDao;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Slf4j
@Service
public class LoginServiceImpl extends ServiceImpl<LoginDao, LoginDto> implements LoginService {
    @Resource
    private RedisService redisService;
    @Resource
    private LoginDao loginDao;

    public String userLogin(LoginDto user) {
        //已登录情况直接返回缓存token
        if(redisService.isContainsKey(RedisConstants.USER_TOKEN+user.getUserEmail())){
            return redisService.getValue(RedisConstants.USER_TOKEN+user.getUserEmail());
        }

        Map<String, Object> tokenMap = new HashMap<>();
        //token携带用户id
        int uid = loginDao.findUidByEmail(user.getUserEmail());
        tokenMap.put("uid",uid);
        String token = JwtUtil.createJwt(tokenMap);

        //TODO 待改：用户token时长
        //将登录用户token存入缓存
        redisService.setHalfHourValue(RedisConstants.USER_TOKEN+user.getUserEmail(),token);
        return token;
    }
}
