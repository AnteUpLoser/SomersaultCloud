package com.commentbot.pojo.bo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("comment_label")
public class Label {
    private Integer id;
    private Integer parentId;
    private String labelContent;
    private int labelLevel;
    private int botId;
}
