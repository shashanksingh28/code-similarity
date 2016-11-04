
package fall15;
// Assignment #: Arizona State University CSE205 #7
//         Name:
//    StudentID: 000-00-0000
//      Lecture:
//      Section:
//  Description: The whole panel creates components for the whole panel
//  of this applet. It also contains CanvasPanel, ButtonListener, ColorListener,
//  and PointListner classes.

import java.awt.*;

import javax.swing.*;

import java.awt.event.*;
import java.text.Format.Field;
import java.util.ArrayList;

public class WholePanel extends JPanel
{
   private Color currentColor;
   private CanvasPanel canvas;
   private JPanel primary, buttonPanel, leftPanel;
   private JButton erase, undo;
   private ArrayList rectList;
   private JRadioButton[] colorRButtons;
   private Color[] colors;
   private int x1, y1, x2, y2, x3, y3;
   private boolean mouseDragged = false;
   private JRadioButton blackB, redB, blueB, greenB, orangeB;
  
   public WholePanel()
   {
	  //initialize ArrayList
	   rectList = new ArrayList();
	   
	  //default color to draw rectangles is black
	   currentColor = Color.black;
	   
      //create buttons	  
	   erase = new JButton("Erase");
	   undo = new JButton("Undo");
	   erase.addActionListener(new ButtonListener());
	   undo.addActionListener(new ButtonListener());
	   
      //create radio buttons for 5 colors
      //at the bginning, black will be chosen by default
	   blackB = new JRadioButton("black", true);
	   redB = new JRadioButton("red");
	   blueB = new JRadioButton("blue");
	   greenB = new JRadioButton("green");
	   orangeB = new JRadioButton("orange");

      //store 5 colors in an array
	   colorRButtons = new JRadioButton[5];
	   colorRButtons[0] = blackB;
	   colorRButtons[1] = redB;
	   colorRButtons[2] = blueB;
	   colorRButtons[3] = greenB;
	   colorRButtons[4] = orangeB;
	   colors = new Color[5];
	   colors[0] = Color.BLACK;
	   colors[1] = Color.RED;
	   colors[2] =  Color.blue;
	   colors[3] = Color.green;
	   colors[4] = Color.ORANGE;
			   
	   
      //Create a group of radio buttons so that at any time only one can be selected,
      ButtonGroup group = new ButtonGroup();
	  for (int i=0; i<colorRButtons.length; i++)
	    group.add(colorRButtons[i]);

      //add ColorListener to each of the radio buttons
	  blackB.addActionListener(new ColorListener());
	  this.redB.addActionListener(new ColorListener());
	  blueB.addActionListener(new ColorListener());
	  greenB.addActionListener(new ColorListener());
	  orangeB.addActionListener(new ColorListener());
	  

      //primary panel contains all radiobuttons, add the radio buttons to the panel
      primary = new JPanel(new GridLayout(5,1));
      primary.add(blackB);
      primary.add(redB);
      primary.add(blueB);
      primary.add(greenB);
      primary.add(orangeB);


      //canvas panel is where rectangles will be drawn, thus
      //it will listen to a mouse event.
      canvas = new CanvasPanel();
      canvas.setBackground(Color.white);

      //add MouseListener & MouseMotionListener to canvas
      canvas.addMouseListener(new PointListener());
      canvas.addMouseMotionListener(new PointListener());

      leftPanel = new JPanel(new GridLayout(2,1));
      buttonPanel = new JPanel(new GridLayout(2,1));
      buttonPanel.add(undo);
      buttonPanel.add(erase);
      leftPanel.add(primary);
      leftPanel.add(buttonPanel);
      JSplitPane sp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel,canvas);

	  setLayout(new BorderLayout());
      add(sp);
    }

   //ButtonListener defined actions to take in case "Create",
   //"Undo", or "Erase" is chosed.
   private class ButtonListener implements ActionListener
    {
      public void actionPerformed (ActionEvent event)
      {
    	  Object source = event.getSource();

			if (source == undo) // in case "undo" button is clicked
			{
				if (rectList.size() > 0) { // check whether there are any recs
					rectList.remove(rectList.size() - 1);
					canvas.repaint();
				}
			}else{
				rectList = new ArrayList<Rect>();
			}

    	  

      }
   } // end of ButtonListener

   // listener class to set the color chosen by a user using
   // the radio buttons.
    class ColorListener implements ActionListener
    {
		public void actionPerformed(ActionEvent event)
		 {
			JRadioButton tempButton = (JRadioButton)event.getSource();

			

				if (tempButton.isSelected()) {
					
					String colorText = tempButton.getText();					
					//System.out.println(colorText+ " is selected");
					
					for (int i = 0; i < colorRButtons.length; i++) {
						if(colorText.equalsIgnoreCase(colorRButtons[i].getText())){
							currentColor = colors[i];
							//System.out.println(i);
							break;
						}
						
					}	
					
				}					
		
			
			
	     }
    }


 //CanvasPanel is the panel where rectangles will be drawn
 private class CanvasPanel extends JPanel
  {

	  
     //this method draws all rectangles specified by a user
	 public void paintComponent(Graphics page)
      {
   	   super.paintComponent(page);


          //Only draw an outline of the rectangle when the mouse is draaged but not released
          //fill the whole rectangel with the correct color when mouse is released.

   	    if (mouseDragged == true)
           {
   	    	
   	    	 page.drawRect(x1, y1, x2, y2);
        	 mouseDragged = false;
        	 canvas.repaint();
	       }else{
	    	   if(rectList.size()>0){
	        		
	        		for(Object o : rectList){
	        			//System.out.println(currentColor);
	        			page.setColor( ((Rect)o).getColor());
	        			((Rect)o).draw(page);
	            		canvas.repaint();
	        		}
	        		
	        	}
	       }
   	    
   	    
   	    
	  }
    } //end of CanvasPanel class

 
 	
 
   // listener class that listens to the mouse
    class PointListener implements MouseListener, MouseMotionListener
    {
    	 Point firstPoint = null;
		 //in case that a user presses using a mouse,
		 //record the point where it was pressed.
	     public void mousePressed (MouseEvent event)
	      {

			 firstPoint = event.getPoint();
	    	 x1 = firstPoint.x;
	    	 y1 = firstPoint.y;
	      }
	
	     //mouseReleased method takes the point where a mouse is released,
	     //using the point and the pressed point to create a rectangle,
	     //add it to the ArrayList "rectList", and call paintComponent method.
	     public void mouseReleased (MouseEvent event)
	      {
	
	    	 Point newPoint = event.getPoint();
	    	 x2 = newPoint.x;
	    	 y2 = newPoint.y;
	    	 
	    	 Color c = currentColor;
	    	 rectList.add(new Rect(firstPoint.x, firstPoint.y, Math.abs(newPoint.x - firstPoint.x), Math.abs(newPoint.y-firstPoint.y), c));
	  
	    	
	    	 canvas.repaint();
		  }
	
	     //mouseDragged method takes the point where a mouse is dragged
	     //and call paintComponent method
		 public void mouseDragged(MouseEvent event)
		  {
			 mouseDragged = true;
			 Point p3 = event.getPoint();
			 x3 = p3.x;
			 y3 = p3.y;
			 canvas.paintComponent(canvas.getGraphics());
			 canvas.repaint();
			
			
		  }
	
	    // public void mouseClicked (MouseEvent event) {}
	     public void mouseEntered (MouseEvent event) {}
	     public void mouseExited (MouseEvent event) {}
	     public void mouseMoved(MouseEvent event) {}
	    

    } // end of PointListener


    
} // end of Whole Panel Class