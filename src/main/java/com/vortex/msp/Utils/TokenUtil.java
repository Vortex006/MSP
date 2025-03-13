package com.vortex.msp.Utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class TokenUtil {

    private static String ISS; //签发人
    private static Long EXP; //过期时间六小时 单位:毫秒(ms)
    private static String KEY; //密钥

    /**
     * 获取一个Token
     * 过期时间默认为6小时
     *
     * @param userId   用户Id 负载信息1
     * @param userName 用户名 负载信息2
     * @return token字符串
     */
    public static String getToken(HashMap<String, String> data) {
        Date date = new Date(System.currentTimeMillis() + (EXP * 60 * 60 * 1000));
        JWTCreator.Builder builder = JWT.create().withIssuer(ISS)//设置签发人
                .withExpiresAt(date);//设置到期时间
        data.forEach(builder::withClaim);
        return builder.sign(Algorithm.HMAC256(KEY));
    }

    @Value("${token.iis.name}")
    public void setISS(String ISS) {
        TokenUtil.ISS = ISS;
    }

    @Value("${token.exp.hours}")
    public void setEXP(Long EXP) {
        TokenUtil.EXP = EXP;
    }

    @Value("${token.key}")
    public void setKEY(String KEY) {
        TokenUtil.KEY = KEY;
    }

//    public static String getToken(String content, String )

    /**
     * 验证token 原理：验证异常会抛异常 根据异常进行解析
     *
     * @param token 需要验证的token
     *              SignatureVerificationException 无效签名
     *              TokenExpiredException          token过期
     */
    public static void verifyToken(String token) {
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256((KEY)))
                .withIssuer(ISS)
                .build().verify(token);
    }

    /**
     * 从Token中获取数据
     *
     * @param token 需要获取数据的token
     * @return token中数据的Map集合 key:负载信息名称 value:负载信息内容
     */
    public static Map<String, Claim> decodeToken(String token) {
        DecodedJWT decode = JWT.decode(token);
        Map<String, Claim> claims = decode.getClaims();
        return claims;
    }

    /*
     * 比较日期
     * compareTo()函数
     * a.compareTo(b)
     * return int
     * 如果 int大于0 说明a在b之后 说明token没过期
     * 如果 int小于0 说明a在b之前 说明token过期
     * 如果 int等于0 说明a和b相等 说明token过期
     */



}