package com.commentbot.pojo.gpt;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public class GptChoices {
    @JSONField(name = "index")
    private int index;              //选项索引
    @JSONField(name = "message")
    private Message message;        //选项中的响应消息
    //@JSONField(name = "logProb")  对数概率(功能比较复杂)

    @JSONField(name = "finish_reason")
    private String finishReason;

}
