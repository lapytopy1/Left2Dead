package loader;

import java.awt.Polygon;

@SuppressWarnings("serial")
public class EnterEvent extends Polygon{
	
	private String type;
	private String name;
	
	private int block = 0;
	private int attack = 0;
	
	public EnterEvent(String name,String type,int[] x, int[] y, int nPoints){
		this.xpoints = x;
		this.ypoints = y;
		this.npoints = nPoints;
		this.type = type;
		this.name = name;
	}
	
	public String getType(){
		return this.type;
	}
	
	public void setBlock(int amount){
		this.block = amount;
	}
	
	public void setAttack(int amount){
		this.attack = amount;
	}
	
	public int getAttack(){
		return this.attack;
	}
	
	public int getBlock(){
		return this.block;
	}
	
	public String getName(){
		return this.name;
	}
	

}
