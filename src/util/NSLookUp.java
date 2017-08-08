package util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class NSLookUp {
	public static void main(String[] args) {
		try {
			Scanner scanner = new Scanner(System.in);
			
			while(true){
				System.out.print("> ");
				String host = scanner.nextLine();
				
				if ("exit".equals(host)) {
					break;
				}
				
				InetAddress[] inetAddresses = InetAddress.getAllByName(host);
				
				for(InetAddress inetAddress : inetAddresses) {
					System.out.println(host + " : " + inetAddress.getHostAddress());
				}
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
}
