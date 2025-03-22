public class Store {

	private int articles;
	private final int numMaxStock = 20;

	public Store(int articles) {
		this.articles = articles;
	}

	synchronized public void Produce(int NewProducts, String Name) {
		try {
			while ((NewProducts + this.articles) > numMaxStock) {
				wait();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		this.articles = this.articles + NewProducts;
		System.out.println("El Productor " + Name + " ha conseguido añadir " + NewProducts + " productos. Stock = "
				+ this.articles);
		notifyAll();
	}

	synchronized public void Consume(int ProductstoRemove, String Name) {
		try {
			while ((this.articles - ProductstoRemove) < 0) {
				wait();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		this.articles = this.articles - ProductstoRemove;
		System.out.println(
				"El Consumidor " + Name + " ha retirado " + ProductstoRemove + " productos. Stock = " + this.articles);
		notifyAll();
	}

	public int getarticles() {
		return articles;
	}
}
