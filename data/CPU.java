
public class CPU {

	private String type;
	private int speed;
	
	public CPU(){
		// type to "?" and speed to 0.
		type = "?";
		speed = 0;
	}
	
	public String getType(){
		return type;
	}
	
	public int getSpeed(){
		return speed;
	}
	
	public void setType(String t){
		type = t;
	}
	
	public void setSpeed(int s){
		speed=s;
	}
	
	public String toString(){
		return type+speed+"HZ";
	}
	
}
