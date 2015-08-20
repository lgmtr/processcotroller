package processcontrol.core.interpreter.test;

import org.testng.annotations.Test;

public class ThreadTest {

	private class JoinThread extends Thread {
		String text;

		public JoinThread(String text) {
			this.text = text;
		}

		public void start(JoinThread[] threads) {
			try {
				for (int i = 0; i < threads.length; i++)
					threads[i].join();
			} catch (InterruptedException e) {
			}
			this.start();
		}

		public void run() {
			for (int i = 0; i < 5; i++) {
				try {
					sleep((int) (Math.random() * 1000));
				} catch (InterruptedException e) {
				}
				System.out.println(text);
			}
		}
	}

	@Test
	public void threadTest() {
		JoinThread t1, t2, t3, t4, t5, t6;

		t1 = new JoinThread("Thread 1");
		t2 = new JoinThread("Thread 2");
		t3 = new JoinThread("Thread 3");
		t4 = new JoinThread("Thread 4");
		t5 = new JoinThread("Thread 5");
		t6 = new JoinThread("Thread 6");

		t1.start(new JoinThread[] {});
		t4.start(new JoinThread[] {});
		t2.start(new JoinThread[] { t1 });
		t3.start(new JoinThread[] { t1 });
		t5.start(new JoinThread[] { t2, t3, t4 });
		t6.start(new JoinThread[] { t2, t3, t4, t5 });
	}

}
