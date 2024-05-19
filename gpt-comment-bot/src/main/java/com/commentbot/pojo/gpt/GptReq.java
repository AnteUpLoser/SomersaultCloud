package com.commentbot.pojo.gpt;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;

@Data
public class GptReq {
    private String model;
    private ArrayList<Message> messages;

    public GptReq(String userPrompt, String sysPrompt, String model){
        Message systemMessage = new Message().setSysMessage(sysPrompt);
        Message userMessage = new Message().setUserMessage(userPrompt);
        ArrayList<Message> messages = new ArrayList<>();
        messages.add(systemMessage);
        messages.add(userMessage);
        this.messages = messages;
        this.model = model;
    }
}
