package test;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class LocalHost {
	public static void main(String[] args) {
		InetAddress inetAddress = null;
		
		try {
			inetAddress = InetAddress.getLocalHost();
			
			String hostName = inetAddress.getHostName();
			System.out.println(hostName);
			
			String hostAddress = inetAddress.getHostAddress();
			System.out.println(hostAddress);
			
			byte[] addresses = inetAddress.getAddress();
			for (int i = 0; i < addresses.length; i++) {
				System.out.print(addresses[i] & 0x000000ff); // 이건 무슨 표현??
				
				if (i < 3) {
					System.out.print(".");
				}
			}
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
}
