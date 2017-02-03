
public class ProducerConsumerGame {

    public static void main(String args[]) {
        Bucket bucket = new Bucket();
        new Thread(new Producer(bucket)).start();
        new Thread(new Consumer(bucket)).start();
    }
}

final class Consumer implements Runnable {

    private Bucket bucket;

    public Consumer(Bucket bucket) {
        this.bucket = bucket;
    }

    public void run() {
        for (int i = 0; i < 10; i++) {
            bucket.get();
        }
    }
}

final class Producer implements Runnable {

    private Bucket bucket;

    public Producer(Bucket bucket) {
        this.bucket = bucket;
    }

    public void run() {
        for (int i = 0; i < 10; i++) {
            bucket.put((int) (Math.random() * 100));
        }
    }
}

class Bucket {

    private int packOfBalls;
    private boolean available = false;

    public synchronized int get() {
        if (available == false) {
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }
        System.out.println("Consumer Got: " + packOfBalls);
        available = false;
        notify();
        return packOfBalls;
    }

    public synchronized void put(int packOfBalls) {
        if (available) {
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }
        this.packOfBalls = packOfBalls;
        available = true;
        System.out.println("Producer Put: " + packOfBalls);
        notify();
    }
}