package com.zyj.msp.Utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.UUID;

/**
 * 对数据进行脱敏
 */
@Component
public class DataUtil {
    /**
     * 随机数常量实例
     */
    private static final Random random = new Random();

    /**
     * 对象映射器
     */
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    /**
     * 空字符串
     */
    private static final String Empty = "";
    /**
     * 脱敏字符
     */
    private static char mark;
    /**
     * 验证码字符集
     */
    private static String codeCharArray;
    /**
     * 验证码长度
     */
    private static int codeLength;

    /**
     * 随机生成一个 8-16位 大小写字母数字组合的密码
     *
     * @param min 最小长度
     * @param max 最大长度
     * @return 密码
     */
    public static String getRandomPassword(int min, int max) {
        String chars = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        if (min > max) return Empty; // 如果最小值大于最大值，则返回空字符串
        int PasswordLength = random.nextInt(min) + (max - min);
        StringBuilder PasswordBuilder = new StringBuilder();
        int length = chars.length();
        for (int j = 0; j < PasswordLength; j++) {
            char c = chars.charAt(random.nextInt(length));
            PasswordBuilder.append(c);
        }
        String password = new String(PasswordBuilder);
        return password;
    }

    @Value("${Mark}")
    public void setMark(char mark) {
        DataUtil.mark = mark;
    }

    @Value("${Code.Char.Array}")
    public void setCodeCharArray(String codeCharArray) {
        DataUtil.codeCharArray = codeCharArray;
    }

    /**
     * 重复字符串多次。
     *
     * @param str   需要被重复的字符串。如果为null，函数将返回null。
     * @param times 字符串需要被重复的次数。必须为非负数。
     * @return 重复后的字符串。如果输入的字符串为null，那么直接返回null。
     */
    public static String repeatString(String str, int times) {
        // 检查输入字符串是否为null
        if (str == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        // 将字符串重复指定次数
        for (int i = 0; i < times; i++) {
            sb.append(str);
        }
        // 返回拼接后的字符串
        return sb.toString();
    }

    public static String MaskingEmail(String email) {
        // 先提取用户名
        String username = email.split("@")[0];
        // 使用repeatString方法来重复字符
        String maskedUsername = username.charAt(0) + DataUtil.repeatString("*", username.length() - 1);
        return maskedUsername + "@" + email.split("@")[1];
    }

    //将数据转换为JAVA对象
    public static <T> T ParseJson(Object data, Class<T> valueType) {
        T t = OBJECT_MAPPER.convertValue(data, valueType);
        return t;
    }

    public static String getUUID() {
        String uuid = UUID.randomUUID().toString().replaceAll("-", Empty);
        return uuid;
    }

    @Value("${Code.Length}")
    public void setCodeLength(int codeLength) {
        DataUtil.codeLength = codeLength;
    }

    /**
     * 隐藏部分字符串
     *
     * @param str           字符串
     * @param frontLenght   前面几位
     * @param lastLenght    后面几位
     * @param replaceChar   替换字符，默认为'*'
     * @param replaceLength 替换字符长度
     * @return 处理后的字符串
     */
    public static String subFormat(String str, int frontLenght, int lastLenght, char replaceChar, int replaceLength) {
        if (!StringUtils.hasText(str)) {
            return str;
        }
        if (str.length() <= (frontLenght + lastLenght)) {
            return str;
        }
        String frontStr = str.substring(0, frontLenght);
        String lastStr = str.substring(str.length() - lastLenght);
        StringBuilder replaceStringBuilder = new StringBuilder();
        for (int i = 0; i < replaceLength; i++) {
            replaceStringBuilder.append(replaceChar);
        }
        String replaceString = replaceStringBuilder.toString();
        String result = frontStr + replaceString + lastStr;
        return result;
    }

    /**
     * 计算给定页面索引和每页条数的偏移量
     *
     * @param pageSize 每页显示的条数
     * @param index    当前页数
     * @return 返回从零开始的索引偏移量，用于数据库查询等操作以获取正确页码的数据
     */
    public static int getOffset(int pageSize, int index) {
        return (index - 1) * pageSize;
    }

    /**
     * 获取验证码
     *
     * @return 验证码
     */
    public static String getVerificationCode() {
        StringBuilder code = new StringBuilder();
        char[] charArray = codeCharArray.toCharArray();
        for (int i = 0; i < codeLength; i++) {
            char b = charArray[(random.nextInt(charArray.length))];
            code.append(b);
        }
        return code.toString();
    }

    /**
     * 获取包含验证码的图片
     *
     * @param code 需要显示的验证码
     * @return base64编码的验证码图片
     */
    public static String getVerificationCodeImg(String code) {
        // 创建画布
        BufferedImage im = new BufferedImage(80, 30, BufferedImage.TYPE_INT_RGB);
        // 获取画笔
        Graphics g = im.getGraphics();
        // 设置画笔颜色
        g.setColor(new Color(255, 255, 255));
        // 填充画布颜色为白色
        g.fillRect(0, 0, 80, 30);
        // 重新设置画笔颜色
        g.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
        // 设置验证码字体大小
        g.setFont(new Font(null, Font.BOLD, 26));
        // 画出验证码
        g.drawString(code, 5, 22);
        for (int i = 0; i < 10; i++) {
            // 设置干扰线画笔颜色
            g.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
            // 绘制干扰线
            g.drawLine(random.nextInt(80), random.nextInt(30), random.nextInt(80), random.nextInt(30));
        }
        ByteArrayOutputStream bos = new ByteArrayOutputStream();//io流
        try {
            ImageIO.write(im, "png", bos);//写入流中
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] bytes = bos.toByteArray();//转换成字节
        BASE64Encoder encoder = new BASE64Encoder();
        String png_base64 = encoder.encodeBuffer(bytes).trim();
        png_base64 = png_base64.replaceAll("\n", "").replaceAll("\r", "");//删除 \r\n
        png_base64 = "data:image/png;base64," + png_base64;
        return png_base64;
    }

    /**
     * 根据身份证号码获取出生日期
     *
     * @param certNo 身份证号
     */
    public static LocalDate getBirthDateByCertNo(String certNo) {
        LocalDate birthDate = LocalDate.parse(certNo.substring(6, 14), DateTimeFormatter.ofPattern("yyyyMMdd"));
        return birthDate;
    }

    /**
     * 根据身份证号码获取年龄
     *
     * @param certNo 身份证号
     */
    public static int getAgeByCertNo(String certNo) {
        LocalDate birthDate = LocalDate.parse(certNo.substring(6, 14), DateTimeFormatter.ofPattern("yyyyMMdd"));
        LocalDate now = LocalDate.now();
        return Period.between(birthDate, now).getYears();
    }

    /**
     * 根据身份证号码判断性别
     *
     * @param certNo 身份证号
     *               偶数为女性，奇数为男性（倒数第二位）
     * @return true 女  false 男
     */
    private static boolean getSexByCertNo(String certNo) {
        char genderCode = certNo.charAt(16);
        return (genderCode % 2 != 0);
    }

}
