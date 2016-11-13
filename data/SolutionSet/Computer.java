
public class Computer {

	private String brandName;
	private CPU cpu;
	private int memory;
	private double price;
	
	public Computer(){
		//all strings to "?", all integers to 0, and all double to 0.0 and instantiates a CPU object. .
		brandName = "?";
		cpu = new CPU();
		memory = 0;
		price = 0;
		
	}
	
	public String getBrandName(){
		  return brandName;
    }
	
	public CPU getCPU(){
		return cpu;
	}
   
	public int getMemory(){
		return memory;
	}
	
	public double getPrice(){
		return price;
   }
	
	
	public void setBrandName(String BrandName){
		brandName = BrandName;
	}
	
	public void setCPU(String cpuType, int cpuSpeed){
		cpu.setType(cpuType);
		cpu.setSpeed(cpuSpeed);
	}
	
	public void setMemory(int memoryAmount){
		  memory = memoryAmount;
	  }
	public void setPrice(double price){
		this.price = price;
	}
	
	public String toString(){
		return "\nBrandName:\t"+getBrandName()+"\n"+
				"CPU:\t\t"+cpu.toString()+"\n"+
				"Memory:\t\t"+getMemory()+"\n"+
				"Price:\t\t"+getPrice()+"\n\n";
	}
	
}
