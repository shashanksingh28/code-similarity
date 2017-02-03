
public class ArrayLengthApp {

    public static void main(String[] args) {
        final int SIZE = 5;
        int[] integerArray = new int[SIZE];
        float[] floatArray = {5.0f, 3.0f, 2.0f, 1.5f};
        String[] weekDays = {"Sunday", "Monday", "Tuesday",
            "Wednesday", "Thursday", "Friday", "Saturday"};
        int[][] jaggedArray = {
            {5, 4},
            {10, 15, 12, 15, 18},
            {6, 9, 10},
            {12, 5, 8, 11}
        };
        System.out.println("integerArray length: " + integerArray.length);
        System.out.println("floatArray length: " + floatArray.length);
        System.out.println("Number of days in a week: " + weekDays.length);
        System.out.println("Length of jaggedArray: " + jaggedArray.length);
        int row = 0;
        for (int[] memberRow : jaggedArray) {
            System.out.println("\tArray length for row "
                    + ++row + ": " + memberRow.length);
        }
    }
}