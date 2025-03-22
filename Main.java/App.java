public class App {
	public static void main(String[] args) {
		
		Store objAlmacen = new Store(10);

		for (int i = 1; i < 5; i++) {
			new Producer(objAlmacen, "Hilo " + i).start();
		}

		for (int i = 1; i < 5; i++) {
			new Consumer(objAlmacen, "Hilo " + i).start();
		}
	}
}
