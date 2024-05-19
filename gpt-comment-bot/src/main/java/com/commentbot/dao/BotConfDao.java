package com.commentbot.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.commentbot.pojo.bo.BotConfig;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BotConfDao extends BaseMapper<BotConfig> {

}
