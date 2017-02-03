
public class IntToFloatConverter {

    public static void main(String[] args) {
        int bigInteger = 1987654321;
        float floatNumber = bigInteger;
        int loss = bigInteger - (int) floatNumber;
        System.out.println("Precision loss: " + loss);
    }
}