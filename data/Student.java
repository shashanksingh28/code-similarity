import java.text.NumberFormat;


public abstract class Student {

	protected String firstName;
	protected String lastName;
	protected String studentID;
	protected int creditNum;
	protected double rate;
	protected double tuition;
	
	public Student(String firstname, String lastname, String id, int credit, double rate){
		firstName = firstname;
		lastName = lastname;
		studentID = id;
		creditNum = credit;
		this.rate = rate;
		tuition = 0;		
	}
	
	public int getCreditNum(){
		return creditNum;
	}
	
	public abstract void computeTuition();
	
	public String toString(){
		
		NumberFormat format = NumberFormat.getCurrencyInstance();
		
		return "\nFirst name:\t\t"+firstName+"\n"+ 
				"Last name:\t\t"+lastName+"\n"+
				"Student ID:\t\t"+studentID+"\n"+
				"Credits:\t\t"+creditNum+"\n"+
				"Rate:\t\t\t"+format.format(rate)+"\n"+
				"Tuition:\t\t"+"+format.format(tuition)+"+"\n";
	
	}
	
	
		
	
}
