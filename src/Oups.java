public class Oups {
	volatile static int nb;

	public static void main(String[] args) {
		for (int i = 0; i < 2; i++) {
			final int id = i;
			new Thread(new Runnable() {
				public void run() {
					while (true) {
						nb = id;
						if (nb != id)
							System.out.println("oups");
					}
				}
			}).start();
		}
	}
}