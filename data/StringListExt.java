package cse360assign2;

public class StringListExt extends StringList {
	
	/** Description of add(String ToBeAdded)
	 * 						Adds string to array list
	 * @param 				ToBeAdded Is a string that is being searched for within the arraylist 
	 */
	public void add(String ToBeAdded){
		mylist.add(counter,ToBeAdded );
		counter++;
		}
	
	/** Description of get(int GettingInt)
	 * @return				returns the string element within the index the user inputs
	 * @param 				GettingInt is the index of the string that the user wants to see 
	 */
	public String get(int GettingInt){
			if (mylist.isEmpty()==true){
				return "";
				}
			if(GettingInt > mylist.size()||GettingInt <0){		
				return "Index doesn't exist";
				}
		return mylist.get(GettingInt);	
		}
	
	/** Description of get(int GettingInt)
	 * @return			    the amount of elements in the string array
	 * @param 				ToBeAdded Is a string that is being searched for within the arraylist 
	 */
	public int length(){
			return mylist.size();
     	}
	
	/** Description of getLast()
	 *  @return				Returns the string in the last index of the string array
	 */
	public String getLast(){
			if (mylist.isEmpty()==true){
				return "";
				}
			return mylist.get(mylist.size()-1); // returning last element in the list
		}
		
	/** Description of remove(String gettingRemoved)
	 * 						This method deletes a certain string element from the string array
	 *  @param				The string that needs to be removed from the string array
	 */
	public void remove(String gettingRemoved){
		int i =0;
		boolean found=true;
		if(mylist.isEmpty()==true){
				found = false;
			}
		while(gettingRemoved != mylist.get(i)&& found== true){
			i++;
			if(i==mylist.size()-1){
			found = false;
			}
		 }
		if(gettingRemoved == mylist.get(i)){
			mylist.remove(i);
			counter--;
			}
		}
}
