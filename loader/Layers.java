package loader;

import java.util.ArrayList;
import java.util.List;

public class Layers {
	
	private List<Integer> gid = new ArrayList<Integer>();
	
	private String name;
	private int width ;
	private int height;
	
	public Layers(String name,int width,int height){
		this.name = name;
		this.width = width;
		this.height = height;
	}
	
	public int getGidCount(){
		return gid.size();
	}
	
	public void addGid(int value){
		gid.add(value);
	}
	
	public int getGid(int index){
		return gid.get(index);
	}

	public String getName() {
		return name;
	}	

	public int getWidth() {
		return width;
	}	

	public int getHeight() {
		return height;
	}

}
