package loader;

import java.awt.Polygon;
import java.util.ArrayList;

import Animation.Demon;

@SuppressWarnings("serial")
public class Trigers extends Polygon{

	private String name;
	private String type;
	
	private ArrayList<Demon> spawns = new ArrayList<Demon>();
	
	
	public Trigers(String name,String type, int[] x,int[]y){
		this.xpoints = x;
		this.ypoints = y;
		this.name = name;
		this.type = type;
		this.npoints = xpoints.length;
	}
	
	public boolean isTriger(String num){
		return type.equals(num);
	}
	
	public void addDemon(Demon d){
		spawns.add(d);
	}
	
	public String getName(){
		return this.name;
	}
	
	public ArrayList<Demon> getDemons(){
		return this.spawns;
	}
	
}
