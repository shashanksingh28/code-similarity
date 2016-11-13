package fall2015;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

public class PurchasePanel extends JPanel {
	// carList is used to create the "Available car(s)" list - cList
	// selectedList is used to create the "Purchased car(s)" list - buyList
	private Vector<Car> carList, selectedList;
	private JList cList, buyList; //

	// ******declare all other necessary instance varibles here ******//

	private JLabel available;
	private JLabel selected;

	private JLabel lblSelectedCount;
	private JLabel lblTotal;

	private JButton add;
	private JButton remove;

	private int numSelected;
	private double totalPrice;

	// Constructor. Initialize each instance varibles by order and set up the
	// layouts
	public PurchasePanel(Vector<Car> carList) {
		numSelected = 0;
		totalPrice = 0.0d;

		this.carList = carList;
		selectedList = new Vector();
	
		cList = new JList(carList);
		buyList = new JList(selectedList);
		cList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		buyList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		available = new JLabel("Available car(s)");
		selected = new JLabel("Selected car(s)");

		lblSelectedCount = new JLabel(numSelected + " car(s) selected");
		lblTotal = new JLabel("Total amount: "
				+ NumberFormat.getCurrencyInstance().format(totalPrice));

		add = new JButton("Pick a Car");
		add.addActionListener(new ButtonListener());
		remove = new JButton("Remove a Car");
		remove.addActionListener(new ButtonListener());

		// ******Never forget to register the listener object with its source
		// object ******//
		GridBagConstraints c = new GridBagConstraints();
		setLayout(new GridBagLayout());

		c.anchor = GridBagConstraints.FIRST_LINE_START;

		c.gridx = 0;
		c.gridy = 0;
		add(available, c);

		c.gridx = 0;
		c.gridy = 3;
		add(selected, c);

		c.gridx = 0;
		c.gridy = 5;
		add(lblSelectedCount, c);

		c.gridx = 1;
		c.gridy = 5;
		add(lblTotal, c);

		c.weightx = 1.d;
		c.weighty = 1.d;
		c.fill = GridBagConstraints.BOTH;

		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 2;
		add(new JScrollPane(cList), c);

		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 1;
		add(add, c);

		c.gridx = 1;
		c.gridy = 2;
		c.gridwidth = 1;
		add(remove, c);

		c.gridx = 0;
		c.gridy = 4;
		c.gridwidth = 2;
		add(new JScrollPane(buyList), c);
	}// end of constructor
		// This method refresh the JList "cList" with pdated vector carList
		// information

	public void updateCarList() {
		cList.setListData(carList);

		buyList.setListData(selectedList);
	}

	// ButtonListener class listens to see if any of "Pick a car" or
	// "Remove a car"
	// button is pushed. When "Pick a car" button is pushed, a selected car
	// name in the "Available car(s)" list on top will be added to the
	// "Selected car(s)" list at the bottom
	// provided that the car was not added before.
	// When "Remov a car" button is pushed, a selected car in the
	// "Selected car(s)" list
	// will be removed.
	private class ButtonListener implements ActionListener {


		public void actionPerformed(ActionEvent event) {
			String whichButton = "";
			
			if(event.getSource()!=null){				
				
				whichButton = ((JButton)event.getSource()).getText();
				
				System.out.println(whichButton+" button is pushed!");
			}
			
			
			if (whichButton.equalsIgnoreCase("Pick a Car")) {
				
				if (cList.getSelectedValue() != null) {
					numSelected++;
					totalPrice += ((Car)cList.getSelectedValue()).getPrice();

					selectedList.add((Car)cList.getSelectedValue());
					carList.remove(cList.getSelectedValue());
				}
			} else {
				if (buyList.getSelectedValue() != null) {
					numSelected--;
					totalPrice -= ((Car)buyList.getSelectedValue()).getPrice();

					carList.add((Car)buyList.getSelectedValue());
					selectedList.remove(buyList.getSelectedValue());
				}
			}

			lblSelectedCount.setText(numSelected + " car(s) selected");
			lblTotal.setText("Total amount: "
					+ NumberFormat.getCurrencyInstance().format(totalPrice));

			updateCarList();
		}
	} // end of ButtonListener class

} // end of PurchasePanel class
