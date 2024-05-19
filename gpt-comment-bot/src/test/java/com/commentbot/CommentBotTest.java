package com.commentbot;

import com.commentbot.dao.BotConfDao;
import com.commentbot.dao.LabelDao;
import com.commentbot.dao.RecordDao;
import com.commentbot.pojo.bo.BotConfig;
import com.commentbot.pojo.bo.RecordInfo;
import com.commentbot.pojo.gpt.GptReq;
import com.commentbot.service.GptService;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

@SpringBootTest
public class CommentBotTest {
    @Resource
    private GptService gptService;
    @Resource
    private BotConfDao botConfDao;
    @Resource
    private RecordDao recordDao;

    @Resource
    private LabelDao labelDao;
    @Value("${bot.sk}")
    private String sk;

    @Test
    public void labelDaoTest(){
        System.out.println(labelDao.getMaxLabels(1));
    }
    @Test
    public void abc(){
        GptReq req = new GptReq("ni","123", "gpt-3.5-turbo");
        System.out.println(req);
    }

    @Test
    public void test01(){
        BotConfig botConfig = botConfDao.selectById(2);
        System.out.println(botConfig);
    }

    @Test
    public void test02(){
        ArrayList<Integer> ids = new ArrayList<>();
        ids.add(1);
        ids.add(2);
        ids.add(3);
        System.out.println(labelDao.getLabelContentList(ids));
    }

    @Test
    public void test03(){
        RecordInfo recordInfo = new RecordInfo();
        recordInfo.setChatId(-1);
        System.out.println(recordDao.insertNewRecord(recordInfo));
        System.out.println(recordInfo);
    }
}
