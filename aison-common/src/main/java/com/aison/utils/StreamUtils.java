package com.aison.utils;

import java.io.*;

/**
 * @Author hyb
 * @Description //字节流的转换 2024/2/23
 * @Date 17:31 2024/2/23
 */
public class StreamUtils {
	/**
	 * 将输入流转换成byte[]，既可以将文件的内容读入到byte[]
	 *
	 * @param is
	 * @return
	 */
	public static byte[] streamToByArray(InputStream is) throws IOException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();//创建输入流对象
		byte[] b = new byte[1024];//字节数组
		int len;
		while ((len = is.read(b)) != -1) {//循环读取
			bos.write(b, 0, len);//把读取的数据，写入bos
		}
		byte[] array = bos.toByteArray();//然后将bos转换成字节数组
		bos.close();
		return array;
	}

	/**
	 * 将InputStream转换成String
	 * @param is
	 * @return
	 * @throws IOException
	 */
	public static String streamToString(InputStream is) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder builder = new StringBuilder();
		String line ;
		while ((line = reader.readLine()) != null) {
			builder.append(line);
		}
		return builder.toString();
	}

}
