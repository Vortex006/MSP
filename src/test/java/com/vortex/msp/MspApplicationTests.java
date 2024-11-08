package com.vortex.msp;

import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.text.csv.CsvReader;
import cn.hutool.core.text.csv.CsvUtil;
import com.vortex.msp.Entity.Deptment;
import com.vortex.msp.Service.DeptmentService;
import com.vortex.msp.Service.LogService;
import com.vortex.msp.Utils.SealUtil;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class MspApplicationTests {
    private static final Logger logger = LoggerFactory.getLogger(MspApplicationTests.class);
    @Autowired
    LogService logService;
    @Autowired
    DeptmentService deptmentService;
    @Autowired
    private RabbitTemplate rabbitService;

    public static String CreateHtml(String fileName, Map<String, Object> dataModel) {
        String htmlContent = "";
        // 初始化Freemarker配置
        Configuration cfg = new Configuration();
        try {
            // 设置模板加载目录
            cfg.setDirectoryForTemplateLoading(new File("src/test/resources/template"));
            // 获取模板
            Template template = cfg.getTemplate(fileName);
            // 使用StringWriter来捕获输出
            StringWriter stringWriter = new StringWriter();
            template.process(dataModel, stringWriter);
            // 获取渲染后的字符串
            htmlContent = stringWriter.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return htmlContent;
    }

    @Test
    void demo() {
        String queueName = "work.queue";
        for (int i = 0; i < 50; i++) {
            rabbitService.convertAndSend(queueName, "hello worker ,message_" + i);
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    void date() {
        try {
            SealUtil.CreateSeal("D:\\seal\\seal.png", "检验报告单");
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Test
    void csv() {
        final CsvReader reader = CsvUtil.getReader();
//假设csv文件在classpath目录下
        final List<Deptment> result = reader.read(
                ResourceUtil.getUtf8Reader("dept.csv"), Deptment.class);
        for (Deptment e : result) {
            deptmentService.saveDeptment(e);
        }
//        result.forEach(e -> logger.info(e.toString()));
    }

    @Test
    void log() {
        logger.info("test log");
    }

    @Test
    void ftl() {
        Map<String, Object> dataModel = new HashMap<>();
        dataModel.put("title", "Welcome Page");
        dataModel.put("name", "World");
        String htmlContent = CreateHtml("demo.ftl", dataModel);
        HtmlGenerator.html2Pdf(htmlContent, "D:\\demo.pdf");
    }

}

