package com.commentbot.pojo.dto.req;

import lombok.Data;

@Data
public class AskReq {
    private int chatId;
    private String message;
    private int botId;
}
