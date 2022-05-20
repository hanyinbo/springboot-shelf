package com.aison.utils;

import org.springframework.stereotype.Component;

import java.security.MessageDigest;

/**
 * @author : zhenyun.su
 * @since : 2018/11/13
 */
@Component
public class FixAssetsSHA1 {

    public static String getFixAssetsSHA1(String value){
        try {
            byte[] data1 = value.getBytes("UTF-8");
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
            byte[] digest = messageDigest.digest(data1);
            return bytesToHexString(digest);
        }catch (Exception e){
            e.printStackTrace();
            return "";
        }
    }

    public static String bytesToHexString(byte[] bArr) {
        StringBuffer sb = new StringBuffer(bArr.length);
        String sTmp;

        for (int i = 0; i < bArr.length; i++) {
            sTmp = Integer.toHexString(0xFF & bArr[i]);
            if (sTmp.length() < 2)
                sb.append(0);
            sb.append(sTmp.toUpperCase());
        }
        return sb.toString();
    }


    public static void main(String[] args) {
        String sha = FixAssetsSHA1.getFixAssetsSHA1("AppId=EZP&Timestamp=20150701093010&Token=9cd8c0ed38a9e113");
        System.out.println(sha);
//        657D0E34AD7B342F888CA3856F207F35CF2622A8
    }
}
