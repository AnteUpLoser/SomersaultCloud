package com.commentbot.controller;

import com.commentbot.pojo.dto.req.AskReq;
import com.commentbot.pojo.dto.req.InitLabelReq;
import com.commentbot.pojo.dto.res.Generation;
import com.common.constant.ResultCode;
import com.common.pojo.R;
import com.commentbot.service.GptService;
import com.common.utils.jwt.JwtUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


@RestController
@Slf4j
@RequestMapping("/commentBot")
public class GptController {
    @Resource
    private GptService gptService;


    //第一次生成评语
    @PostMapping("/generate")
    public R<Generation> generate(@RequestHeader String token,
                                  @RequestBody InitLabelReq initLabelReq){
        if(!JwtUtil.isValidJwt(token)) return R.error(ResultCode.VALIDATE_FAILED, "无效用户令牌");
        Generation generation = gptService.initGeneration(initLabelReq);
        if(generation == null) return R.error(ResultCode.FAILED, "连接请求失败");
        return R.success(generation, "生成评语成功");
    }

    //后续发送微调文本(使用默认上下文大模型 可以忽略这个接口了)
    @PostMapping("/tune")
    public R<Generation> tune(@RequestHeader String token,
                              @RequestBody AskReq askReq){
        if(!JwtUtil.isValidJwt(token)) return R.error(ResultCode.VALIDATE_FAILED, "无效用户令牌");
        Generation generation = gptService.tuneBack(askReq);
        return R.success(generation, "微调成功");
    }

}
