

import java.util.Random;

public class Producer extends Thread {

	private Store store;
	private String name;

    

	public Producer(Store store, String name) {
		this.store = store;
		this.name = name;
	}

	@Override
	public void run() {
		int NumProducts = 0;

		while (true) {
			
			NumProducts = (new Random()).nextInt(5);
			
			this.store.Produce(NumProducts, name);
			
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}