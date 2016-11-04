

public class Clothing extends Product{
	
	private String size;
	private String color;
	
	public Clothing(String id, int quantity, double unitPrice, String size, String color){
		
		super(id,quantity,unitPrice);
		this.size = size;
		this.color = color;
		
	}
	
	public void computeTotalCost( ){
		
		super.totalCost = unitPrice * quantity;
		
	}

	public String toString(){
		return "\nClothing:\n"+
				super.toString()+
				"Size:\t\t\t"+size+"\n"+
				"Color:\t\t\t"+color+"\n";
	}
	
}
