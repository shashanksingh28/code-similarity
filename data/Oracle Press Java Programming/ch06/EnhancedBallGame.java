
import java.awt.Color;

class Ball {

    private static int count = 0;
    private static int redBallCount = 0;
    private static int greenBallCount = 0;
    private static int radius = 0;
    private Color defaultColor;

    public static int getRedBallCount() {
        return redBallCount;
    }

    public static int getGreenBallCount() {
        return greenBallCount;
    }

    public static int getRadius() {
        return radius;
    }

    public static void setRadius(int radius) {
        Ball.radius = radius;
    }

    public Ball(Color color) {
        count++;
        if (color == Color.RED) {
            this.defaultColor = Color.RED;
            redBallCount++;
        } else {
            this.defaultColor = Color.GREEN;
            greenBallCount++;
        }
    }

    public void setVelocity(double v) {
        String strColor = null;
        if (defaultColor == Color.RED) {
            strColor = "Red";
        } else {
            strColor = "Green";
        }
        System.out.printf("Ball #%d:%-10s velocity set to %.02f%n",
                count, strColor, v);
    }
}

public class EnhancedBallGame {

    public static void main(String[] args) {
        int numberOfBalls = (int) (Math.random() * 10);
        int radius = (int) (Math.random() * 20) + 1;
        Ball.setRadius(radius);
        System.out.printf("Creating %d balls of radius %d%n",
                numberOfBalls, Ball.getRadius());
        for (int i = 0; i < numberOfBalls; i++) {
            int number = (int) (Math.random() * 2);
            if (number == 0) {
                new Ball(Color.RED).setVelocity(Math.random() * 10);
            } else {
                new Ball(Color.GREEN).setVelocity(Math.random() * 10);
            }
        }
        System.out.println("Number of red balls created: "
                + Ball.getRedBallCount());
        System.out.println("Number of green balls created: "
                + Ball.getGreenBallCount());
    }
}