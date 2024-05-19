package com.commentbot.pojo.bo;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.commentbot.dao.BotConfDao;
import jakarta.annotation.Resource;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@TableName(value = "bot_config")
public class BotConfig {
    @TableId(type = IdType.INPUT)
    private Long botId;
    private String initPrompt;
    private String model;
    private Integer tagId;

}
