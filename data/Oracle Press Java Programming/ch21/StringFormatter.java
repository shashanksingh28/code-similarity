
import java.util.*;

public class StringFormatter {

    public static void main(String[] args) {
        float rate = 12.5f;
        int quantity = 100;
        float total = 1250;
        System.out.printf("Rate: %1$.2f Quantity:%2$d Total:%3$.2f\n",
                rate, quantity, total);
        System.out.printf("Total: %3$.2f Quantity:%2$d Rate:%1$.2f\n\n",
                rate, quantity, total);
        float f = (float) 123456789.98;
        System.out.printf("US - Price: %,.2f\n", f);
        System.out.printf(Locale.FRANCE, "France - Price: %,.2f\n", f);
        System.out.printf(Locale.GERMANY, "German - Price: %,.2f\n", f);
        System.out.printf(Locale.CHINA, "China - Price: %,.2f\n\n", f);
        Calendar calendar = Calendar.getInstance();
        System.out.printf("The current local time is %tr on "
                + "%<tA, %<tB %<te, %<tY.%n", calendar);
        System.out.printf(Locale.FRANCE, "The current local time is %tr on "
                + "%<tA, %<tB %<te, %<tY.%n", calendar);
        System.out.printf(Locale.GERMANY, "The current local time is %tr on "
                + "%<tA, %<tB %<te, %<tY.%n", calendar);
        System.out.printf(Locale.CHINA, "The current local time is %tr on "
                + "%<tA, %<tB %<te, %<tY.%n", calendar);
    }
}