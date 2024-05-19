package com.commentbot.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.commentbot.pojo.bo.Record;
import com.commentbot.pojo.bo.RecordInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RecordDao extends BaseMapper<Record> {
    //插入新的对话信息并返回对话ID
    long insertNewRecord(RecordInfo recordInfo);

    //插入新的用户消息
    void insertNewAsk(Record ask);

    //插入新的机器人消息
    void insertNewGeneration(Record generation);
}
