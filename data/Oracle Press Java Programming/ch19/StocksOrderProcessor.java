
import java.util.*;
import java.util.concurrent.*;

public class StocksOrderProcessor {

    static final int MAX_NUMBER_OF_ORDERS = 10000;
    static private ExecutorService executor =
            Executors.newFixedThreadPool(100);
    static private List<Future> ordersToProcess = new ArrayList();

    private static class OrderExecutor implements Callable {

        int id = 0;
        int count = 0;

        public OrderExecutor(int id) {
            this.id = id;
        }

        public Object call() throws Exception {
            try {
                while (count < 50) {
                    count++;
                    Thread.sleep(new Random(
                            System.currentTimeMillis() % 100).nextInt(10));
                }
                System.out.println("Successfully executed order: " + id);
            } catch (Exception ex) {
                throw (ex);
            }
            return id;
        }
    }

    public static void main(String[] args) {
        System.out.printf("Submitting %d trades%n", MAX_NUMBER_OF_ORDERS);
        for (int i = 0; i < MAX_NUMBER_OF_ORDERS; i++) {
            SubmitOrder(i);
        }
        new Thread(new EvilThread(ordersToProcess)).start();
        System.out.println("Cancelling a few orders at random");
        try {
            executor.awaitTermination(30, TimeUnit.SECONDS);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        System.out.println("Checking status before shutdown");
        int count = 0;
        for (Future f : ordersToProcess) {
            if (f.isCancelled()) {
                count++;
            }
        }
        System.out.printf("%d trades cancelled%n", count);
        executor.shutdownNow();
    }

    private static void SubmitOrder(int id) {
        Callable<Integer> callable = new OrderExecutor(id);
        ordersToProcess.add(executor.submit(callable));
    }
}

class EvilThread implements Runnable {

    private List<Future> ordersToProcess;

    public EvilThread(List<Future> futures) {
        this.ordersToProcess = futures;
    }

    public void run() {
        Random myNextKill = new Random(System.currentTimeMillis()
                % 100);
        for (int i = 0; i < 100; i++) {
            int index =
                    myNextKill.nextInt(StocksOrderProcessor.MAX_NUMBER_OF_ORDERS);
            boolean cancel = ordersToProcess.get(index).cancel(true);
            if (cancel) {
                System.out.println("Cancel Order Succeeded: " + index);
            } else {
                System.out.println("Cancel Order Failed: " + index);
            }
            try {
                Thread.sleep(myNextKill.nextInt(100));
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
}