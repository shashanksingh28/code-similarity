interface Shape {
    public boolean contains (double x, double y);
}

public class Rectangle implements Shape {
    private int x0;
    private int y0;
    private int width;
    private int height;
    
    Rectangle(int x0, int y0, int width, int height){
        this.x0 = x0;
        this.y0 = y0;
        this.width = width;
        this.height = height;
    }
    
    public int getWidth(){
        return width;
    }
    
    public int getHeight(){
        return height;
    }
    
    public int getX(){
        return x0;
    }
    
    public int getY(){
        return y0;
    }
    
    public boolean contains(double x, double y) {
      double x0 = this.getX();
      double y0 = this.getY();
      return (x >= x0 && y >= y0 && x < x0 + getWidth() && y < y0 + getHeight());
    }
    
    // Objects Inheritance Classes ;Strings; Interfaces ; BooleanExpressions Variables ArithmeticOperations
    public static void main(String args[]){
        Shape box = new Rectangle( 0, 0, 10, 20);
        System.out.println(box.contains(50, 10));
    }
    
    public void listIteration(){
        ArrayList<Double> list = new ArrayList<Double>();
        list.add(1.1);
        list.add(2.2);
        list.add(3.3);
        list.remove(0);
        for(Double d : list)
            System.out.println(d);
    }
}
