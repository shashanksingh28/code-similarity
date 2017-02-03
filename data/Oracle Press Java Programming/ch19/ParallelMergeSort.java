
import java.util.concurrent.*;

public class ParallelMergeSort {

    private static ForkJoinPool threadPool;
    private static final int THRESHOLD = 16;

    private static void sort(Comparable[] objectArray) {
        Comparable[] destArray = new Comparable[objectArray.length];
        threadPool.invoke(new SortTask(objectArray,
                destArray, 0, objectArray.length - 1));
    }

    static class SortTask extends RecursiveAction {

        private Comparable[] sourceArray;
        private Comparable[] destArray;
        private int lowerIndex, upperIndex;

        public SortTask(Comparable[] sourceArray,
                Comparable[] destArray,
                int lowerIndex,
                int upperIndex) {
            this.sourceArray = sourceArray;
            this.lowerIndex = lowerIndex;
            this.upperIndex = upperIndex;
            this.destArray = destArray;
        }

        @Override
        protected void compute() {
            if (upperIndex - lowerIndex < THRESHOLD) {
                insertionSort(sourceArray, lowerIndex, upperIndex);
                return;
            }
            int midIndex = (lowerIndex + upperIndex) >>> 1;
            invokeAll(new SortTask(sourceArray, destArray, lowerIndex, midIndex),
                    new SortTask(sourceArray, destArray, midIndex + 1,
                    upperIndex));
            merge(sourceArray, destArray, lowerIndex, midIndex, upperIndex);
        }
    }

    private static void merge(Comparable[] sourceArray,
            Comparable[] destArray, int lowerIndex,
            int midIndex, int upperIndex) {
        if (sourceArray[midIndex].compareTo(
                sourceArray[midIndex + 1]) <= 0) {
            return;
        }
        System.arraycopy(sourceArray, lowerIndex,
                destArray, lowerIndex, midIndex - lowerIndex + 1);
        int i = lowerIndex;
        int j = midIndex + 1;
        int k = lowerIndex;
        while (k < j && j <= upperIndex) {
            if (destArray[i].compareTo(sourceArray[j]) <= 0) {
                sourceArray[k++] = destArray[i++];
            } else {
                sourceArray[k++] = sourceArray[j++];
            }
        }
        System.arraycopy(destArray, i, sourceArray, k, j - k);
    }

    private static void insertionSort(Comparable[] objectArray,
            int lowerIndex, int upperIndex) {
        for (int i = lowerIndex + 1; i <= upperIndex; i++) {
            int j = i;
            Comparable tempObject = objectArray[j];
            while (j > lowerIndex
                    && tempObject.compareTo(objectArray[j - 1]) < 0) {
                objectArray[j] = objectArray[j - 1];
                --j;
            }
            objectArray[j] = tempObject;
        }
    }

    public static Double[] createRandomData(int length) {
        Double[] data = new Double[length];
        for (int i = 0; i < data.length; i++) {
            data[i] = length * Math.random();
        }
        return data;
    }

    public static void main(String[] args) {
        int processors = Runtime.getRuntime().availableProcessors();
        System.out.println("No of processors: " + processors);
        threadPool = new ForkJoinPool(processors);
        Double[] data = createRandomData(1000);
        System.out.println("Original unsorted data:");
        for (Double d : data) {
            System.out.printf("%3.2f ", (double) d);
        }
        sort(data);
        System.out.println("\n\nSorted Array:");
        for (Double d : data) {
            System.out.printf("%3.2f ", d);
        }
    }
}