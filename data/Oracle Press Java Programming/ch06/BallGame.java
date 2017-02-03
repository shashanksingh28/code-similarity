
class Ball {

    private static int count = 0;

    public static int getCount() {
        return count;
    }

    public Ball() {
        count++;
    }
}

public class BallGame {

    public static void main(String[] args) {
        for (int i = 0; i < 10000; i++) {
            int number = (int) (Math.random() * 10);
            if (number == 5) {
                new Ball();
            }
        }
        System.out.println("No of balls created: " + Ball.getCount());
    }
}