package loader;

import java.util.ArrayList;
import java.util.List;

public class ObjectGroup {
	private String name;
	private int width;
	private int height;
	
	private List<Object> objects = new ArrayList<Object>();
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public void addObj(Object obj){
		this.objects.add(obj);
	}
	public int getObjectSize(){
		return this.objects.size();
	}
	public Object getObject(int index){
		return this.objects.get(index);
	}
	
	
}
