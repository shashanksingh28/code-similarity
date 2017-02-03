
import java.io.*;

public class ControlledPrimeNumberGenerator {

    public static void main(String[] args) {
        Thread primeNumberGenerator = new Thread(new WorkerThread());
        primeNumberGenerator.start();
        InputStreamReader in = new InputStreamReader(System.in);
        try {
            while (in.read() != '\n') {
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        primeNumberGenerator.interrupt();
// uncomment the following lines to introduce a delay
// before checking the interrupt status
// try {
// Thread.sleep(100);
// } catch (InterruptedException ex) {
// }
        if (primeNumberGenerator.isInterrupted()) {
            System.out.println("\nNumber generation has "
                    + "already been interrupted");
        } else {
            System.out.println("Number generator "
                    + "is not currently running");
        }
        Thread lazyWorker = new Thread(new LazyWorker());
        lazyWorker.start();
        System.out.println("\nRunning lazy worker");
        try {
            Thread.sleep(100);
        } catch (InterruptedException ex) {
        }
        lazyWorker.interrupt();
    }
}

class WorkerThread implements Runnable {

    public void run() {
        long i = 1;
        while (true) {
            long j;
            for (j = 2; j < i; j++) {
                long n = i % j;
                if (n == 0) {
                    break;
                }
            }
            if (i == j) {
                System.out.print(" " + i);
            }
            i++;
            if (Thread.interrupted()) {
                System.out.println("\nStopping prime "
                        + "number generator");
                return;
            }
        }
    }
}

class LazyWorker implements Runnable {

    public void run() {
        try {
            Thread.sleep(100000);
        } catch (InterruptedException ex) {
            System.out.println("Lazy worker: " + ex.toString());
        }
    }
}