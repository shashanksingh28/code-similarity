
import javax.swing.*;
import javax.swing.plaf.metal.MetalBorders;
import java.awt.*;

public class CalcInterface extends javax.swing.JFrame {

    private JLabel jLabelOutput = new javax.swing.JLabel(
            "123456789", SwingConstants.RIGHT);
    private JButton jButtonMC = new javax.swing.JButton("MC");
    private JButton jButtonMPlus = new javax.swing.JButton("M+");
    private JButton jButtonMMinus = new javax.swing.JButton("M-");
    private JButton jButtonMR = new javax.swing.JButton("MR");
    private JButton jButtonC = new javax.swing.JButton("C");
    private JButton jButtonAddSub = new javax.swing.JButton("+/-");
    private JButton jButtondiv = new javax.swing.JButton("/");
    private JButton jButtonMul = new javax.swing.JButton("*");
    private JButton jButtonSeven = new javax.swing.JButton("7");
    private JButton jButtonEight = new javax.swing.JButton("8");
    private JButton jButtonNine = new javax.swing.JButton("9");
    private JButton jButtonSub = new javax.swing.JButton("-");
    private JButton jButtonFour = new javax.swing.JButton("4");
    private JButton jButtonFive = new javax.swing.JButton("5");
    private JButton jButtonSix = new javax.swing.JButton("6");
    private JButton jButtonAdd = new javax.swing.JButton("+");
    private JButton jButtonOne = new javax.swing.JButton("1");
    private JButton jButtonTwo = new javax.swing.JButton("2");
    private JButton jButtonThree = new javax.swing.JButton("3");
    private JButton jButtonZero = new javax.swing.JButton("0");
    private JButton jButtonDot = new javax.swing.JButton(".");
    private JButton jButtonEqual = new javax.swing.JButton("=");

    public CalcInterface() {
        super.setTitle("Calc");
        initGUI();
    }

    private void initGUI() {
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.ipady = 15;
        constraints.ipadx = 10;
        constraints.fill = java.awt.GridBagConstraints.BOTH;
        getContentPane().setLayout(new GridBagLayout());
        jLabelOutput.setFont(new Font("Monospaced", 1, 24));
        jLabelOutput.setBorder(new MetalBorders.TextFieldBorder());
        constraints.insets = new Insets(10, 10, 10, 10);
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        getContentPane().add(jLabelOutput, constraints);
        constraints.insets = new Insets(0, 0, 0, 0);
        constraints.gridwidth = 1;
        constraints.gridx = 0;
        constraints.gridy = 1;
        getContentPane().add(jButtonMC, constraints);
        constraints.gridx = 1;
        constraints.gridy = 1;
        getContentPane().add(jButtonMPlus, constraints);
        constraints.gridx = 2;
        constraints.gridy = 1;
        getContentPane().add(jButtonMMinus, constraints);
        constraints.gridx = 3;
        constraints.gridy = 1;
        getContentPane().add(jButtonMR, constraints);
        constraints.gridx = 0;
        constraints.gridy = 2;
        getContentPane().add(jButtonC, constraints);
        constraints.gridx = 1;
        constraints.gridy = 2;
        getContentPane().add(jButtonAddSub, constraints);
        constraints.gridx = 2;
        constraints.gridy = 2;
        getContentPane().add(jButtondiv, constraints);
        constraints.gridx = 3;
        constraints.gridy = 2;
        getContentPane().add(jButtonMul, constraints);
        constraints.gridx = 0;
        constraints.gridy = 3;
        getContentPane().add(jButtonSeven, constraints);
        constraints.gridx = 1;
        constraints.gridy = 3;
        getContentPane().add(jButtonEight, constraints);
        constraints.gridx = 2;
        constraints.gridy = 3;
        getContentPane().add(jButtonNine, constraints);
        constraints.gridx = 3;
        constraints.gridy = 3;
        getContentPane().add(jButtonSub, constraints);
        constraints.gridx = 0;
        constraints.gridy = 4;
        getContentPane().add(jButtonFour, constraints);
        constraints.gridx = 1;
        constraints.gridy = 4;
        getContentPane().add(jButtonFive, constraints);
        constraints.gridx = 2;
        constraints.gridy = 4;
        getContentPane().add(jButtonSix, constraints);
        constraints.gridx = 3;
        constraints.gridy = 4;
        getContentPane().add(jButtonAdd, constraints);
        constraints.gridx = 0;
        constraints.gridy = 5;
        getContentPane().add(jButtonOne, constraints);
        constraints.gridx = 1;
        constraints.gridy = 5;
        getContentPane().add(jButtonTwo, constraints);
        constraints.gridx = 2;
        constraints.gridy = 5;
        getContentPane().add(jButtonThree, constraints);
        constraints.gridx = 0;
        constraints.gridy = 6;
        constraints.gridwidth = 2;
        getContentPane().add(jButtonZero, constraints);
        constraints.gridwidth = 1;
        constraints.gridx = 2;
        constraints.gridy = 6;
        getContentPane().add(jButtonDot, constraints);
        constraints.gridx = 3;
        constraints.gridy = 5;
        constraints.gridheight = 2;
        getContentPane().add(jButtonEqual, constraints);
        pack();
    }

    public static void main(String args[]) {
        new CalcInterface().setVisible(true);
    }
}