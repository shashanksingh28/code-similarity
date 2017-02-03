
class Point {

    private int x;
    private int y;

    public Point() {
        x = 10;
        y = 10;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}

class CustomConstructorApp {

    public static void main(String[] args) {
        System.out.println("Creating a Point object ... ");
        Point p = new Point();
        System.out.println("\nPrinting Point object");
        System.out.println("Point p (" + p.getX() + ", " + p.getY() + ")");
    }
}