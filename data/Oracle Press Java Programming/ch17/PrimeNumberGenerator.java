
public class PrimeNumberGenerator {

    public static void main(String[] args) {
        Thread primeNumberGenerator = new Thread(new WorkerThread());
        primeNumberGenerator.setDaemon(true);
        primeNumberGenerator.start();
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
        }
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
        }
    }
}