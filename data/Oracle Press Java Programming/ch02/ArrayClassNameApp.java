
public class ArrayClassNameApp {

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
        Class cls = integerArray.getClass();

        System.out.println(
                "The class name of integerArray: " + cls.getName());
        cls = floatArray.getClass();

        System.out.println(
                "The class name of floatArray: " + cls.getName());
        cls = weekDays.getClass();

        System.out.println(
                "The class name of weekDays: " + cls.getName());
        cls = jaggedArray.getClass();

        System.out.println(
                "The class name of jaggedArray: " + cls.getName());
        System.out.println();
        cls = cls.getSuperclass();

        System.out.println(
                "The super class of an array object: "
                + cls.getName());
    }
}