// Assignment #: Arizona State University CSE205 #6
//         Name: Your Name
//    StudentID: Your ID
//      Lecture: Your lecture time (e.g., MWF 10:30am)
//  Description: The Assignment 6 class creates a Tabbed Pane with
//               two tabs, one for Car creation and one for
//               Car purchasing. and adds it as its Applet content
//               and also sets its size.
package fall2015;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.util.*;

public class Assignment6 extends JApplet
 {

   private int APPLET_WIDTH = 500, APPLET_HEIGHT = 300;

   private JTabbedPane tPane;
   private CreatePanel createPanel;
   private PurchasePanel purchasePanel;
   private Vector carList;

   //The method init initializes the Applet with a Pane with two tabs
   public void init()
    {
	 //list of car to be used in both CreatePanel & PurchasePanel
	 carList = new Vector();

	 //register panel uses the list of courses
	 purchasePanel = new PurchasePanel(carList);

     createPanel = new CreatePanel(carList, purchasePanel);

     //create a tabbed pane with two tabs
     tPane = new JTabbedPane();
     tPane.addTab("Car creation", createPanel);
     tPane.addTab("Car purchase", purchasePanel);

     getContentPane().add(tPane);
     
     
     
     ChangeListener changeListener = new ChangeListener() {
         public void stateChanged(ChangeEvent changeEvent) {
           JTabbedPane sourceTabbedPane = (JTabbedPane) changeEvent.getSource();
           int index = sourceTabbedPane.getSelectedIndex();
           if(index==1){
        	   purchasePanel.updateCarList();
           }       
         }
       };
       tPane.addChangeListener(changeListener);

     
     setSize (APPLET_WIDTH, APPLET_HEIGHT); //set Applet size
    }
}

