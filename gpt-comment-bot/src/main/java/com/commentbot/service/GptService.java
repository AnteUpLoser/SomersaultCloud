package com.commentbot.service;

import com.commentbot.pojo.dto.req.AskReq;
import com.commentbot.pojo.dto.req.InitLabelReq;
import com.commentbot.pojo.dto.res.Generation;

public interface GptService {
    //生成第一次的评语
    Generation initGeneration(InitLabelReq initLabelReq);

    //后续对评语的微调
    Generation tuneBack(AskReq askReq);

}
