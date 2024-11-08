package com.vortex.msp.ServiceImpl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import com.vortex.msp.Service.EMICardService;
import com.vortex.msp.Service.RedisService;
import com.vortex.msp.Utils.DataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static java.nio.charset.StandardCharsets.UTF_8;

@Service
public class EMICardServiceImpl implements EMICardService {

    private final RedisService redisService;

    @Autowired
    public EMICardServiceImpl(RedisService redisService) {
        this.redisService = redisService;
    }

    @Override
    public String getEMICardNo(String certNo) {
        String redisKey = "YbKey";
        String salt = IdUtil.simpleUUID();
        byte[] key = salt.getBytes(UTF_8);
        redisService.delete(redisKey);
        boolean isSuccess = redisService.setString(redisKey, salt);
        String hex = "";
        if (isSuccess) {
            //构建
            SymmetricCrypto aes = new SymmetricCrypto(SymmetricAlgorithm.AES, key);
            //加密为16进制表示
            hex = aes.encryptHex(certNo);
            hex = hex.toUpperCase();
        }
        return hex;
    }

    @Override
    public String getEMICardQr(String certNo) {
        String hex = getEMICardNo(certNo);
        String base64 = DataUtil.getQrCode(hex);
        return base64;
    }
}
