package loader;

public class Points {
	private int[] x;
	private int[] y;
	
	public Points(int length){
		this.x = new int[length];
		this.y = new int[length];
	}
	public void addX(int index,int value){
		this.x[index] = value; 
	}
	
	public void addY(int index,int value){
		this.y[index] = value; 
	}
	
	public int[] getX(){
		return this.x;
	}
	
	public int[] getY(){
		return this.y;
	}	
	
}
