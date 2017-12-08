import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Exo1 {

	private static Lock lock = new ReentrantLock();
	private static List<Integer> maListThreadInteger;

	public static Runnable maMethode(int i) {
		return new Runnable() {
			@Override
			public void run() {

				int j = 0;

				while (j < 50000) {

					j++;
					lock.lock();
					maListThreadInteger.add(j); // .add n'est pas synchronize donc on devoir ajouter des locks de part
												// et d'autre de la methode
					lock.unlock();
				}
				System.out.println(Thread.currentThread() + " " + j);

			}

		};

	}

	public static void main(String[] args) throws InterruptedException {

		List<Thread> maListedeThreads = new ArrayList<Thread>();
		maListThreadInteger = new ArrayList<Integer>(Integer.parseInt(args[0]));

		for (int i = 0; i < Integer.parseInt(args[0]); i++) {
			Thread thread = new Thread(Exo1.maMethode(i));
			maListedeThreads.add(thread);
			thread.start();
		}

		// On lance le scanner
		// System.out.println("Rentrez le thread à interrompre svp ");
		// Scanner sc = new Scanner(System.in);
		// while (!(maListedeThreads.isEmpty())) {
		// int a = sc.nextInt();
		// maListedeThreads.get(a).interrupt();
		// maListedeThreads.remove(maListedeThreads.get(a));
		// }

		// le prg s'arrette quand tous les threads sont interrompus

		for (Thread thread : maListedeThreads) {

			thread.join(0);
		}

		System.out.println("j’ai tout fini");
		System.out.println("Commun : " + maListThreadInteger.size());

	}

}
