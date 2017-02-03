
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class IntrospectionTestApplication extends JFrame {

    private String displayString = "Hello";
    private Font textFont = new Font("Arial", Font.PLAIN, 20);
    private Color textColor = new Color(255, 0, 0);
    private JLabel labelDisplay;

    IntrospectionTestApplication() {
        super("Introspection Test");
        setBounds(200, 200, 200, 200);
        setResizable(false);
        labelDisplay = new JLabel("Hello", JLabel.CENTER);
        add(labelDisplay, BorderLayout.CENTER);
        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent evt) {
                dispose();
            }
        });
        setVisible(true);
    }

    public static void main(String args[]) {
        new IntrospectionTestApplication();
    }

    public void setDefaultString() {
        displayString = "Hello";
        labelDisplay.setText(displayString);
    }

    public void setDisplayColor(short red, short blue, short green) {
        textColor = new Color(red, blue, green);
        labelDisplay.setForeground(textColor);
    }

    public void setDisplayString(String str) {
        displayString = str;
        labelDisplay.setText(displayString);
    }

    public void setFontSize(int size) {
        textFont = new Font("Arial", Font.PLAIN, size);
        labelDisplay.setFont(textFont);
    }
}