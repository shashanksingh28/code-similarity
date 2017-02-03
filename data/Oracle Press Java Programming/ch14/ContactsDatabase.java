
import java.awt.*;
import java.awt.event.*;
import java.util.logging.*;
import javax.swing.*;

public class ContactsDatabase extends JFrame implements ActionListener {

    private JPanel informationPanel;
    private JPanel listPanel;
    private JList contactList;
    private final JTextField jTextFieldName = new JTextField(20);
    private final JTextField jTextFieldMobile = new JTextField(20);
    private JButton jButtonAdd = new JButton("Add");
    private JButton jButtonClear = new JButton("Clear");

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(
                    "javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(
                    ContactsDatabase.class.getName()).log(Level.SEVERE,
                    null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(
                    ContactsDatabase.class.getName()).log(Level.SEVERE,
                    null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(
                    ContactsDatabase.class.getName()).log(Level.SEVERE,
                    null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(
                    ContactsDatabase.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
        ContactsDatabase mDIFrame = new ContactsDatabase();
    }

    public ContactsDatabase() {
        initGUI();
    }

    private void initGUI() {
        Box verticalBoxRight, verticalBoxLeft;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container contentPane = getContentPane();
        contentPane.setLayout(new GridLayout(1, 2));
        verticalBoxLeft = Box.createVerticalBox();
        verticalBoxLeft.add(Box.createRigidArea(new Dimension(70, 20)));
        verticalBoxLeft.add(new JLabel("Name"));
        verticalBoxLeft.add(jTextFieldName);
        verticalBoxLeft.add(Box.createVerticalStrut(10));
        verticalBoxLeft.add(new JLabel("Mobile #"));
        verticalBoxLeft.add(jTextFieldMobile);
        verticalBoxLeft.add(Box.createVerticalStrut(25));
        verticalBoxLeft.add(jButtonAdd);
        jButtonAdd.addActionListener(this);
        informationPanel = new JPanel();
        informationPanel.add(verticalBoxLeft);
        informationPanel.setBorder(
                BorderFactory.createTitledBorder("Information"));
        contactList = new JList();
        contactList.setModel(new DefaultListModel());
        verticalBoxRight = Box.createVerticalBox();
        verticalBoxRight.add(new JScrollPane(contactList));
        verticalBoxRight.add(Box.createRigidArea(new Dimension(80, 10)));
        verticalBoxRight.add(jButtonClear);
        jButtonClear.addActionListener(this);
        listPanel = new JPanel();
        listPanel.setBorder(BorderFactory.createTitledBorder("Contacts"));
        listPanel.add(verticalBoxRight);
        contentPane.add(informationPanel);
        contentPane.add(listPanel);
        setSize(600, 250);
        setResizable(false);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == jButtonAdd) {
            DefaultListModel contactsModel =
                    (DefaultListModel) contactList.getModel();
            contactsModel.addElement(jTextFieldName.getText()
                    + " " + jTextFieldMobile.getText());
            jTextFieldName.setText("");
            jTextFieldMobile.setText("");
        } else {
            contactList.setModel(new DefaultListModel());
        }
    }
}