
class Point {

    int x;
    int y;
}

class TestPoint {

    public static void main(String[] args) {
        System.out.println("Creating a Point object ... ");
        Point p = new Point();
        System.out.println("Initializing data members ...");
        p.x = 4;
        p.y = 5;
        System.out.println("Printing object");
        System.out.println("Point p (" + p.x + ", " + p.y + ")");
    }
}