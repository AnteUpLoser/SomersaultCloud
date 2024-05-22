package com.commentbot.service.impl;


import com.alibaba.fastjson.JSON;
import com.commentbot.dao.BotConfDao;
import com.commentbot.dao.ChatDao;
import com.commentbot.dao.LabelDao;
import com.commentbot.dao.RecordDao;
import com.commentbot.pojo.bo.BotConfig;
import com.commentbot.pojo.bo.Record;
import com.commentbot.pojo.bo.RecordInfo;
import com.commentbot.pojo.dto.InitLabelReqDto;
import com.commentbot.pojo.dto.req.AskReq;
import com.commentbot.pojo.dto.req.InitLabelReq;
import com.commentbot.pojo.dto.res.Generation;
import com.commentbot.pojo.gpt.GptChoices;
import com.commentbot.pojo.gpt.GptReq;
import com.commentbot.pojo.gpt.GptRes;
import com.commentbot.pojo.gpt.Message;
import com.commentbot.service.GptService;
import com.common.constant.RedisConstants;
import com.common.redis.RedisService;
import com.common.utils.TimeUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class GptServiceImpl implements GptService {
    @Resource
    private ChatDao chatDao;
    @Resource
    private RedisService redisService;
    @Resource
    private LabelDao labelDao;
    @Resource
    private BotConfDao botConfDao;
    @Resource
    private RecordDao recordDao;

    //gpt请求配置
    @Value("${bot.sk}")
    private String SK;
    @Value("${bot.api-url}")
    private String API_URL;
    @Value("${bot.proxy-port}")
    private int PROXY_PORT;
    @Value("${bot.proxy-hostname}")
    private String PROXY_HOSTNAME;

    /**
     * 第一次生成评语 业务实现
     * @param initLabelReq 设计的前端请求体
     * @return generation 响应体
     */
    @Override
    public Generation initGeneration(InitLabelReq initLabelReq) {
        //获取机器人配置信息
        BotConfig botConfig = fetchAndCacheBotConfig(initLabelReq.getBotId());

        //获取标签的内容
        String performance = labelDao.getLabelContentList(initLabelReq.getPerfLabelIds()).toString();
        String studySugLabelContent = labelDao.getLabelContentList(initLabelReq.getStudySugLabelIds()).toString();
        String coopSugLabelContent = labelDao.getLabelContentList(initLabelReq.getCoopSugLabelIds()).toString();

        //拼接内容
        String studySug = studySugLabelContent + initLabelReq.getAddiStudySug();
        String coopSug = coopSugLabelContent + initLabelReq.getAddiCoopSug();

        //组装请求字符串内容
        InitLabelReqDto reqDto = new InitLabelReqDto();
        reqDto.setStuName(initLabelReq.getStuName())
                .setPracticeContent(initLabelReq.getPracticeContent())
                .setPerformance(performance)
                .setStudySug(studySug)
                .setCoopSug(coopSug);
        String reqString = reqDto.getStuName()
                +reqDto.getPracticeContent()
                +reqDto.getPerformance()
                +reqDto.getStudySug()
                +reqDto.getCoopSug();

        //发送得到响应的GptRes
        GptRes gptRes = sendMsg(reqString, botConfig);
        //特判请求gpt失败
        if(gptRes == null) return null;

        //组装响应体
        Generation resGeneration = new Generation();

        GptChoices choices = gptRes.getChoices().get(0);
        resGeneration.setGenerationText(choices.getMessage().getContent())
                .setFinishReason(choices.getFinishReason());

        //异步新增聊天记录
        asyncAddGenerationMsg(initLabelReq, resGeneration.getGenerationText());

        //TODO 存进redis上下文 可做RPC
//        long chatId = initLabelReq.getChatId();
//        redisService.setHalfHourValue(RedisConstants.CHAT+chatId, );

        return resGeneration;
    }

    /**
     * 获取botConfig
     * @param botId 机器人id
     * @return 机器人配置信息 botConfig
     */
    public BotConfig fetchAndCacheBotConfig(long botId){
        String botConfigCache = redisService.getValue(RedisConstants.BOT_CONFIG_CACHE+botId);
        if(botConfigCache != null) return JSON.parseObject(botConfigCache, BotConfig.class);
        return botConfDao.selectById(botId);
    }




    /**
     * 异步操作数据库
     *   新增聊天记录
     */
    private void asyncAddGenerationMsg(InitLabelReq dto, String generationText){
        //新增对话记录 同步得到新对话id
        RecordInfo recordInfo = new RecordInfo();
        recordInfo.setChatId(dto.getChatId());
        recordDao.insertNewRecord(recordInfo);

        //新增响应消息记录 (异步)
        Record generation = new Record();
        generation.setRecordId(recordInfo.getRecordId())
                .setChatId(dto.getChatId())
                .setMessage(generationText)
                .setTime(TimeUtil.getNowUnix());
        asyncSaveGeneration(generation);
    }
    private void asyncSaveGeneration(Record generation) {
        CompletableFuture.runAsync(() -> recordDao.insertNewGeneration(generation));
    }

    private void asyncSaveAsk(Record ask){
        CompletableFuture.runAsync(() -> recordDao.insertNewAsk(ask));
    }







    //请求官方api的函数
    public GptRes sendMsg(String reqString, BotConfig botConfig){
        // 创建代理服务器的主机和端口
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(PROXY_HOSTNAME, PROXY_PORT));
        // 创建 OkHttpClient.Builder 实例，并配置代理
        OkHttpClient.Builder builder = new OkHttpClient.Builder().proxy(proxy);
        // 创建 OkHttpClient 实例
        OkHttpClient client = builder.build();

        //请求体格式
        MediaType mediaType = MediaType.parse("application/json");

        //创建请求体
        GptReq gptReq = new GptReq(reqString, botConfig.getInitPrompt(), botConfig.getModel());
        String gptReqJSON = JSON.toJSONString(gptReq);
        RequestBody requestBody = RequestBody.create(gptReqJSON,mediaType);

        // 创建 POST 请求
        Request request = new Request.Builder()
                .url(API_URL)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer " + SK)
                .post(requestBody)
                .build();
        System.out.println(request);
        try {
            // 发送请求并获取响应
            Response response = client.newCall(request).execute();
            if(!response.isSuccessful()) return null;

            String responseBody = Objects.requireNonNull(response.body()).string();
            return JSON.parseObject(responseBody, GptRes.class);
        } catch (IOException e) {
            // 处理异常
            e.printStackTrace();
            return null;
        }

    }


}
