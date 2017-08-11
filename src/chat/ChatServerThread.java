package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class ChatServerThread extends Thread {
	private Socket socket;
	private List<User> listWrite;
	private User user;

	public ChatServerThread(Socket socket, List<User> listWrite) {
		this.socket = socket;
		this.listWrite = listWrite;
	}

	@Override
	public void run() {
		// 4. 연결 성공
		InetSocketAddress remoteSocketAddress = (InetSocketAddress) socket.getRemoteSocketAddress();
		int remoteHostPort = remoteSocketAddress.getPort();

		String remoteHostAddress = remoteSocketAddress.getAddress().getHostAddress();
		consoleLog("connected from " + remoteHostAddress + ":" + remoteHostPort);

		try {
			// 5. I/O Stream 받아오기
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true);

			while (true) {
				// 6. 데이터 읽기 (read)
				String message = br.readLine();
				if (message == null) { // 정상종료
					consoleLog("disconnection by client");
					break;
				}
				
				String[] tokens = message.split(":");
				switch (tokens[0]) {
				case "JOIN":
					doJoin(tokens[1], pw); // 닉네임과 메세지처리
					break;
				case "MESSAGE":
					doMessage(tokens[1]);
					break;
				case "QUIT":
					doQuit(tokens[1], pw);
					break;

				default:
					break;
				}
			}

		} catch (SocketException e) {
			// 상대편이 소켓을 정상적으로 닫지 않고 종료한 경우
			consoleLog("sudden closed by client");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (socket != null && socket.isClosed() == false) {
					socket.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void doQuit(String nickName, PrintWriter pw) {
	   removeWriter(nickName);
		
	   String data = user.getNickName() + "님이 퇴장 하였습니다."; 
	   broadcast(data);
	}

	private void removeWriter(String nickName) {
		synchronized(listWrite) {
			for (User user : listWrite) {
				if (user.getNickName().equals(nickName)) {
					listWrite.remove(user);
				}
			}
		}
	}

	private void doMessage( String message ) {
		String data = message;
		broadcast(user.getNickName() + ":" + data);
	}

	private void doJoin(String nickName, PrintWriter pw) {
		user = new User(nickName, pw);
		
		String data = user.getNickName() + "님이 참여하였습니다.";
		broadcast(data);

		addWriter(user, pw);

		// ack
		pw.println("JOIN:OK\r\n");
		pw.flush();
	}

	private void addWriter(User user, PrintWriter pw) {
		synchronized (listWrite) {
			listWrite.add(user);
		}
	}
	
	private void broadcast(String data) {
		synchronized (listWrite) {
			for (User user : listWrite) {
				PrintWriter printWriter = (PrintWriter) user.getPrintWrite();
				printWriter.println(data);
				printWriter.flush();
			}
		}
	}

	private void consoleLog(String log) {
		System.out.println("[server :" + getId() + " ] " + log);
	}

}
