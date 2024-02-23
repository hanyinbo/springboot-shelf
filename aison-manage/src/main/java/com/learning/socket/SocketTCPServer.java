package com.learning.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketTCPServer {
	public static void main(String[] args) throws IOException {
		//1:监听8888端口，等待连接 serverSocket 与 socket 一对多
		ServerSocket serverSocket = new ServerSocket(8888);
		System.out.println("等待连接");
		//2:当没有客户端连接8888端口时，程序会阻塞，等待连接
		//  有客户端连接，则返回Socket对象，程序继续
		Socket socket = serverSocket.accept();
		//3:通过socket.getInputStream取客户端写入的数据
		InputStream inputStream = socket.getInputStream();
		//4:获取流中的数据
		byte[] buf = new byte[1024];
		int readLine = 0;
		while ((readLine = inputStream.read(buf)) != -1) {
			System.out.println(new String(buf, 0, readLine));
		}
		//5：获取socket相关的输出流
		OutputStream outputStream = socket.getOutputStream();
		outputStream.write("hellow client".getBytes());
		//6：设置结束标记
		socket.shutdownOutput();
		outputStream.close();
		//7:关闭流和socket
		inputStream.close();
		socket.close();
		serverSocket.close();
	}
}
