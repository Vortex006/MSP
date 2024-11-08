package com.vortex.msp.Utils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.*;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

public class EmailUtil {
    /**
     * 读取邮件模板
     * 替换模板中的信息
     * @param title 内容
     * @return
     */
    public static String buildContent(String title) {
        //加载邮件html模板
        Resource resource = new ClassPathResource("template/email.ftl");
        InputStream inputStream = null;
        BufferedReader fileReader = null;
        StringBuffer buffer = new StringBuffer();
        String line = "";
        try {
            inputStream = resource.getInputStream();
            fileReader = new BufferedReader(new InputStreamReader(inputStream));
            while ((line = fileReader.readLine()) != null) {
                buffer.append(line);
            }
        } catch (Exception e) {
            System.out.println("发送邮件读取模板失败");
        } finally {
            if (fileReader != null) {
                try {
                    fileReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        //替换html模板中的参数
        return MessageFormat.format(buffer.toString(), title);
    }


    public static String dddd(File file) {
        // 初始化Freemarker配置
        Configuration cfg = new Configuration();
        try {
            // 设置模板加载目录
            cfg.setDirectoryForTemplateLoading(file);

            // 获取模板
            Template template = cfg.getTemplate("demo.ftl");

            // 准备数据模型
            Map<String, Object> dataModel = new HashMap<>();
            dataModel.put("title", "Welcome Page");
            dataModel.put("name", "World");

            // 使用StringWriter来捕获输出
            StringWriter stringWriter = new StringWriter();
            template.process(dataModel, stringWriter);
            // 获取渲染后的字符串
            String renderedString = stringWriter.toString();
            return renderedString;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


}


