package fall2015;

import java.io.IOException;
import java.util.Scanner;

public class Assignment9
{
	/******************************************************************************
	 ***Complete the main() method. See above program description for details.
	 ******************************************************************************/
	public static void main (String[] args) throws IOException
	{

		Scanner input = new Scanner(System.in);
		int temp[] = new int[100];
		int i = 0;
		int number;


		do {
			number = input.nextInt();
			temp[i] = number;
			i++;
		} while (number!=0);

		
		System.out.println("The minimum number is " 
				+ findMin(temp, 0, i));
		
		System.out.println("The total number of odd integers is " 
				+ countOdd(temp, 0, i));
		System.out.println("The total number of -1 is " 
				+ countNegativeOne(temp, i - 1));
		
		System.out.println("The sum of numbers at even indexes is " 
				+ computeSumAtEvenIndexes(temp, i - 1));
	}

	/*************************************************************************************
	 ***(1)Complete the method. The method finds the minimum number in the partial array
	 ***range from startIndex to endIndex
	 *************************************************************************************/
	public static int findMin(int[ ] numbers, int startIndex, int endIndex)
	{

		 if (startIndex == endIndex) return numbers[startIndex];
		 else
		  {
			 int previousMin = findMin(numbers, startIndex, endIndex-1);
			
		     if (previousMin < numbers[endIndex]){
		    	 return previousMin;
		     }		        
		     else{
		        return numbers[endIndex]; 
		     }
		   }
		 
		 
	}

	/**************************************************************************************
	 ***(2)Complete the method. The method counts the number of odd integers in the partial
	 ***array range from startIndex to endIndex
	 *************************************************************************************/
	public static int countOdd(int[ ] numbers, int startIndex, int endIndex)
	{
		int num = 0;
		
		if(startIndex <= endIndex)
		{
			
			num += countOdd(numbers, startIndex + 1, endIndex);

			
			if(numbers[startIndex] % 2 != 0)
			{
				num++;
			}
		}
		return num;   
	}

	/*************************************************************************************
	 ***(3)Complete the method. The method counts the number of -1 inside an array with
	 ***   "count" numbers, index ranges from 0 to count-1
	 *************************************************************************************/
	public static int countNegativeOne(int[ ] numbers, int count)
	{
		int num = 0;

		if (count >= 0)
		{
			
			num += countNegativeOne(numbers, count-1);
			

			if (numbers[count] == -1)
				num++;
		}
		return num;
	}

	/**************************************************************************************
	 ***(4)Complete the method. The method computes the sum of numbers at index 0, 2, 4, ...
	 ***  inside a partial array with "count" numbers inside, index ranges from 0 to count-1
	 ***************************************************************************************/
	public static int computeSumAtEvenIndexes(int[ ] numbers, int count)
	{
		int num = 0;
		//execute the recursion process if we are still in the array limits
		if (count >= 0)
		{
			//add 0 to num
			num += computeSumAtEvenIndexes(numbers, count-1);

			//add array number to num if it's an even integer
			if (count % 2 == 0)
			{
				num += numbers[count];
			}
		}
		return num;
	}

}// end of class Assignment9
