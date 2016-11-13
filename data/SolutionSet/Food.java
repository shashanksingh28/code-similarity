import java.text.DecimalFormat;
import java.text.NumberFormat;



public class Food extends Product{
	
	private String name;
	private double damageRate;
	private String expirationDate;
	
	
	public Food(String id, int quantity, double unitPrice, String name, double damageRate, String expirationDate){
		
		super(id, quantity,unitPrice);
		
		this.name = name;
		this.damageRate = damageRate;
		this.expirationDate = expirationDate;
		
		
	}
	
	public void computeTotalCost( ){
		
		super.totalCost = (super.unitPrice * super.quantity) * (1 + this.damageRate);
		
	}
	
	public String toString( ){
		
		DecimalFormat fmt = new DecimalFormat("0.00"); 
		NumberFormat percentFormat = NumberFormat.getPercentInstance(); 
		return "\nFood:\n"+
				super.toString()+
				"Food Name:\t\t"+name+"\n"+
				"Damage Rate:\t\t"+   fmt.format( this.damageRate*100)  +"%\n"+
				"Expiration date:\t"+expirationDate+"\n";
		
	}

}
