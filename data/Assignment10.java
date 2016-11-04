package fall2015;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Assignment10 {
	public static void main(String[] args) {
		char input1;
		String inputInfo = new String();
		int operation2;
		String line = new String();

		// create a linked list to be used in this method.
		LinkedList list1 = new LinkedList();

		try {
			// print out the menu
			printMenu();

			// create a BufferedReader object to read input from a keyboard
			InputStreamReader isr = new InputStreamReader(System.in);
			BufferedReader stdin = new BufferedReader(isr);

			do {
				System.out.print("What action would you like to perform?\n");
				line = stdin.readLine().trim(); // read a line
				input1 = line.charAt(0);
				input1 = Character.toUpperCase(input1);

				if (line.length() == 1) // check if a user entered only one
										// character
				{
					switch (input1) {
					case 'O': // List Current Size
						System.out.print("The current size is " + list1.size()
								+ "\n");
						break;

					case 'A': // Add String
						System.out.print("Please enter a string to add:\n");
						String str1 = stdin.readLine().trim();
						System.out.print("Please enter an index to add:\n");
						inputInfo = stdin.readLine().trim();
						int addIndex = Integer.parseInt(inputInfo);
						list1.insertElement(addIndex, str1);
						System.out.print(str1 + " is inserted at index "
								+ addIndex + "\n");
						break;

					case 'I': // Search for the Index of a String
						System.out.print("Please enter a string to search:\n");
						inputInfo = stdin.readLine().trim();
						operation2 = list1.searchElement(inputInfo);
						if (operation2 > -1)
							System.out.print(inputInfo + " found at index "
									+ operation2 + "\n");
						else
							System.out.print(inputInfo + " not found\n");
						break;

					case 'E': // Search for String at an Index
						System.out.print("Please enter an index to search:\n");
						inputInfo = stdin.readLine().trim();
						int searchIndex = Integer.parseInt(inputInfo);
						System.out
								.print("string at index " + searchIndex
										+ " is "
										+ list1.getElement(searchIndex) + "\n");
						break;

					case 'S': // Set a new element at specified index
						System.out.print("Please enter a new string to set:\n");
						String str2 = stdin.readLine().trim();
						System.out.print("Please enter an index to set:\n");
						inputInfo = stdin.readLine().trim();
						int setIndex = Integer.parseInt(inputInfo);
						list1.setElement(setIndex, str2);
						System.out.print(str2 + " is set at index " + setIndex
								+ "\n");
						break;

					case 'R': // Remove an element at a specified index
						System.out.print("Please enter an index to remove:\n");
						inputInfo = stdin.readLine().trim();
						int removeIndex = Integer.parseInt(inputInfo);
						list1.removeElement(removeIndex);
						System.out.print("string at index " + removeIndex
								+ " is removed\n");
						break;

					case 'C': // Count the number of occurences of a specific
								// element
						System.out.print("Please enter a string to count:\n");
						inputInfo = stdin.readLine().trim();
						int counter1 = list1.countHowMany(inputInfo);
						System.out
								.print("There are " + counter1 + " "
										+ inputInfo
										+ " found inside the linked list\n");
						break;

					case 'D': // Remove all occurences of a given element
						System.out.print("Please enter a string to remove:\n");
						inputInfo = stdin.readLine().trim();
						list1.removeDuplicate(inputInfo);
						System.out.print(inputInfo
								+ " is removed from the linked list\n");
						break;

					case 'P': // Append a given element a number of times at the
								// end of the linked list
						System.out
								.print("Please enter a string to append at the end:\n");
						String str3 = stdin.readLine().trim();
						System.out
								.print("Please enter the number of times you want to append:\n");
						inputInfo = stdin.readLine().trim();
						int times = Integer.parseInt(inputInfo);
						list1.appendAtEnd(str3, times);
						System.out.print(str3 + " is appended " + times
								+ " times at end of the linked list\n");
						break;

					case 'T': // Append a given element after the first
								// occurence of another element
						System.out
								.print("Which string do you want to append after:\n");
						String str4 = stdin.readLine().trim();
						System.out
								.print("Please enter the string you want to append:\n");
						inputInfo = stdin.readLine().trim();
						list1.appendAfter(str4, inputInfo);
						System.out.print(inputInfo + " is appended after "
								+ str4 + "\n");
						break;

					case 'V': // Reverse the first few element of the linked
								// list
						System.out
								.print("Please enter the number of elements you want to reverse:\n");
						inputInfo = stdin.readLine().trim();
						int reverseNum = Integer.parseInt(inputInfo);
						list1.reverseFirstFew(reverseNum);
						System.out.print("The first " + reverseNum
								+ " elements are reversed\n");
						break;

					case 'L': // List all strings inside the linked list
						System.out.print(list1.toString());
						break;

					case 'Q': // Quit
						break;

					case '?': // Display Menu
						printMenu();
						break;
					default:
						System.out.print("Unknown action\n");
						break;
					}
				} else {
					System.out.print("Unknown action\n");
				}
			} while (input1 != 'Q' || line.length() != 1);
		} catch (IOException exception) {
			System.out.print("IO Exception\n");
		}
	}

	/** The method printMenu displays the menu to a user **/
	public static void printMenu() {
		System.out.print("Choice\t\tAction\n" + "------\t\t------\n"
				+ "O\t\tList Current Size\n" + "I\t\tSearch Element\n"
				+ "E\t\tGet Element by Index\n" + "S\t\tSet Element by Index\n"
				+ "A\t\tInsert Element by Index\n"
				+ "R\t\tRemove Element by Index\n" + "C\t\tCount How Many\n"
				+ "D\t\tRemove Duplicates\n" + "P\t\tAppend at the End\n"
				+ "T\t\tAppend After\n" + "V\t\tReverse First Few\n"
				+ "L\t\tList Linked List\n" + "Q\t\tQuit\n"
				+ "?\t\tDisplay Help\n\n");

	} // end of printMenu()
}
