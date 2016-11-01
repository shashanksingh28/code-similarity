import java.util.*;

class RecursionSample{

    public static int nthFibonacciRecursive(int n){
        if (n == 0){
            return 1;
        }

        if (n == 1){
            return 1;
        }
        
        return nthFibonacciRecursive(n - 1) + nthFibonacciRecursive(n - 2);
    }

    public static void nthFibonacciDynamic(int n){
        
    }

    public static int nthFibonacciDynamic(int n, HashMap<Integer, Integer> memo){
        if(memo.containsKey(n)){
            return memo.get(n);
        }
        
        if (n == 0){
            return 1;
        }

        if (n == 1){
            return 1;
        }

        int value = nthFibonacciDynamic(n - 1, memo) + nthFibonacciDynamic(n - 2, memo);
        memo.put(n, value);
        return value;
    }
}
