
public class TypeConversion {

    public static void main(String[] args) {
        float f = 12.25f;
        int i = -200;
        char c = 'A';
        i = (int) f; // floating point to integer conversion
        System.out.println("float type " + f
                + " assigned to an int produces " + i);
        f = i;
        System.out.println("int type " + i
                + " assigned to a float produces " + f);
        i = c;
        System.out.println("char type " + c
                + " assigned to an int produces " + i);
        i = 66;
        c = (char) i;
        System.out.println("int type " + i + " assigned to a char produces " + c);
    }
}