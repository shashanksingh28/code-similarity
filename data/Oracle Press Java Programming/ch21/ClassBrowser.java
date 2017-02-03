
import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.*;
import javax.swing.*;

public class ClassBrowser extends JFrame implements ActionListener {

    private JButton buttonAccept = new JButton("Accept");
    private JButton buttonInvoke = new JButton("Invoke");
    private JLabel returnType = new JLabel();
    private JLabel returnValue = new JLabel();
    private DefaultListModel constructors = new DefaultListModel();
    private DefaultListModel methods = new DefaultListModel();
    private JList listConstructors = new JList(constructors);
    private JList listMethods = new JList(methods);
    private JScrollPane jScrollPane1 = new JScrollPane();
    private JScrollPane jScrollPane2 = new JScrollPane();
    private JTextField textClassName =
            new JTextField("IntrospectionTestApplication");
    private String strClassName;
    private Class theClass;
    private Object obj;
    private String argumentValue;

    public ClassBrowser() {
        initComponents();
    }

    public void setArgumentValue(String argumentValue) {
        this.argumentValue = argumentValue;
    }

    private void initComponents() {
        setTitle("Class Browser");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setLayout(new java.awt.GridBagLayout());
        GridBagConstraints gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 7;
        gridBagConstraints.insets = new java.awt.Insets(11, 23, 0, 0);
        getContentPane().add(new JLabel(
                "Enter name of class file to be loaded & press accept"),
                gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 9;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.insets = new java.awt.Insets(18, 18, 0, 20);
        buttonAccept.addActionListener(this);
        getContentPane().add(buttonAccept, gridBagConstraints);
        jScrollPane1.setViewportView(listConstructors);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 16;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 257;
        gridBagConstraints.ipady = 71;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(6, 10, 0, 20);
        getContentPane().add(jScrollPane1, gridBagConstraints);
        jScrollPane2.setViewportView(listMethods);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 16;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 257;
        gridBagConstraints.ipady = 81;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(6, 10, 0, 20);
        getContentPane().add(jScrollPane2, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 8;
        gridBagConstraints.insets = new java.awt.Insets(18, 17, 0, 0);
        buttonInvoke.addActionListener(this);
        getContentPane().add(buttonInvoke, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.insets = new java.awt.Insets(11, 10, 0, 0);
        getContentPane().add(new JLabel("Methods"), gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = new java.awt.Insets(1, 10, 0, 0);
        getContentPane().add(new JLabel("Constructors"), gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 6;
        gridBagConstraints.ipadx = 183;
        gridBagConstraints.insets = new java.awt.Insets(19, 10, 0, 0);
        getContentPane().add(textClassName, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.insets = new java.awt.Insets(29, 10, 0, 0);
        getContentPane().add(new JLabel("Return Type"), gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.insets = new java.awt.Insets(6, 10, 11, 0);
        getContentPane().add(new JLabel("Return Value"), gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.ipadx = 48;
        gridBagConstraints.insets = new java.awt.Insets(29, 16, 0, 0);
        getContentPane().add(returnType, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.insets = new java.awt.Insets(6, 18, 11, 0);
        getContentPane().add(returnValue, gridBagConstraints);
        pack();
    }

    public static void main(String args[]) {
        try {
// UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
            ClassBrowser app = new ClassBrowser();
            app.setBounds(220, 30, 470, 600);
            app.setVisible(true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent aevt) {
        Object source = aevt.getSource();
        if (source == buttonAccept) {
            strClassName = textClassName.getText();
            if (strClassName.equals("")) {
                return;
            }
            constructors.clear();
            methods.clear();
            returnValue.setText("");
            returnType.setText("");
            try {
                theClass = Class.forName(strClassName);
            } catch (Throwable exp) {
                constructors.addElement(
                        "You have entered the class name incorrectly");
                constructors.addElement("Please input a new class name");
                textClassName.requestFocus();
                return;
            }
            try {
                obj = theClass.newInstance();
            } catch (Exception e) {
                constructors.addElement(e.toString());
            }
            displayMethods();
        } else if (source == buttonInvoke) {
            invokeSelectedMethod();
        }
    }

    private void displayMethods() {
        Method methodList[] = theClass.getDeclaredMethods();
        Constructor constructorList[] = theClass.getDeclaredConstructors();
        for (int count = 0; count < constructorList.length; count++) {
            constructors.addElement(constructorList[count].toString());
        }
        for (int count = 0; count < methodList.length; count++) {
            methods.addElement(methodList[count].toString());
        }
        listMethods.requestFocus();
    }

    private void invokeSelectedMethod() {
        returnType.setText("");
        returnValue.setText("");
        int index = listMethods.getSelectedIndex();
        Method classMethods[] = theClass.getDeclaredMethods();
        Class inputParameters[] = classMethods[index].getParameterTypes();
        Object[] params = new Object[inputParameters.length];
        for (int i = 0; i < inputParameters.length; i++) {
            (new InputFrame(this, inputParameters[i].getName(),
                    true)).setVisible(true);
            if (inputParameters[i].isAssignableFrom(
                    java.lang.Short.TYPE)) {
                params[i] = new Short(argumentValue);
            } else if (inputParameters[i].isAssignableFrom(
                    java.lang.Boolean.TYPE)) {
                params[i] = Boolean.valueOf(argumentValue);
            } else if (inputParameters[i].isAssignableFrom(
                    java.lang.Character.TYPE)) {
                params[i] = new Character(argumentValue.charAt(0));
            } else if (inputParameters[i].isAssignableFrom(
                    java.lang.Byte.TYPE)) {
                params[i] = new Byte(argumentValue);
            } else if (inputParameters[i].isAssignableFrom(
                    java.lang.Integer.TYPE)) {
                params[i] = new Integer(argumentValue);
            } else if (inputParameters[i].isAssignableFrom(
                    java.lang.Long.TYPE)) {
                params[i] = new Long(argumentValue);
            } else if (inputParameters[i].isAssignableFrom(
                    java.lang.Float.TYPE)) {
                params[i] = new Float(argumentValue);
            } else if (inputParameters[i].isAssignableFrom(
                    java.lang.Double.TYPE)) {
                params[i] = new Double(argumentValue);
            } else {
                params[i] = argumentValue;
            }
        }
        try {
            Object returnObject = classMethods[index].invoke(obj, params);
            returnValue.setText(returnObject.toString());
            returnType.setText(returnObject.getClass().getName());
        } catch (java.lang.IllegalAccessException iae) {
            System.out.println("Invalid operation");
        } catch (Exception e) {
            returnValue.setText(e.toString());
        }
    }
}

class InputFrame extends JDialog implements ActionListener {

    private ClassBrowser app;
    private JTextField inputText = new JTextField(15);
    private JButton buttonOK = new JButton("OK");

    InputFrame(ClassBrowser app, String name, boolean model) {
        super(app, model);
        setTitle(name);
        this.app = app;
        init();
    }

    private void init() {
        setBounds(50, 50, 200, 100);
        setLayout(new FlowLayout());
        add(inputText);
        add(buttonOK);
        buttonOK.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        if (evt.getSource() == buttonOK) {
            app.setArgumentValue(inputText.getText());
            dispose();
        }
    }
}