
import java.awt.Color;
import java.io.*;

public class NestedObjectsApp {

    public static void main(String args[]) {
        Line line = new Line();
        System.out.println("Before saving object:\n" + line);
        try (ObjectOutputStream outStream = new ObjectOutputStream(
                        new FileOutputStream("graph.dat"))) {
            outStream.writeObject(line);
        } catch (IOException ex) {
            System.out.println("Error writing object");
        }
        try (ObjectInputStream inStream = new ObjectInputStream(
                        new FileInputStream("graph.dat"));) {
            line = (Line) inStream.readObject();
        } catch (IOException ioe) {
            System.out.println("Error reading object");
        } catch (ClassNotFoundException cfe) {
            System.out.println("Casting error");
        }
        System.out.println("\nAfter retrieving object:\n" + line);
    }
}

class Point implements Serializable {

    protected int x;
    protected int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
}

class ColorPoint extends Point implements Serializable {

    private Color color;

    public ColorPoint(int x, int y, Color color) {
        super(x, y);
        this.color = color;
    }

    @Override
    public String toString() {
        return "Point{" + "x=" + x + " y=" + y + '}'
                + " ColorPoint{" + "color=" + color + '}';
    }
}

class Line implements Serializable {

    private ColorPoint startPoint = new ColorPoint(0, 0, Color.red);
    private ColorPoint endPoint = new ColorPoint(10, 10, Color.blue);

    @Override
    public String toString() {
        return "StartPoint=" + startPoint + "\nEndPoint=" + endPoint;
    }
}