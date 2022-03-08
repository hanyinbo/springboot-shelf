package com.aison.test.basic;

import java.security.MessageDigest;

/**
 * @author : zhenyun.su
 * @since : 2018/11/13
 */

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
        String sha = FixAssetsSHA1.getFixAssetsSHA1("AppId=gusen&appSecret=1UutEpXgsrQLs1l14qkMIkMR9iU&Timestamp=2022-03-08 15:50:16&TenantId=62");
        System.out.println("sign:"+sha);

    }
}
