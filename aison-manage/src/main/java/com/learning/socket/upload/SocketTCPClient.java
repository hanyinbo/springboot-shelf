package com.learning.socket.upload;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class SocketTCPClient {
	public static void main(String[] args) throws IOException {
		//1:连接Socket
		Socket socket = new Socket(InetAddress.getLocalHost(), 8888);
		//创建读取的磁盘的输入流


		//2:发送消息
		OutputStream outputStream = socket.getOutputStream();
		outputStream.write("hellow server".getBytes());
		//3:设置结束标记
		socket.shutdownOutput();
		//4：获取socket相关联的输入流
		InputStream inputStream = socket.getInputStream();
		byte[] buf = new byte[1024];
		int readLine = 0;
		while ((readLine = inputStream.read(buf)) != -1) {
			System.out.println(new String(buf, 0, readLine));
		}
		//关闭流和socket
		inputStream.close();
		outputStream.close();
		socket.close();
		System.out.println("客户端发送完成，并关闭客户端连接");
	}
}
