package com.commentbot.pojo.bo;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Record {
    private Long recordId;
    private long chatId;
    private String message;
    private long time;
}
