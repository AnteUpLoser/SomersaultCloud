package com.userlogin.service.impl;

import com.common.constant.RedisConstants;
import com.userlogin.pojo.CheckCode;
import com.common.redis.RedisService;
import com.userlogin.service.CheckCodeService;
import com.common.utils.checkcode.CheckCodeUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.Map;

@Service
public class CheckCodeServiceImpl implements CheckCodeService {
    @Resource
    private RedisService redisService;

    public String getCheckCodeImg(CheckCode checkCode) {
        //客户端发的sessionID
        String sessionID = checkCode.getSessionID();
        try {
            Map<String,String> map = CheckCodeUtil.outputVerifyImage(checkCode.getWidth(), checkCode.getHeight(), checkCode.getVerifySize());
            String verifyCode = map.get("verifyCode");
            String Base64 = map.get("Base64");
            //设置了验证码有效期为一分钟
            redisService.setOneMinValue(RedisConstants.REGISTER_IMG_CODE+sessionID, verifyCode);
            return Base64;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "验证码生成错误";
    }

    public String recheckCodeIsTrue(String sessionID, String checkCode) {
        //获取正确的验证码
        String theRightCode = redisService.getValue(RedisConstants.REGISTER_IMG_CODE+sessionID);
        if(theRightCode == null) {
            return null;
        }
        if(checkCode.equalsIgnoreCase(theRightCode)){
            //核销验证码
            redisService.deleteValue(RedisConstants.REGISTER_IMG_CODE+sessionID);
            return "true";
        }
        return "false";
    }
}
