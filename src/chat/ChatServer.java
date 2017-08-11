package chat;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class ChatServer {
	private static final int SERVER_PORT = 9090;
	private static List<User> listWrite = new ArrayList<User>();
	
	public static void main(String[] args) {
		ServerSocket serverSocket = null;
		
		try {
			serverSocket = new ServerSocket();
			
			String hostAddress = InetAddress.getLocalHost().getHostAddress();
			serverSocket.bind(new InetSocketAddress(hostAddress, SERVER_PORT));
			System.out.println("bind " + InetAddress.getLocalHost().getHostAddress() + ":" + SERVER_PORT);
			
			while (true) {
				Socket socket = serverSocket.accept();
				consoleLog("연결됨");

				Thread chatThread = new ChatServerThread(socket, listWrite);
				chatThread.start();
			}
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void consoleLog(String log) {
		System.out.println("[server :" + Thread.currentThread().getId() + " ] " + log);
	}
}
