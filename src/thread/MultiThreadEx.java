package thread;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class MultiThreadEx {
	public static void main(String[] args) {
		List<PrintWriter> list = new ArrayList<PrintWriter>();
		
		
		/*for (int i = 0; i <= 9; i++) {
			System.out.print(i);
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}*/
		
		/*for(char c = 'a'; c <= 'z'; c++) {
			System.out.print(c);
		}*/
		
		Thread thread1 = new AlphabetThread();
		Thread thread2 = new Thread(new DigitThread());
		new Thread(new Runnable() {
			@Override
			public void run() {
				for (char c = 'A'; c < 'Z'; c++) {
					System.out.print(c);
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
		
		thread1.start();
		thread2.start();
	}
}
