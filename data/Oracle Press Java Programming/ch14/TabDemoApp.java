
import javax.swing.*;
import java.awt.*;

public class TabDemoApp {

    public static void main(String[] args) {
        TabFrame frame = new TabFrame("Tab Demo");
        frame.setSize(500, 200);
        frame.setVisible(true);
    }
}

class TabFrame extends JFrame {

    public TabFrame(String title) {
        super(title);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        initGUI();
    }

    public void initGUI() {
        JTabbedPane tabbedPane = new JTabbedPane();
//Create the "cards".
        tabbedPane.addTab("Address", new AddressPanel());
        tabbedPane.addTab("Memo", new MemoPanel());
        getContentPane().add(tabbedPane, BorderLayout.CENTER);
    }
}

class MemoPanel extends JPanel {

    public MemoPanel() {
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        add(new JLabel("Enter Memo"));
        add(new JTextField());
        add(new JButton("OK"));
    }
}

class AddressPanel extends JPanel {

    public AddressPanel() {
        setLayout(new BorderLayout(10, 0));
        JPanel leftPanel = new JPanel() {

            @Override
            public Dimension getPreferredSize() {
                Dimension size = super.getPreferredSize();
                size.width += 20;
                return size;
            }
        };
        leftPanel.setLayout(new GridLayout(4, 1, 10, 10));
        leftPanel.add(new JLabel("Name", JLabel.RIGHT));
        leftPanel.add(new JLabel("Address 1", JLabel.RIGHT));
        leftPanel.add(new JLabel("Address 2", JLabel.RIGHT));
        leftPanel.add(new JLabel("City", JLabel.RIGHT));
        add(leftPanel, BorderLayout.LINE_START);
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new GridLayout(4, 1, 10, 10));
        rightPanel.add(new JTextField(20));
        rightPanel.add(new JTextField(10));
        rightPanel.add(new JTextField(15));
        rightPanel.add(new JTextField(5));
        add(rightPanel, BorderLayout.CENTER);
    }
}