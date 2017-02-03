
import java.util.*;

public class ThreadScheduler {

    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            list.add((int) (Math.random() * 10));
        }
        PriorityQueue<Integer> threadQueue = new PriorityQueue<>();
        threadQueue.addAll(list);
        System.out.println("Waiting threads...");
        for (Integer thread : threadQueue) {
            System.out.print(thread + ",");
        }
        System.out.println("\nDeploying threads...");
        while (!threadQueue.isEmpty()) {
            System.out.print(threadQueue.remove() + ",");
        }
    }
}