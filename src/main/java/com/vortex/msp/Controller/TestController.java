package com.vortex.msp.Controller;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiRobotSendRequest;
import com.dingtalk.api.response.OapiRobotSendResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.taobao.api.ApiException;
import com.vortex.msp.Service.AsyncService;
import com.vortex.msp.Utils.DataUtil;
import com.vortex.msp.Utils.JsonConvert;
import com.vortex.msp.Utils.Result;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.jasypt.contrib.org.apache.commons.codec_1_3.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@RequestMapping("/test")
@RestController
@Tag(name = "测试模块")
public class TestController {
    private final String queueName = "android.queue";
    private final AsyncService asyncService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private RabbitTemplate rabbitService;
    @Value("${DingDing.Secret}")
    private String dingSecret;
    @Value("${DingDing.Token}")
    private String dingToken;

    @Autowired
    public TestController(RabbitTemplate rabbitService, AsyncService asyncService) {
        this.rabbitService = rabbitService;
        this.asyncService = asyncService;
    }

    @GetMapping
    public Result hello1() {
//        CompletableFuture<String> future = testService.test();
//        future.thenAccept(System.out::println)
//                .exceptionally(e -> {
//                    e.printStackTrace();
//                    return null;
//                });
//        System.out.println(cardNo);
//        System.out.println(cardType);
        String serverFilePath = "upload" + "/abctest/" + DataUtil.getUUID() + ".rar";
        CompletableFuture<Boolean> future = asyncService.uploadFtp(
                serverFilePath, "D:\\seal\\file\\服务端安装部署.rar");
        future.thenAccept(result -> {
            if (result) {
                logger.info("上传成功");
            } else {
                logger.info("上传失败");
            }
        });
        return Result.SUCCEED("调用接口完毕");
    }

    @GetMapping("/app")
    public void test(@RequestParam("msg") String message) {
        rabbitService.convertAndSend(queueName, message);
    }

    @PostMapping
    public Result hello2() {
        return Result.SUCCEED("Hello POST");
    }

    @PutMapping
    public Result hello3() {
        return Result.SUCCEED("Hello PUT");
    }

    @DeleteMapping
    public Result hello4() {
        return Result.SUCCEED("Hello DELETE");
    }

    @GetMapping("WX")
    public Result WxPayQrCode() {
        String url = "http://192.168.10.7:9090/#/user8";
        return Result.SUCCEED(DataUtil.getWxPayQrCode(url));
    }

    @GetMapping("Ali")
    public Result AliPayQrCode() {
        String url = "http://192.168.10.7:9191/test";
        return Result.SUCCEED(DataUtil.getAliPayQrCode(url));
    }

    @GetMapping("/hello")
    public Result GetHello() {
        String str = new ApplicationHome(this.getClass()).getDir().getAbsolutePath();
        return Result.SUCCEED(str);
    }

    /**
     * 钉钉机器人发送消息
     */
    @PostMapping("/ding")
    public Result DingDing(@RequestBody Map<String, String> mapBody) {
        String msg = mapBody.getOrDefault("msg", "");
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
            OapiRobotSendRequest.Text text = new OapiRobotSendRequest.Text();
            text.setContent(msg);
            //定义 @ 对象
            OapiRobotSendRequest.At at = new OapiRobotSendRequest.At();
            at.setAtUserIds(Collections.singletonList(USER_ID));
            //设置消息类型
            req.setMsgtype("text");
            req.setText(text);
            req.setAt(at);
            OapiRobotSendResponse rsp = client.execute(req, CUSTOM_ROBOT_TOKEN);
            logger.info(rsp.getBody());
            Map<String, String> map = JsonConvert.deserializeObject(rsp.getBody(), new TypeReference<Map<String,
                    String>>() {
            });
            if (map.get("errcode").equals("0")) {
                return Result.SUCCEED("钉钉发送成功", null);
            } else {
                return Result.FAILED(map.get("errmsg"));
            }
        } catch (ApiException | UnsupportedEncodingException | NoSuchAlgorithmException | InvalidKeyException e) {
            return Result.Exception(e);
        }
    }

    @GetMapping("/cache/{index}")
    @Cacheable(value = "testCache", key = "#index")
    public Result CacheTest(@PathVariable("index") Integer index) {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            return Result.Exception(e);
        }
        return Result.SUCCEED(100);
    }

    @GetMapping("/stream")
    public ResponseEntity<StreamingResponseBody> streamData() {
        String content = "本文介绍了如何在SpringBoot项目中使用OpenAIAPI的流式响应功能，并结合WebSocket实现实时交互。作者详细描述了配置、请求体、响应类以及WebSocket" +
                "后端的实现，以支持长文本的逐块返回和双向通信。";
        StreamingResponseBody responseBody = outputStream -> {
            try (OutputStreamWriter os = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8)) {
                for (int i = 0; i < content.length(); i++) {
                    logger.info("正在发送第" + i + "个字符==>" + content.charAt(i));
                    os.write(content.charAt(i));
                    os.flush();
                    Thread.sleep(500); // 模拟延迟
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        // 指示这是一个流式响应
        headers.setContentLength(-1L);

        return new ResponseEntity<>(responseBody, headers, HttpStatus.OK);
    }


}
