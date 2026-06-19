package loader;

import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.image.BufferedImage;

@SuppressWarnings("serial")
public class Item extends Polygon{
	private String type;
	private String name;
	BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_4BYTE_ABGR);
	
	private int x;
	private int y;
	
	private int block = 0;
	private int attack = 0;
	private int heal = 0;
	private int scale;
	
	public Item(String name,int scale,int[] xPoints, int[] yPoints, int nPoints){		
		this.xpoints = xPoints;
		this.ypoints = yPoints;
		this.npoints = nPoints;
		this.scale = scale;
		this.name = name;
		
		int minX = Integer.MAX_VALUE;
		for(int i = 0; i<nPoints;i++){
			if(xpoints[i]<minX){
				minX = xpoints[i];
			}
		}
		this.x = minX;
		
		int minY = Integer.MAX_VALUE;
		for(int i = 0; i<nPoints;i++){
			if(ypoints[i]<minY){
				minY = ypoints[i];
			}
		}
		
		this.y = minY;
	}
	
	public void setImage(BufferedImage img){
		this.img = img;
	}
	
	public BufferedImage getImage(){
		return this.img;
	}
	
	public void draw(Graphics g){
		g.drawImage(img, x,y,8*scale,8*scale, null);
	}
	
	public String getType(){
		return this.type;
	}
	
	public void setType(String type){
		this.type = type;
	}
	
	public void setBlock(int amount){
		this.block = amount;
	}
	
	public void setHeal(int amount){
		this.heal = amount;		
	}
	
	public int getHeal(){
		return this.heal;
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
