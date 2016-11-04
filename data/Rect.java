package fall15;

import java.awt.Color;
import java.awt.Graphics;

public class Rect {

	private int x,y,width,height;
	private Color color;
	
	public Rect(int x1, int y1, int width, int height, Color color){
		x = x1;
		y = y1;
		this.width = width;
		this.height = height;
		this.color = color;
		
	}
	
	public void draw (Graphics page){
		page.fillRect(x, y, width, height);
	}


	public Color getColor(){
		return color;
	}
	
}
