
import java.util.*;

public class BitwiseOperators {

    public static void main(String[] args) {
        int m = 0xFFFFFFFF, n = 0x80000000, result;
        result = m & n;
        StringBuilder builder = new StringBuilder();
        Formatter formatter = new Formatter(builder, Locale.US);
        formatter.format("Logical AND: %X & %X = %X\n", m, n, result);
        m = 0x0000FFFF;
        n = 0xFFFF0000;
        result = m | n;
        formatter.format("Logical OR: %X & %X = %X\n", m, n, result);
        m = 0x8000FFFF;
        n = 0xFFFF8000;
        result = m ^ n;
        formatter.format("Logical XOR: %X & %X = %X\n", m, n, result);
        m = 0x7FFF0000;
        result = ~m;
        formatter.format("Logical NOT: ~%X = %X\n", m, result);
        m = 0xFFFF0000;
        result = m << 4;
        formatter.format("Left Shift: %X << 4 = %X\n", m, result);
        m = 0xFFFF0000;
        result = m >> 4;
        formatter.format("Right Shift: %X >> 4 = %X\n", m, result);
        m = 0xFFFF0000;
        result = m >>> 1;
        formatter.format(
                "Right Shift with zero fill: %X >>> 1 = %X", m, result);
        System.out.println(builder);
    }
}