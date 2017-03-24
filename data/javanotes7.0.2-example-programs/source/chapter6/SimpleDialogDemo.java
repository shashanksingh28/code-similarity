
/**
 * This program demonstrates four easy-to-use routines for showing 
 * a dialog box and, in three cases, getting back some information 
 * from the user.  The methods are:
 *  
 *       JOptionPane.showMessageDialog
 *       JOptionPane.showConfirmDialog
 *       JOptionPane.showInputDialog
 *       JColorChooser.showDialog
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class SimpleDialogDemo extends JPanel implements ActionListener {

    /**
     * A main routine allows this class to be run as an application.
     */
    public static void main(String[] args) {
        JFrame window = new JFrame("Four Simple Dialogs");
        SimpleDialogDemo content = new SimpleDialogDemo();
        window.setContentPane(content);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLocation(250,200);
        window.pack();
        window.setResizable(false);
        window.setVisible(true);
    }

    //---------------------------------------------------------------------

   JLabel message;  // A label for giving some feedback to the user.
                    // It appears at the top of the panel.

   Color selectedColor = Color.GRAY; // This color will be used as the
                                     // initial color in the color chooser.
                                     // It is used to remember the user's
                                     // color choice, so that the color 
                                     // chooser can show the same color,
                                     // if it is opened twice.
                                   

   public SimpleDialogDemo() {
          // Set up the panel with a message label and four buttons.
          // Each button will open a different type of dialog.

       setBackground(Color.GRAY);
       setBackground(Color.GRAY);
       setLayout( new GridLayout(3,1,3,3) );
       message = new JLabel("Click a button to open a dialog", JLabel.CENTER);
       message.setForeground(new Color(180,0,0));
       message.setBackground(Color.WHITE);
       message.setOpaque(true);
       add(message);

       JPanel buttonBar;
       JButton button;

       buttonBar = new JPanel();
       buttonBar.setLayout(new GridLayout(1,2,3,3));
       buttonBar.setBackground(Color.GRAY);
       add(buttonBar);
       button = new JButton("Message Dialog");
       button.addActionListener(this);
       buttonBar.add(button);
       button = new JButton("Confirm Dialog");
       button.addActionListener(this);
       buttonBar.add(button);

       buttonBar = new JPanel();
       buttonBar.setLayout(new GridLayout(1,2,3,3));
       buttonBar.setBackground(Color.GRAY);
       add(buttonBar);
       button = new JButton("Input Dialog");
       button.addActionListener(this);
       buttonBar.add(button);
       button = new JButton("Color Chooser");
       button.addActionListener(this);
       buttonBar.add(button);
       
       setBorder(BorderFactory.createLineBorder(Color.GRAY,3));

   } // end constructor
   
 
   /**
    * Respond to a button click by showing a dialog and setting the 
    * message label to describe the user's response.
    */
   public void actionPerformed(ActionEvent evt) {

      String command = evt.getActionCommand();

      if (command.equals("Message Dialog")) {
         message.setText("Displaying message dialog.");
         JOptionPane.showMessageDialog(this,
             "This is an example of JOptionPane.showMessageDialog.");
         message.setText("You closed the message dialog.");
      }

      else if (command.equals("Confirm Dialog")) {
         message.setText("Displaying confirm dialog.");
         int response = JOptionPane.showConfirmDialog(this,
             "This is an example of JOptionPane.showConfirmDialog.\n" 
               + "Click any button to indicate your response.");
         switch(response) {
            case JOptionPane.YES_OPTION: 
               message.setText("You clicked \"Yes\".");
               break;
            case JOptionPane.NO_OPTION: 
               message.setText("You clicked \"No\".");
               break;
            case JOptionPane.CANCEL_OPTION: 
               message.setText("You clicked \"Cancel\".");
               break;
            case JOptionPane.CLOSED_OPTION: 
               message.setText("You closed the box without making a selection.");
         }
      }

      else if (command.equals("Input Dialog")) {
         message.setText("Displaying input dialog.");
         String response = JOptionPane.showInputDialog(this,
             "This is an example of JOptionPane.showInputDialog.\n" 
               + "Type your response, and click a button.");
         if (response == null)
            message.setText("You canceled the input.");
         else if (response.trim().length() == 0)
            message.setText("You left the input box empty.");
         else
            message.setText("You entered \"" + response + "\".");
      }

      else if (command.equals("Color Chooser")) {
         message.setText("Displaying color chooser dialog.");
         Color c = JColorChooser.showDialog(this,"Select a Color",selectedColor);
         if (c == null)
            message.setText("You canceled without selecting a color.");
         else {
            selectedColor = c;  // Remember selected color for next time.
            int r = c.getRed();
            int g = c.getGreen();
            int b = c.getBlue();
            message.setText("You selected RGB = (" + r + "," + g + "," + b + ").");
         }
      }

   } // end actionPerformed()


} // end class SimpleDialogDemo

