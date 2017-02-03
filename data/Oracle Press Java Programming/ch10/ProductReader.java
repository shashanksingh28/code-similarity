
import java.io.*;

public class ProductReader {

    public static void main(String args[]) throws Exception {
        ObjectInputStream is = new ObjectInputStream(
                new FileInputStream("product.dat"));
        Product p1 = (Product) is.readObject();
        System.out.println(p1.toString());
    }
}