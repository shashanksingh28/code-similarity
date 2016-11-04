import java.util.Scanner;


public class Assignment2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		int[] numbers = new int[100];
		int totalNum = 0;
		
		while(totalNum<numbers.length){
			
			Scanner console = new Scanner(System.in);
			numbers[totalNum] = console.nextInt();
			
			if (numbers[totalNum] != 0){				
				totalNum++;			
			}else{
				System.out.print("The maximum number is "+findMax(numbers,totalNum)+"\n"+
						"The sum of the positive numbers is "+computePositiveSum(numbers,totalNum)+"\n"+
						"The total number of negative numbers is "+ countNegative(numbers,totalNum));
				System.exit(0);
			}

			
		}
		/*
		for(int i=0;i<numbers.length;i++){
			Scanner console = new Scanner(System.in);
			numbers[i] = console.nextInt();
			
			if (numbers[i] != 0){				
				totalNum++;			
			}else{
				System.out.print("The maximum number is "+findMax(numbers,totalNum)+"\n"+
						"The sum of the positive numbers is "+computePositiveSum(numbers,totalNum)+"\n"+
						"The total number of negative numbers is "+ countNegative(numbers,totalNum));
				System.exit(0);
			}
			
		}
		*/
		
	}
	
    public static int findMax(int[] numbers, int i){
    	int max = 0;
    	for(int n: numbers){
    		if(n>max)
    			max = n;
    	}
    	return max;
    }

    public static int computePositiveSum(int[] numbers, int i){
    	int pos=0;
    	for(int n: numbers){
    		if( n>0)
    			pos+=n;
    	}
    	return pos;
    }

    public static int countNegative(int[] numbers, int i){
    	int count=0;
    	
    	for(int n: numbers){

    		if (n<0){
    			count++;
    		}
    	}
    	
    	return count;
    }

}
