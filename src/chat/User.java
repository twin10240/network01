package chat;

import java.io.PrintWriter;

public class User {
	private String nickName;
	private PrintWriter printWrite;
	
	public User(String nickName, PrintWriter pw) {
		this.nickName = nickName;
		this.printWrite = pw;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public PrintWriter getPrintWrite() {
		return printWrite;
	}

	public void setPrintWrite(PrintWriter printWrite) {
		this.printWrite = printWrite;
	}
	
}
