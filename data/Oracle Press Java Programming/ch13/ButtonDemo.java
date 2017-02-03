
import javax.swing.*;
import java.awt.event.*;

public class ButtonDemo {

    public static void main(String[] args) {
        MyFrame frame = new MyFrame("Button Demo");
        frame.setSize(200, 200);
        frame.setVisible(true);
    }
}

class MyFrame extends JFrame implements ActionListener {

    private JButton closeButton = new JButton("Close");
    private JButton messageButton = new JButton("Hide");
    private JLabel label = new JLabel(
            "Java Programming is easy", JLabel.CENTER);

    public MyFrame(String str) {
        super(str);
        add(messageButton, "North");
        add(closeButton, "South");
        add(label, "Center");
        messageButton.addActionListener(this);
        closeButton.addActionListener(this);
    }

    public void actionPerformed(ActionEvent evt) {
        if (evt.getSource().equals(messageButton)) {
            if (messageButton.getText().equals("Show")) {
                label.setVisible(true);
                messageButton.setText("Hide");
            } else {
                label.setVisible(false);
                messageButton.setText("Show");
            }
        } else if (evt.getSource().equals(closeButton)) {
            System.exit(0);
        }
    }
}