import java.text.NumberFormat;


public class FullTime extends Student{

	private boolean resident;
	private int creditUpperbound;
	private double studentProgramFee;
	
	public FullTime(String firstname, String lastname, String id,  int credits, double rate, boolean resident, double programFee){
		
		super(firstname,  lastname,  id,   credits,  rate);
		
		this.resident = resident;
		
		if (resident){
			creditUpperbound = 7;
		}else{
			creditUpperbound = 12; 
		}
		
		studentProgramFee = programFee;
	}
	
	@Override
	public void computeTuition() {
		
		this.tuition = rate * creditUpperbound + studentProgramFee;
		
	}
	
	public String toString(){
		
		NumberFormat format = NumberFormat.getCurrencyInstance();
		
		if (resident){
			return "\nOnCampus Student:"+
					"\nResident Status"+
					this.toString()+
					"Student Program Fee:\t"+format.format(studentProgramFee)+"\n"+
					"\n";
		}else{
			return "\nOnCampus Student:"+
					"\nNonResident Status"+
					this.toString()+
					"Student Program Fee:\t"+format.format(studentProgramFee)+"\n"+
					"\n";
		}
		
	}
	
}
