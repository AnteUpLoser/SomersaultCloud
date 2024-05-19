package com.userlogin.controller;


import com.userlogin.service.LoginService;
import com.common.constant.ResultCode;
import com.userlogin.dao.LoginDao;
import com.common.pojo.R;
import com.userlogin.pojo.dto.LoginDto;
import com.common.utils.encryption.EncryptUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/user")
public class LoginController {
    @Resource
    private LoginDao loginDao;
    @Resource
    private LoginService loginService;

    @GetMapping("")
    public R<String> userLogin() {
        return R.success(ResultCode.SUCCESS, "登录成功", "");
    }


    @PostMapping("/login")
    public R<String> userLogin(@RequestBody LoginDto loginDto) {
        String realPwd = loginDao.findPasswordByUserEmail(loginDto.getUserEmail());
        if (realPwd == null) {
            return R.error(ResultCode.VALIDATE_FAILED, "用户不存在");
        }
        if (!EncryptUtil.verifyUserPwd(loginDto.getPassword(), loginDto.getUserEmail(), realPwd)) {
            return R.error(ResultCode.VALIDATE_FAILED, "密码错误");
        }

        String token = loginService.userLogin(loginDto);
        return R.success(ResultCode.SUCCESS, "登录成功", token);

    }

}
