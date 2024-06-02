package com.zyj.msp.Utils;

import org.jasypt.util.text.BasicTextEncryptor;

public class EncryptUtil {

    //密钥
    private static final String passwordKey = "MspVortex";


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

    //解密密码
    public static String decrypted(String passwordStr) {
        //创建实例
        BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
        //设置加密密钥
        textEncryptor.setPassword(passwordKey);
        //解密信息
        String text = textEncryptor.decrypt(passwordStr);
        return text;
    }
}
