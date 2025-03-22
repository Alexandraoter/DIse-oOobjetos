

import java.util.Random;

public class Consumer extends Thread {

	private Store store;
	private String Name;

	public Consumer(Store store, String name) {
		this.store = store;
		this.Name = name;
	}

	@Override
	public void run() {
		int NumProducts = 0;

		while (true) {
			
			NumProducts = (new Random()).nextInt(5) + 1;
			
			this.store.Consume(NumProducts, Name);
			
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
