interface Living{
    void move();
    void eat();
    void sleep();
}

abstract class Animal implements Living{
    private String name;
    
    public abstract void eat();
    
    public void move(){
        System.out.println("Animal moves");
    }
    
    public void sleep(){
        System.out.println("Animal Sleeps");
    }
}

class Mammal extends Animal {
    
    public void eat(){
        System.out.println("Mammals are some herbivores and some carnivores");
    }
    
    public void regulateTemperature(){}
}

public class Human extends Mammal{
    
    public void think(){}
    
    public void move(){
        System.out.println("Human who walks on two legs");
    }
    
    /* Polymorphism Examples
     */
    public static void main(String args[]){
	    // Example of Polymorphism
	    Living m = new Mammal();
	    m.eat();

	    // Example of Casting
	    Object obj = "John";
	    String str = (String) obj;
	    System.out.println("Hello " + str);

	    // Example of instanceOf
	    if(m instanceof Animal){
	    	System.out.println(m + " is an instance of Animal");
	    }
    }

}
