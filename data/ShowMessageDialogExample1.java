import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class ShowMessageDialogExample1
{
  public static void main(String[] args)
  {
    String backupDir = "/Users/al/backups";
    
    // create a jframe
    JFrame frame = new JFrame("JOptionPane showMessageDialog example");
    
    // show a joptionpane dialog using showMessageDialog
    JOptionPane.showMessageDialog(frame,
        "Problem writing to backup directory: '" + backupDir + "'.");

    getNumInputs();
    System.exit(0);
    
  }
  
  // LoopsWhile ; Strings BooleanExpressions ; Strings ; LoopsWhile ; BooleanExpressions
  public static void getNumInputs(){
        String input = "";
        String msg="Enter some number (Q for exit)";
        while(!input.equalsIgnoreCase("Q"))
        {
            input = JOptionPane.showInputDialog(msg);

            if(!input.equalsIgnoreCase("Q"))
            { 
                double aNumber = Double.parseDouble(input);
                System.out.println(aNumber);
            }
        }
    }
}
