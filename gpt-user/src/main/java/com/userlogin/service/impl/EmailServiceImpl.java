package com.userlogin.service.impl;

import cn.hutool.extra.mail.MailUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.common.utils.encryption.EncryptUtil;
import com.userlogin.dao.UserDao;
import com.userlogin.pojo.EmailCode;
import com.common.redis.RedisService;
import com.common.constant.RedisConstants;
import com.userlogin.pojo.User;
import com.userlogin.pojo.dto.EmailFindPwdDto;
import com.userlogin.service.EmailService;
import com.common.utils.checkcode.CheckCodeUtil;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class EmailServiceImpl implements EmailService {
    @Resource
    private RedisService redisService;
    @Resource
    private UserDao userDao;


    /**
     * 邮件的subject头
     */
    private static final String subject = "[SomersaultCloud]";


    /**
     * 发送注册邮箱验证码
     */
    public String sendRegisterMail(String mailAddress) {
        String checkCode = CheckCodeUtil.generateVerifyCode(6, null);
        //选择注册模板并发送
        renderAndSendMail(mailAddress, "register-email.ftl", checkCode);
        //缓存验证码
        redisService.setValueByMin(RedisConstants.REGISTER_EMAIL_CODE + mailAddress, checkCode, 10);
        return checkCode;
    }


    /**
     * 校验注册邮箱验证码
     */
    public boolean recheckRegisterMailCode(EmailCode email) {
        String rightCode = redisService.getValue(RedisConstants.REGISTER_EMAIL_CODE + email.getAddress());
        //忽略大小写
        //校验后兑消缓存验证码
        if (email.getEmailCode().equalsIgnoreCase(rightCode)) {
            redisService.deleteValue(RedisConstants.REGISTER_EMAIL_CODE + email.getAddress());
            return true;
        } else {
            return false;
        }
    }

    /**
     * 发送忘记密码邮箱验证码
     */
    public String sendForgotPwdMail(String mailAddress){
        String checkCode = CheckCodeUtil.generateVerifyCode(6, null);
        //选择注册模板并发送
        renderAndSendMail(mailAddress, "findPwd-email.ftl", checkCode);
        //缓存验证码
        redisService.setValueByMin(RedisConstants.FIND_PWD_EMAIL_CODE + mailAddress, checkCode, 10);
        return checkCode;
    }

    /**
     * 校验忘记密码邮箱验证码 && 修改密码
     */
    public boolean recheckForgotPwdMailCode(EmailFindPwdDto emailFindPwdDto) {
        String rightCode = redisService.getValue(RedisConstants.FIND_PWD_EMAIL_CODE + emailFindPwdDto.getAddress());
        //忽略大小写
        //校验后兑消缓存验证码
        if (emailFindPwdDto.getEmailCode().equalsIgnoreCase(rightCode)) {
            redisService.deleteValue(RedisConstants.FIND_PWD_EMAIL_CODE + emailFindPwdDto.getAddress());
            //修改密码
            String resetPwd = EncryptUtil.SHAForUserPwd(emailFindPwdDto.getResetPwd(), emailFindPwdDto.getAddress());

            UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("user_email", emailFindPwdDto.getAddress())
                    .set("password", resetPwd);
            userDao.update(new User(), updateWrapper);
            return true;
        } else {
            return false;
        }
    }


    /**
     * 选择渲染模板并发送邮件
     *
     * @param toAddress 收件人地址
     * @param template  模板ftl的路径
     * @param checkCode 验证码
     */
    public void renderAndSendMail(String toAddress, String template, String checkCode) {
        //模板渲染
        Configuration config = new Configuration(Configuration.VERSION_2_3_0);
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        // 设置模板文件所在的类路径位置
        config.setClassLoaderForTemplateLoading(classLoader, "/template");
        // 加载模板文件
        Template model;
        try {
            model = config.getTemplate(template);
            Map<String, String> map = new HashMap<>();
            map.put("checkCode", checkCode);
            // 渲染模板
            StringWriter writer = new StringWriter();
            model.process(map, writer);
            MailUtil.send(toAddress, subject, writer.toString(), true);
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }

    }

}
