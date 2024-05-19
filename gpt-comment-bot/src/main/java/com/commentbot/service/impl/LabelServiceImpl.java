package com.commentbot.service.impl;

import com.alibaba.fastjson.JSON;
import com.commentbot.dao.LabelDao;
import com.commentbot.pojo.vo.LabelVo;
import com.commentbot.service.LabelService;
import com.common.constant.RedisConstants;
import com.common.redis.RedisService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class LabelServiceImpl implements LabelService {
    @Resource
    private RedisService redisService;
    @Resource
    private LabelDao labelDao;

    public List<LabelVo> getBotLabels(int botId) {
        return fetchAndCacheLabels(botId);
    }

    //根据传入botId查找缓存相关标签
    public List<LabelVo> fetchAndCacheLabels(int botId){
        String redisKey = RedisConstants.BOT_LABELS + botId;
        String labels = redisService.getValue(redisKey);
        //特判 有缓存直接返回
        if(labels != null) return new ArrayList<>(JSON.parseArray(labels, LabelVo.class));

        //无缓存查找后，缓存再返回
        List<LabelVo> labelList = labelDao.getMaxLabels(botId);
        for (LabelVo curVo : labelList) {
            curVo.setChildLabels(labelDao.getChildrenByParentId(curVo.getId()));
        }
        String redisValue = JSON.toJSONString(labelList);
        redisService.setHalfHourValue(redisKey, redisValue);
        return labelList;
    }
}
