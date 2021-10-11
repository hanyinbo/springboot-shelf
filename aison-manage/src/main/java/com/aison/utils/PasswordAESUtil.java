package com.aison.utils;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

@Component
@Slf4j
public class PasswordAESUtil {

    public static String AES_KEY;

    @Value("${pwd.aesKey}")
    public void setAesKey(String aesKey) {
        AES_KEY = aesKey;
    }
    /**
     * PKCS5Padding -- Pkcs7 两种padding方法都可以
     * @param content 3c2b1416d82883dfeaa6a9aa5ecb8245  16进制
     * @return
     */
    public static String decryptAES2(String content) {
        try {
            log.info("配置密码"+AES_KEY);
            SecretKeySpec skeySpec = new SecretKeySpec(AES_KEY.getBytes("UTF-8"), "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding"); // "算法/模式/补码方式"
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            return new String(cipher.doFinal(parseHexStr2Byte(content)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /**将16进制转换为二进制
     * @param hexStr
     * @return
     */
    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1)
            return null;
        byte[] result = new byte[hexStr.length()/2];
        for (int i = 0;i< hexStr.length()/2; i++) {
            log.info("iiii"+i);
            int high = Integer.parseInt(hexStr.substring(i*2, i*2+1), 16);
            int low = Integer.parseInt(hexStr.substring(i*2+1, i*2+2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }
}
