
import java.util.concurrent.*;

public class Bank {

    private final static int COUNT = 100;
    private final static Semaphore semaphore =
            new Semaphore(2, true);

    public static void main(String[] args) {
        for (int i = 0; i < COUNT; i++) {
            final int count = i;
            new Thread() {

                @Override
                public void run() {
                    try {
                        if (semaphore.tryAcquire(10,
                                TimeUnit.MILLISECONDS)) {
                            try {
                                Teller.getService(count);
                            } finally {
                                semaphore.release();
                            }
                        }
                    } catch (InterruptedException ex) {
                    }
                }
            }.start();
        }
    }
}

class Teller {

    static public void getService(int i) {
        try {
            System.out.println("serving: " + i);
            Thread.sleep((long) (Math.random() * 10));
        } catch (InterruptedException ex) {
        }
    }
}