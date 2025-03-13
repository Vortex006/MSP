package com.vortex.msp.Utils;

import org.jasypt.util.text.BasicTextEncryptor;
import org.springframework.beans.factory.annotation.Value;

public class EncryptUtil {

    /**
     * AES加密密钥
     */
    private static String encryptKey;
    private static String passwordKey = "8d243df837vb##@$";

    /**
     * 解密密码
     *
     * @param passwordStr 被加密的密码
     * @return 解密后的密码
     */
    public static String decrypted(String passwordStr) {
        //创建实例
        BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
        //设置加密密钥
        textEncryptor.setPassword(passwordKey);
        //解密信息
        String text = textEncryptor.decrypt(passwordStr);
        return text;
    }

//    public static String EncryptAes(String content) {
//
//
//
//    }


    /**
     * 加密密码
     *
     * @param password 密码本体
     * @param salt     加盐
     * @return 被加密后的密码
     */
    public static String encrypted(String password, String salt) {
        //创建实例
        BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
        //设置加密密钥
        textEncryptor.setPassword(passwordKey);
        //加密信息
        String text = textEncryptor.encrypt(password.concat(salt));
        return text;
    }

    public static String decrypted(String passwordStr, String salt) {
        //创建实例
        BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
        //设置加密密钥
        textEncryptor.setPassword(passwordKey);
        //解密信息
        String text = textEncryptor.decrypt(passwordStr);
        text = text.replaceAll(salt, "");
        return text;
    }

    @Value("${Encrypt.Key}")
    public void setCodeCharArray(String key) {
        this.encryptKey = key;
    }
}
