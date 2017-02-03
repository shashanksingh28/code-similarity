
import java.util.concurrent.*;

public class NaturalLogCalc {

    private static final int numberOfTerms = 10;
    private static double[] termArray = new double[numberOfTerms];
    private static final float x = 0.2f;

    public static void main(String[] args) {
        CyclicBarrier barrier = new CyclicBarrier(numberOfTerms, new Runnable() {

            @Override
            public void run() {
                System.out.println("Computing series sum");
                double sum = 0;
                for (double term : termArray) {
                    sum += term;
                }
                System.out.println("ln (1-" + x + ") equals " + -sum);
            }
        });
        for (int i = 0; i < numberOfTerms; i++) {
            new Thread(new TermCalc(barrier, i)).start();
        }
        System.out.println("Waiting...");
    }

    private static class TermCalc implements Runnable {

        private int termIndex;
        private CyclicBarrier barrier;

        public TermCalc(CyclicBarrier barrier, int termIndex) {
            this.barrier = barrier;
            this.termIndex = termIndex;
        }

        @Override
        public void run() {
            double result = Math.pow(x, termIndex + 1) / (termIndex + 1);
            termArray[termIndex] = result;
            System.out.println("Term " + (termIndex + 1) + ": " + result);
            try {
                barrier.await();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            } catch (BrokenBarrierException ex) {
                ex.printStackTrace();
            }
        }
    }
}