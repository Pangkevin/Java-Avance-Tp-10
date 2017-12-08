
public class GetSet {
	private final Object readLock = new Object();
	private final Object writeLock = new Object();
	private int val;

	public void setValue(int v) {
		synchronized (readLock) {
			synchronized (writeLock) {
				this.val = v;
			}
		}
	}

	public int getVal() {
		synchronized (readLock) {
			return val;
		}
	}

	public void go() throws InterruptedException {
		new Thread(new Runnable() {
			@Override
			public void run() {
				setValue(42);
			}
		}).start();
		synchronized (writeLock) {
			Thread.sleep(100);
			System.out.println(getVal());
		}
	}

	public static void main(String[] args) throws InterruptedException {
		GetSet b = new GetSet();
		b.go();
	}
}