package com.vortex.msp.ServiceImpl;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiRobotSendRequest;
import com.dingtalk.api.response.OapiRobotSendResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.taobao.api.ApiException;
import com.vortex.msp.Entity.Message;
import com.vortex.msp.Exception.ParameterNullException;
import com.vortex.msp.Mapper.MessageMapper;
import com.vortex.msp.Service.MessageService;
import com.vortex.msp.Utils.DataUtil;
import com.vortex.msp.Utils.JsonConvert;
import org.jasypt.contrib.org.apache.commons.codec_1_3.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public class MessageServiceImpl implements MessageService {

    private final MessageMapper messageMapper;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Value("${DingDing.Secret}")
    private String dingSecret;
    @Value("${DingDing.Token}")
    private String dingToken;


    @Autowired
    public MessageServiceImpl(MessageMapper messageMapper) {
        this.messageMapper = messageMapper;
    }

    @Override
    public boolean saveMessage(Message message) {
        Integer i = messageMapper.saveMessage(message);
        return i > 0;
    }

    @Override
    public boolean updateMessage(Message message) {
        Integer i = messageMapper.updateMessage(message);
        return i > 0;
    }

    @Override
    public boolean deleteMessage(Integer messageId) {
        Integer i = messageMapper.deleteMessage(messageId);
        return i > 0;
    }

    @Override
    public Message getMessage(Integer messageId) {
        Message message = messageMapper.getMessage(messageId);
        return message;
    }

    @Override
    public List<Message> listMessages() {
        List<Message> messages = messageMapper.listMessages();
        return messages;
    }

    @Override
    public List<Message> listMessagesByUser(Integer userId) {
        List<Message> messages = messageMapper.listMessagesByUser(userId);
        return messages;
    }

    @Override
    public List<Message> listMessagesByDate(LocalDate date) {
        List<Message> messages = messageMapper.listMessagesByDate(date);
        return messages;
    }

    @Override
    public List<Message> limitMessages(Integer pageSize, Integer index) {
        if (pageSize <= 0 || index <= 0) {
            throw new ParameterNullException("参数异常");
        }
        int offset = DataUtil.getOffset(pageSize, index);
        List<Message> messages = messageMapper.limitMessages(pageSize, offset);
        return messages;
    }

    @Override
    public Integer countMessageByUser(Integer userId) {
        Integer count = messageMapper.countMessageByUser(userId);
        return count;
    }

    @Override
    public Integer countMessage() {
        Integer count = messageMapper.countMessage();
        return count;
    }

    public Boolean sendDingMsg(String title, String msg) {
        final String CUSTOM_ROBOT_TOKEN = dingToken;
        final String USER_ID = "";
        final String SECRET = dingSecret;
        try {
            Long timestamp = System.currentTimeMillis();
            String secret = SECRET;
            String stringToSign = timestamp + "\n" + secret;
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
            byte[] signData = mac.doFinal(stringToSign.getBytes(StandardCharsets.UTF_8));
            String sign = URLEncoder.encode(new String(Base64.encodeBase64(signData)), "UTF-8");
            //sign字段和timestamp字段必须拼接到请求URL上，否则会出现 310000 的错误信息
            String url = String.format("https://oapi.dingtalk.com/robot/send?sign=%s&timestamp=%s", sign, timestamp);
            DingTalkClient client = new DefaultDingTalkClient(url);
            OapiRobotSendRequest req = new OapiRobotSendRequest();
            //定义文本内容
            OapiRobotSendRequest.Markdown markdown = new OapiRobotSendRequest.Markdown();
//            OapiRobotSendRequest.Text text = new OapiRobotSendRequest.Text();
//            text.setContent(msg);
            //定义 @ 对象
//            OapiRobotSendRequest.At at = new OapiRobotSendRequest.At();
//            at.setAtUserIds(Collections.singletonList(USER_ID));
            //设置消息类型
            markdown.setTitle(title);
            markdown.setText(msg);
            req.setMsgtype("markdown");
            req.setMarkdown(markdown);
//            req.setAt(at);
            OapiRobotSendResponse rsp = client.execute(req, CUSTOM_ROBOT_TOKEN);
            logger.info(rsp.getBody());
            Map<String, String> map = JsonConvert.deserializeObject(rsp.getBody(), new TypeReference<Map<String,
                    String>>() {
            });
            return map.get("errcode").equals("0");
        } catch (ApiException | UnsupportedEncodingException | NoSuchAlgorithmException | InvalidKeyException e) {
            logger.error(e.getMessage());
            return false;
        }

    }
}


