package com.commentbot.pojo.bo;


import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

@Data
@TableName(value = "record_info")
public class RecordInfo {
    @TableId(type = IdType.AUTO)
    private Long recordId;
    private long chatId;
    private Long createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long referenceId;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Integer referenceToken;
}
