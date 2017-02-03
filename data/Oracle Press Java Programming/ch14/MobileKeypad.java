
import java.awt.GridLayout;
import javax.swing.*;

public class MobileKeypad extends JFrame {

    public MobileKeypad() {
        setTitle("Mobile Keypad");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        initGUI();
    }

    public static void main(String[] args) {
        MobileKeypad app = new MobileKeypad();
        app.setSize(220, 240);
        app.setVisible(true);
    }

    private void initGUI() {
        setLayout(new GridLayout(4, 3));
        add(new JButton("1"));
        add(new JButton("2"));
        add(new JButton("3"));
        add(new JButton("4"));
        add(new JButton("5"));
        add(new JButton("6"));
        add(new JButton("7"));
        add(new JButton("8"));
        add(new JButton("9"));
        add(new JButton("+"));
        add(new JButton("0"));
        add(new JButton("#"));
    }
}