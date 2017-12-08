import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Friend {
	private final String name;
	private Lock lock = new ReentrantLock();
	public Friend(String name) {
		this.name = name;
	}

	public  String getName() {
		return this.name;
	}

	public void hello(Friend helloer) {
		lock .lock();
		System.out.println(this.name + ": " + helloer.getName() + " told me hello!");
		lock.unlock();
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
		}
		helloer.helloBack(this);
	}

	public void helloBack(Friend helloer) {
		lock .lock();
		System.out.println(this.name + " : " + helloer.getName() + " gave me hello back");
		lock.unlock();

	}

	public static void main(String[] args) {
		final Friend amin = new Friend("Amin");
		final Friend barbara = new Friend("Barbara");
		new Thread(new Runnable() {
			public void run() {
				amin.hello(barbara);
			}
		}).start();
		new Thread(new Runnable() {
			public void run() {
				barbara.hello(amin);
			}
		}).start();
	}
}