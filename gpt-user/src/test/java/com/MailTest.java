package com;

import cn.hutool.extra.mail.MailUtil;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class MailTest {

    @Test
    void mailTest() throws TemplateException, IOException {
        StringWriter writer = renderMail("register-email.ftl","123");
        MailUtil.send("1750859115@qq.com", "[SomersaultCloud]", String.valueOf(writer),true);
    }
    //选择渲染模板
    public StringWriter renderMail(String template,String checkCode) throws IOException, TemplateException {
        //模板渲染
        Configuration config = new Configuration(Configuration.VERSION_2_3_0);
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        // 设置模板文件所在的类路径位置
        config.setClassLoaderForTemplateLoading(classLoader, "/template");
        // 加载模板文件
        Template model = config.getTemplate(template);
        Map<String, String> map = new HashMap<>();
        map.put("checkCode", checkCode);
        // 渲染模板
        StringWriter writer = new StringWriter();
        model.process(map, writer);
        return writer;
    }

}
