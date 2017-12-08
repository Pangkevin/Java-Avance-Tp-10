import java.util.concurrent.locks.ReentrantLock;

public class Diner {
	private final ReentrantLock[] forks;

	public Diner(int nbPlates) {
		forks = new ReentrantLock[nbPlates];
		for (int i = 0; i < nbPlates; ++i)
			forks[i] = new ReentrantLock();
	}

	public void eat(int guyIndex) {
		ReentrantLock forkLeft = forks[guyIndex];
		ReentrantLock forkRight = forks[(guyIndex + 1) % forks.length];
		boolean flag = true;
		int i =0;
		
		while(flag) {
			
		forkLeft.lock();
		i++;
		try {
			Thread.sleep(5);// minisieste
			if (forkRight.tryLock()) {
				// Si il arrive à choper la fourchette droite, il peut manger
				try {
					System.out.println("Guy " + guyIndex + " is eating.");
					
					System.out.println("Au bout de : "+i);
					flag = false;
				} finally {
					forkRight.unlock();
				}
			}
			
		} catch (InterruptedException e) {
		} finally {
			forkLeft.unlock();
		}
		}
	}

	public static void main(String[] args) {
		int nbGuys = 5;
		Diner d = new Diner(nbGuys);
		for (int i = 0; i < nbGuys; ++i) {
			final int guy = i;
			new Thread(new Runnable() {
				@Override
				public void run() {
					d.eat(guy);
				}
			}).start();
		}
	}
}