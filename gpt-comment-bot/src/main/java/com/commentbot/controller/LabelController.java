package com.commentbot.controller;

import com.commentbot.pojo.dto.req.BotId;
import com.commentbot.pojo.vo.LabelVo;
import com.commentbot.service.LabelService;
import com.common.pojo.R;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

@Slf4j
@RestController
public class LabelController {
    @Resource
    private LabelService labelService;

    @PostMapping("/bot/labels")
    public R<List<LabelVo>> getBotLabels(@RequestBody BotId req){
        return R.success(labelService.getBotLabels(req.getBot_id()));
    }

}
