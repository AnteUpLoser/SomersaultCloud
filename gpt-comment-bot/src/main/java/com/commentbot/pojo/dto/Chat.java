package com.commentbot.pojo.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.common.utils.TimeUtil;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@TableName(value = "chat")
public class Chat {
    @TableId(type = IdType.AUTO)
    public Integer chatId;
    public int userId;
    public int botId;
    public String title;
    public long lastUpdateTime;
    public int isDelete;

    //默认新建构造
    public Chat(int userId, int botId){
        this.userId = userId;
        this.botId = botId;
        this.lastUpdateTime = TimeUtil.getNowUnix();
        this.isDelete = 0;
    }
}
