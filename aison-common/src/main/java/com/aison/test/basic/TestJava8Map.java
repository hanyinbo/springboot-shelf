package com.aison.test.basic;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.stream.Stream;

/**
 * 测试JDK8  map sort distinct
 */
public class TestJava8Map {
    public static void main(String[] args) throws NoSuchAlgorithmException, UnsupportedEncodingException {
//        Stream.of("2","1","9","0","8","2","0").map(Integer::parseInt).distinct().sorted((o1, o2) -> o1-o2).forEach(System.out::println);
//        //指定对象distinct
//       Stream.of(new Person("刘德华",19,"歌手"),
//               new Person("爱德华",20,"理发师"),
//               new Person("刘德华",19,"歌手")).distinct().forEach(System.out::println);
//        String s = "";
//        if(StringUtils.isNotBlank(s)){
//            System.out.println("不为空");
//        }else {
//            System.out.println("为空");
//        }

        String s1 = "1234343";
        byte[] data1 = s1.getBytes("UTF-8");
        String s2 = DigestUtils.sha1Hex(s1).toUpperCase();
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
        byte[] digest1 = messageDigest.digest(data1);

        System.out.println(s2);

        String s = bytesToHexString(digest1);
        System.out.println(s);

        long time = new Date().getTime();
        System.out.println(time);

        long times = System.currentTimeMillis() / 1000;
        System.out.println(times);

        String timestamp = verifyTimestamp(time);
        System.out.println(timestamp);


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
    public static String verifyTimestamp(Long timestamp) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String result = "";
        try {
            String transforTime = formatter.format(Long.parseLong(String.valueOf(timestamp*1000L)));
            Date requestTime = formatter.parse(transforTime);
            long lo = (new Date().getTime() - requestTime.getTime()) / (60 * 1000);
            if (lo > 10l) {
                result = "时间戳过期";
            }
        } catch (ParseException e) {
            e.printStackTrace();
            result = "时间戳格式不正确";
        }
        return result;
    }

    public static String getFixAssetsValue(String appId, String appSecret, String timestamp, Integer tenantId) {
        return "AppId=" + appId + "&appSecret=" + appSecret + "&Timestamp=" + timestamp + "&TenantId=" + tenantId;
    }


}

