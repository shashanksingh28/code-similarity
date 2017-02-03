
import java.util.Arrays;

public class ArrayCopyApp {

    public static void main(String[] args) {
        float[] floatArray = {5.0f, 3.0f, 2.0f, 1.5f};
        float[] floatArrayCopy = floatArray.clone();
        System.out.println(Arrays.toString(floatArray) + " - Original");
        System.out.println(Arrays.toString(floatArrayCopy) + " - Copy");
        System.out.println();
        System.out.println("Modifying the second element of the original array");
        floatArray[1] = 20;
        System.out.println(Arrays.toString(floatArray)
                + " - Original after modification");
        System.out.println(Arrays.toString(floatArrayCopy) + " - Copy");
        System.out.println();
        System.out.println("Modifying the third element of the copy array");
        floatArrayCopy[2] = 30;
        System.out.println(Arrays.toString(floatArray) + " - Original");
        System.out.println(Arrays.toString(floatArrayCopy)
                + " - Copy array after modification");
    }
}