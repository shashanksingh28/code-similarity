
import javax.swing.*;
import java.awt.*;

public class FlowLayoutDemoApp {

    public static void main(String[] args) {
        MyFrame frame = new MyFrame("FlowLayout Demo");
        frame.setSize(500, 300);
        frame.setVisible(true);
    }
}

class MyFrame extends JFrame {

    public MyFrame(String title) {
        super(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container pane = getContentPane();
        pane.setLayout(new FlowLayout());
        pane.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        JButton button = new JButton("ONE");
        pane.add(button);
        button = new JButton("TWO");
        pane.add(button);
        button = new JButton("THREE");
        pane.add(button);
        button = new JButton("FOUR");
        pane.add(button);
        button = new JButton("FIVE");
        pane.add(button);
    }
}