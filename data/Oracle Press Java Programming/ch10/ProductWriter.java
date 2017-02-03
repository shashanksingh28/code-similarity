
import java.io.*;

public class ProductWriter {

    public static void main(String args[]) throws IOException {
        Product p1 = new Product(100);
        ObjectOutputStream os = new ObjectOutputStream(
                new FileOutputStream("product.dat"));
        os.writeObject(p1);
        os.close();
    }
}

class Product implements Serializable {

    private float price;
    private float tax;

    public Product(float price) {
        this.price = price;
        tax = (float) (price * 0.20);
    }
}