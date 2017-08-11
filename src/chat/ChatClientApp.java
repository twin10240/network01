package chat;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class ChatClientApp {
	private static final String SERVER_IP = "192.168.1.20";
	private static final int SERVER_PORT = 9090;
	
	public static void main(String[] args) {
		Socket socket = null;
		String name = null;
		Scanner scanner = new Scanner(System.in);
		
		while( true ) {
			System.out.println("대화명을 입력하세요.");
			System.out.print(">>> ");
			name = scanner.nextLine();
			
			if (name.isEmpty() == false) {
				try {
					socket = new Socket();
					socket.connect(new InetSocketAddress(SERVER_IP, SERVER_PORT));
					
					BufferedReader br = new BufferedReader( new InputStreamReader( socket.getInputStream(), "UTF-8" ) );
					PrintWriter pw = new PrintWriter( new OutputStreamWriter( socket.getOutputStream(), "UTF-8" ), true );
					
					//메세지 보내기 JOIN:name\r\n
					pw.println("JOIN:" + name + "\r\n");
					
					//에코 메세지 받기
					String echoMessage = br.readLine();
					if( echoMessage == null ) {
						System.out.println( "[client] Diconnection by Server" );
						break;
					}
					
					String[] tokens = echoMessage.split(":");
					if ("OK".equals(tokens[1])) {
						new ChatWindow(name, socket, br, pw).show();
					}else {
						consoleLog("연결이 실패했습니다.");
						continue;
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					if( scanner != null ) {
						scanner.close();
					}
				}
				break;
			}else {
				consoleLog("대화명은 한글자 이상 입력해야 합니다.\n");
				continue;
			}
		}
	}
	
	private static void consoleLog(String msg) {
		System.out.println("[Client] "+ msg);
	}
}
