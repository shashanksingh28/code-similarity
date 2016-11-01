import java.awt.Rectangle;
public class RectTester {
    public static void main(String[] args) {
        Rectangle box = new Rectangle(5, 10, 20, 30);
        box.translate(10, 25);
        System.out.print(box.getX());
    }    
}
