// Assignment #: Arizona State University CSE205 #6
//         Name: Your Name
//    StudentID: Your ID
//      Lecture: Your lecture time (e.g., MWF 10:30am)
//  Description: CreatePanel generates a panel where a user can enter
//  a car information and create a list of available cars. 
package fall2015;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import java.util.*;

public class CreatePanel extends JPanel
{
   private Vector carList;
   private PurchasePanel buyPanel;
   private JButton createButton;
   private JLabel message;
   
   //******declare all other necessary java components here ******//   
   private JTextField manuField;
   private JTextField brandField;
   private JTextField yearField;
   private JTextField priceField;
   private JTextArea display;
      
   private JPanel myPanel;
   
 //Constructor
   public CreatePanel(Vector carList, PurchasePanel buyPanel)
   {
      this.carList = carList;
      this.buyPanel = buyPanel;
      
      message = new JLabel(" ");
      message.setForeground(Color.red);
   
      //******Initialize all instance varibles and set up  the layouts ******//     
         
      this.buyPanel = new PurchasePanel(this.carList);
      
      
      JLabel manuLabel = new JLabel("Manufacturer");
      JLabel brandLabel = new JLabel("Brand Name");
      JLabel yearLabel = new JLabel("Year");
      JLabel priceLabel = new JLabel("Price");
      manuField = new JTextField(20);
      brandField = new JTextField(20);
      yearField = new JTextField(20);
      priceField = new JTextField(20);
      display = new JTextArea(20,30);
      
      myPanel = new JPanel();   
      myPanel.setLayout(new BorderLayout());
      
      JPanel inputPanel = new JPanel();
      inputPanel.setLayout(new BorderLayout());
      JPanel parameterPane = new JPanel();
      parameterPane.setLayout(new GridLayout(4,1));
      
      JPanel outputPanel = new JPanel();
      
      myPanel.add(inputPanel,BorderLayout.WEST);
      myPanel.add(outputPanel,BorderLayout.EAST);
      
      
      inputPanel.add(message,BorderLayout.NORTH);
      inputPanel.add(parameterPane,BorderLayout.CENTER);
      
      parameterPane.add(manuLabel);
      parameterPane.add(manuField);
      
      parameterPane.add(brandLabel);
      parameterPane.add(brandField);
      
      parameterPane.add(yearLabel);
      parameterPane.add(yearField);
      
      parameterPane.add(priceLabel);
      parameterPane.add(priceField);
          
      
      //******Never forget to register the listener object with its source object, such as the following example ******//
      createButton = new JButton("Create a car");
      createButton.addActionListener(new ButtonListener());
      
      
      inputPanel.add(createButton, BorderLayout.SOUTH);
      outputPanel.add(display);
      add(myPanel);
   
   } //end of constructor


  //ButtonListener is a listener class that listens to
  //see if the buttont "Create a car" is pushed.
  //When the event occurs, it get a car's manufacturer, brand, year and price
  //information from the relevant text fields, create a new car and add it inside
  //the carList. Meanwhile it will display the car's information inside the text area. 
  //It also does error checking in case any of the textfields are empty.
  
   private class ButtonListener implements ActionListener
   {
      public void actionPerformed(ActionEvent event)
      {
         //******declare all necessary local varibles here ******//
         String manu;
         String brand;
         String year;
         String price;
         Car newCar;
         message.setForeground(Color.RED);
         
         //******In case any of the four textfields are empty, but the button is pushed ******//
         if (manuField.getText().equalsIgnoreCase("") )
         {
            message.setText("Please fill all fields");
            
         }
         else
         {
            try
            {
             //******handle the button event here ******//  
            	manu = manuField.getText();
            	brand = brandField.getText();
            	year = yearField.getText();
            	price = priceField.getText();
            	
            	

            	if(carList.size()==0){

	                newCar = new Car(manu,brand,Integer.parseInt(year), Double.parseDouble(price));
	                carList.add(newCar);
	                //udpate
 	                //buyPanel.updateCarList(); 	                
	                //display.append(newCar.toString());
	                updateOutput();
	                message.setText("Car added");                	
            	}else{
            		boolean cont = false;
                	for(Object o: carList){
                		
                		Car c = (Car)o;
                		if(c.getBrandName().equalsIgnoreCase(brand) &&
                				c.getManufacturer().equalsIgnoreCase(manu) &&
                				c.getYear() == Integer.parseInt(year) ){
                    		
                    		message.setText("Car not added - duplicate");
                        	cont = true;
                    	}
                		
                	}
                	if(!cont){

                    		
                    		newCar = new Car(manu,brand,Integer.parseInt(year), Double.parseDouble(price));
         	                carList.add(newCar);
         	                
         	                //udpate
         	                //buyPanel.updateCarList();         	                
         	                //display.append(newCar.toString());
         	                updateOutput() ;
         	                
         	                message.setText("Car added");       
                    		
                  
                	}
	
            	}
            	
            	
            	
            }
            catch(NumberFormatException ex)
            {
               message.setText("Please enter correct data format");
            
            }
         }
      
      } //end of actionPerformed method
   } //end of ButtonListener class

   
	private void updateOutput() {
		buyPanel.updateCarList();
		final StringBuilder op = new StringBuilder();
		for(Object o : carList){
			op.append(((Car)o).toString());
			//carList.addElement(o);
		}
		
		display.setText(op.toString());
	}
} //end of CreatePanel class