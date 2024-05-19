package com.commentbot.pojo.dto.res;


import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Generation {
    private String generationText;
    private String finishReason;
}
