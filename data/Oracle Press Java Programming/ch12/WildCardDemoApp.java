
public class WildCardDemoApp {

    public static void main(String args[]) {
        System.out.println("Creating 'Long' stack:");
        NumberStack<Long> longStack = new NumberStack<Long>();
        longStack.push(5L);
        longStack.push(10L);
        System.out.println("Creating 'Number' stack:");
        NumberStack<Number> numberStack = new NumberStack<Number>();
        numberStack.push(10L);
        System.out.println("\nDumping 'Long' stack");
        dumpStack(longStack);
        System.out.println("\nDumping 'Number' stack");
        dumpStack(numberStack);
    }

    static void dumpStack(NumberStack<?> stack) {
        for (Number n : stack.getStack()) {
            System.out.println(n);
        }
    }
}

class NumberStack<T extends Number> {

    private Number stack[] = new Number[5];
    private int ptr = -1;

    public Number[] getStack() {
        return stack;
    }

    void push(T data) {
        ptr++;
        stack[ptr] = data;
    }

    T pop() {
        return (T) stack[ptr--];
    }
}