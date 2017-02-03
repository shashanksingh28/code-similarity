
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class ListDemoApp {

    public static void main(String[] args) {
        MyFrame frame = new MyFrame("List Demo");
        frame.setBounds(20, 50, 400, 300);
        frame.setVisible(true);
    }
}

class MyFrame extends JFrame implements ActionListener {

    private DefaultListModel sourceModel;
    private DefaultListModel destModel;
    private JList source;
    private JList dest = new JList();
    private JButton addButton = new JButton(">>");
    private JButton removeButton = new JButton("<<");

    public MyFrame(String title) {
        super(title);
        addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        sourceModel = new DefaultListModel();
        sourceModel.addElement("Banana");
        sourceModel.addElement("Apple");
        sourceModel.addElement("Orange");
        sourceModel.addElement("Mango");
        sourceModel.addElement("Pineapple");
        sourceModel.addElement("Kiwi");
        sourceModel.addElement("Strawberry");
        sourceModel.addElement("Peach");
        source = new JList(sourceModel);
        source.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        source.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 5), ""
                + "Shop", 0, 0, null, Color.RED));
        source.setSelectedIndex(0);
        source.setSelectionBackground(Color.BLACK);
        source.setSelectionForeground(Color.WHITE);
        destModel = new DefaultListModel();
        dest.setModel(destModel);
        dest.setSelectionBackground(Color.BLACK);
        dest.setSelectionForeground(Color.WHITE);
        dest.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 5), ""
                + "Fruit Basket", 0, 0, null, Color.RED));
// Building GUI
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1, 20, 20));
        panel.add(new JLabel());
        panel.add(addButton);
        panel.add(removeButton);
        panel.add(new JLabel());
        this.setLayout(new GridLayout(1, 3, 20, 20));
        add(source);
        add(panel);
        add(dest);
// Setting event handlers
        addButton.addActionListener(this);
        removeButton.addActionListener(this);
    }

    public void actionPerformed(ActionEvent evt) {
        if (evt.getSource().equals(addButton)) {
            if (source.getSelectedValue() != null) {
                String str = (String) source.getSelectedValue();
                if (str != null) {
                    destModel.addElement(str);
                    dest.setSelectedIndex(0);
                    sourceModel.removeElement(str);
                    source.setSelectedIndex(0);
                }
            }
        }
        if (evt.getSource().equals(removeButton)) {
            if (dest.getSelectedValue() != null) {
                String str = (String) dest.getSelectedValue();
                if (str != null) {
                    sourceModel.addElement(str);
                    source.setSelectedIndex(0);
                    destModel.removeElement(str);
                    dest.setSelectedIndex(0);
                }
            }
        }
    }
}