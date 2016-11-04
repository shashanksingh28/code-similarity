
public class PartTime extends Student{

	private boolean campus;
	private double studentProgramFee;
	
	public PartTime(String firstname, String lastname, String id,  int credits, double rate, boolean campus){
		
		super(firstname,  lastname,  id,   credits,  rate);
		
		this.campus = campus;
		if (!campus){
			studentProgramFee = studentProgramFee * 0.2;
		}
		
	}
	
	@Override
	public void computeTuition() {
		
		this.tuition = rate * this.creditNum + studentProgramFee;
		
	}
	

	public String toString(){
		
		return "";
	}
	
}
