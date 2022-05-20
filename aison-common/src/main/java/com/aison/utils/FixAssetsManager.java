package com.aison.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
@Builder
@ToString
@NoArgsConstructor
@Accessors(chain = true)
public class FixAssetsManager implements Serializable {

	public static String getFixAssetsValue(String appId, String appSecret, long timestamp, Integer tenantId) {
		return "AppId=" + appId + "&appSecret=" + appSecret + "&Timestamp=" + timestamp + "&TenantId=" + tenantId;
	}

	/**
	 * 获取时间戳
	 *
	 * @param
	 * @return
	 */
	public static String getTimestamp() {
		SimpleDateFormat f = new SimpleDateFormat("yyyyMMddHHmmss");
		return f.format(new Date());
	}

	/**
	 * 验证时间戳
	 *
	 * @param
	 * @return
	 */
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

	/**
	 * 验证签名
	 *
	 * @param
	 * @return
	 */
	public static boolean verifySign(FixAssetsRequest fixAssetsRequest) {
		String appId = fixAssetsRequest.getAppId();
		String appSecret = fixAssetsRequest.getAppSecret();
		String requestSign = fixAssetsRequest.getSign();
		Long timestamp = Long.parseLong(fixAssetsRequest.getTimestamp());
		Integer tenantId = fixAssetsRequest.getTenantId();
		String sign = FixAssetsSHA1.getFixAssetsSHA1(getFixAssetsValue(appId,appSecret, timestamp, tenantId));
		System.out.println("签名："+sign);
		return (sign.equals(requestSign) ? true : false);
	}
}
