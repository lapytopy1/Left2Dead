package loader;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class TileSet {
	private int firstgid;
	//public final int lastgid;
	private  String name;
	private  int tileWidth;
	private  String source;
	private  int tileHeight;
	private  int imageWidth;
	private  int imageHeight;
	private BufferedImage img;
	//public final int tileAmountWidth;	
	public int getFirstgid() {
		return firstgid;
	}
	public void setFirstgid(int firstgid) {
		this.firstgid = firstgid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
		String path = String.format("data/Images/%s.png", name);
		try {
			img = ImageIO.read(new File(path));
		} catch (IOException e) {			
			e.printStackTrace();
		}
	}
	public BufferedImage getTileImage(){
		return this.img;
	}
	public int getTileWidth() {
		return tileWidth;
	}
	public void setTileWidth(int tileWidth) {
		this.tileWidth = tileWidth;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public int getImageWidth() {
		return imageWidth;
	}
	public void setImageWidth(int imageWidth) {
		this.imageWidth = imageWidth;
	}
	public int getTileHeight() {
		return tileHeight;
	}
	public void setTileHeight(int tileHeight) {
		this.tileHeight = tileHeight;
	}
	public int getImageHeight() {
		return imageHeight;
	}
	public void setImageHeight(int imageHeight) {
		this.imageHeight = imageHeight;
	}
	
	public int getTileAmountWidth(){
		return imageWidth/tileWidth;
	}
	
	public int getTileAmountHeight(){
		return imageHeight/tileHeight;
	}
	
	public int getLastgid(){
		return getTileAmountWidth()*getTileAmountHeight()+firstgid-1;
	}
}
