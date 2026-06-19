package loader;

import java.awt.Point;
import java.awt.Polygon;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import Animation.BossDemon;
import Animation.Demon;
import Animation.Hero;
import Animation.MedDemon;
import Animation.WeakDemon;

public class LoadGame {
	private ArrayList<Polygon> collisionPolys = new ArrayList<Polygon>();
	private ArrayList<EnterEvent> enterEventPolys = new ArrayList<EnterEvent>();
	private ArrayList<Item> pickupEventPolys = new ArrayList<Item>();
	
	private ArrayList<Demon> dSpawns = new ArrayList<Demon>();
	
	
	private ArrayList<BufferedImage> images = new ArrayList<BufferedImage>();
	
	private ArrayList<Trigers> trigers = new ArrayList<Trigers>();	

	private ArrayList<Point> spawns = new ArrayList<Point>();

	BufferedImage tiles ;
	BufferedImage flippedTiles;
	BufferedImage smallHouse ;
	BufferedImage medHouse ;
	private int scale = 4;

	/**
	 * This class creates a calls myHandeler class then constructs the game objects and map images from the
	 * data that myHandeler returns.
	 * @param dir
	 */
	public LoadGame(String dir) {
		try {
			tiles = ImageIO
					.read(new File("data/Images/Tiles.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		reader(dir);		
	}
	public ArrayList<Item> getItems(){
		return this.pickupEventPolys;
	}
	public ArrayList<Point> getSpawns(){
		return this.spawns;
	}
	public ArrayList<Polygon> getCollisions(){
		return this.collisionPolys;
	}
	public ArrayList<BufferedImage> getImages() {
		return this.images;
	}	
	
	public ArrayList<Demon> getDSpawns(){
		return this.dSpawns;
	} 
	
	public ArrayList<Trigers> getTrigers(){
		return this.trigers;
	}

	public void reader(String dir) {
		SAXParserFactory spf = SAXParserFactory.newInstance();
		try {
			SAXParser saxParser = spf.newSAXParser();
			MyHandler handler = new MyHandler();
			handler.setScale(scale);
			saxParser.parse(new File(dir), handler);
			List<TileSet> tileSets = handler.getTileSets();
			List<Layers> layers = handler.getLayers();
			List<ObjectGroup> groups = handler.getGroups();
			for (int i = 0; i < layers.size(); i++) {
				Layers layer = layers.get(i);
				BufferedImage img = new BufferedImage(layer.getWidth()
						* handler.getTileWidth(), layer.getHeight()
						* handler.getTileHeight(),
						BufferedImage.TYPE_4BYTE_ABGR);
				for (int j = 0; j < layer.getGidCount(); j++) {
					int gid = layer.getGid(j);
					for (int a = 0; a < tileSets.size(); a++) {
						TileSet tile = tileSets.get(a);
						if (gid <= tile.getLastgid()
								&& gid >= tile.getFirstgid()) {
							int tileAmountW = tile.getTileAmountWidth();
							int tileAmountH = tile.getTileAmountHeight();
							int height = layer.getHeight();
							int width = layer.getWidth();
							int tileW = tile.getTileWidth();
							int tileH = tile.getTileHeight();
							int xCount = 0;
							int yCount = 0;
							loop: for (int b = 0; b < tileAmountH; b++) {
								for (int c = 0; c < tileAmountW; c++) {
									if (tileAmountW * yCount
											+ xCount == gid
											- tile.getFirstgid()) {
										break loop;
									}
									xCount++;
								}
								yCount++;
								xCount = 0;
							}
							int imgXCount = 0;
							int imgYCount = 0;
							loop2: for (int b = 0; b < height; b++) {
								for (int c = 0; c < width; c++) {
									if ((height * imgYCount)
											+ imgXCount == j) {
										break loop2;
									}
									imgXCount++;
								}
								imgYCount++;
								imgXCount = 0;
							}
							int tileXCoords = xCount * tile.getTileWidth();
							int tileYCoords = yCount * tile.getTileWidth();
							int imgXCoords = imgXCount * tile.getTileWidth();
							int imgYCoords = imgYCount * tile.getTileHeight();

							for (int x = 0; x < tileW; x++) {
								for (int y = 0; y <tileH; y++) {
										int col = tile.getTileImage().getRGB(tileXCoords + x,
												tileYCoords + y);
										img.setRGB(imgXCoords + x, imgYCoords
												+ y, col);
								}
							}
						}
					}
				}

				images.add(img);
			}
			for (int i = 0; i < groups.size(); i++) {
				ObjectGroup group = groups.get(i);
				for (int j = 0; j < group.getObjectSize(); j++) {
					Object obj = group.getObject(j);
						createObject(group.getName(), obj);
					}
				}			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void createObject(String type,Object obj){
		int [] x = obj.getXPoints();
		int [] y = obj.getYPoints();
		if (type.equals("ISpawn")) {
			Item ev = new Item(obj.getName(),scale,x, y, x.length);
			if(obj.getName().equals("SpecialDrop")){
				if(obj.getType().equals("Armor")){
					ev.setBlock(15+(int)Math.random()*(20-15));
					ev.setImage(getImageFGid(tiles,80,8,8));
				}else if(obj.getType().equals("Weapon")){
					ev.setAttack(15+(int)Math.random()*(20-15));
					ev.setImage(getImageFGid(tiles,79,8,8));
				}
			}else if(obj.getName().equals("RandomI")){
				double random = Math.random()*1.0;

				if(random>=0 && random<=0.3333){					
					ev.setAttack(15+(int)Math.random()*(20-15));
					ev.setType("Weapon");
					ev.setImage(getImageFGid(tiles,31,8,8));
				}else if (random>0.3333 && random<=0.6666){
					ev.setHeal(5+ (int)(Math.random()*(10-5)));
					ev.setType("Potion");
					ev.setImage(getImageFGid(tiles,33,8,8));
				}else if (random>0.6666 && random<=1.0){
					ev.setBlock(15+(int)Math.random()*(20-15));
					ev.setType("Armour");
					ev.setImage(getImageFGid(tiles,32,8,8));
				}

			}

			pickupEventPolys.add(ev);
		} else if (type.equals("EEvent")) {
			EnterEvent ev = new EnterEvent(obj.getName(),obj.getType(),x, y, x.length);
			enterEventPolys.add(ev);
		} else if(type.equals("DSpawn")){
			String dif = obj.getName();
			int minX = Integer.MAX_VALUE;
			int minY = Integer.MAX_VALUE;
			for(int a = 0; a<x.length;a++){
				if(x[a] < minX){
					minX = x[a];
				}
			}
			for(int a = 0; a<y.length;a++){
				if(y[a] < minY){
					minY = y[a];
				}
			}
		
			if(dif.equals("Easy")){
				dSpawns.add(createDemon("E",new WeakDemon(minX,minY)));				
			}else if (dif.equals("Hard")){
				dSpawns.add(createDemon("H",new BossDemon(minX,minY)));				
			}else if (dif.equals("Med")){
				dSpawns.add(createDemon("M",new MedDemon(minX,minY)));				
			}
		}else if(type.equals("Trigers")){
			Trigers t = new Trigers(obj.getName(),obj.getType(),x,y);
			trigers.add(t);
			
		}else if (type.equals("TSpawn")){
			int minX = Integer.MAX_VALUE;
			int minY = Integer.MAX_VALUE;
			for(int a = 0; a<x.length;a++){
				if(x[a] < minX){
					minX = x[a];
				}
			}
			for(int a = 0; a<y.length;a++){
				if(y[a] < minY){
					minY = y[a];
				}
			}
			for(int i = 0; i < trigers.size();i++){
				if(trigers.get(i).isTriger(obj.getType())){
					if(obj.getName().equals("Easy")){
						trigers.get(i).addDemon(createDemon("E",new WeakDemon(minX,minY)));
					}else if(obj.getName().equals("Med")){
						trigers.get(i).addDemon(createDemon("M",new MedDemon(minX,minY)));
					}else if(obj.getName().equals("Hard")){
						trigers.get(i).addDemon(createDemon("H",new BossDemon(minX,minY)));
					}
				}
			}
		}else if(type.equals("PSpawn")){
			int minX = Integer.MAX_VALUE;
			int minY = Integer.MAX_VALUE;
			for(int a = 0; a<x.length;a++){
				if(x[a] < minX){
					minX = x[a];
				}
			}
			for(int a = 0; a<y.length;a++){
				if(y[a] < minY){
					minY = y[a];
				}
			}
			
			Point p = new Point(0,0);
			if(obj.getName().equals("Player1")){
				p.x = minX;
				p.y = minY;				
				
			}else if(obj.getName().equals("Player2")){
				p.x = minX;
				p.y = minY;				
				
			}else if(obj.getName().equals("Player3")){
				p.x = minX;
				p.y = minY;				
				
			}else if(obj.getName().equals("Player4")){
				p.x = minX;
				p.y = minY;				
			}
			
			spawns.add(p);
			

		}else {
			Polygon pol = new Polygon(x, y, x.length);
			collisionPolys.add(pol);
		}
		
	}
	public static Hero createCharacter(String name,int uid, int x, int y){
		BufferedImage ch = new BufferedImage(9,8,BufferedImage.TYPE_4BYTE_ABGR);
		BufferedImage chH = new BufferedImage(9,8,BufferedImage.TYPE_4BYTE_ABGR);
		BufferedImage chA = new BufferedImage(9,8,BufferedImage.TYPE_4BYTE_ABGR);
		BufferedImage chAH = new BufferedImage(9,8,BufferedImage.TYPE_4BYTE_ABGR);
					
		String pl = String.format("data/Character/Players/Player%d/Player%d.png", uid,uid);
		String plA = String.format("data/Character/Players/Player%d/Player%dA.png", uid,uid);
		String plAH = String.format("data/Character/Players/Player%d/Player%dAH.png", uid,uid);
		String plH = String.format("data/Character/Players/Player%d/Player%dH.png", uid,uid);
		try {
			ch = ImageIO.read(new File(pl));
			chH = ImageIO.read(new File(plH));
			chA = ImageIO.read(new File(plA));
			chAH = ImageIO.read(new File(plAH));
		} catch (IOException e) {
			e.printStackTrace();
		}
		Hero c = new Hero(name, uid,x,y);
		//System.out
		
		
		//Normal sprite
		c.createUp1(getImageFGid(ch,0,9,8));
		c.createUp2(getImageFGid(ch,1,9,8));
		c.createUp3(getImageFGid(ch,2,9,8));
		c.createRight1(getImageFGid(ch,3,9,8));
		c.createRight2(getImageFGid(ch,4,9,8));
		c.createRight3(getImageFGid(ch,5,9,8));
		c.createLeft1(getImageFGid(ch,6,9,8));
		c.createLeft2(getImageFGid(ch,7,9,8));
		c.createLeft3(getImageFGid(ch,8,9,8));
		c.createDown1(getImageFGid(ch,9,9,8));
		c.createDown2(getImageFGid(ch,10,9,8));
		c.createDown3(getImageFGid(ch,11,9,8));

		//Attack and hit sprite
		c.createUp1AHit(getImageFGid(chAH,0,9,8));
		c.createUp2AHit(getImageFGid(chAH,1,9,8));
		c.createUp3AHit(getImageFGid(chAH,2,9,8));
		c.createRight1AHit(getImageFGid(chAH,3,9,8));
		c.createRight2AHit(getImageFGid(chAH,4,9,8));
		c.createRight3AHit(getImageFGid(chAH,5,9,8));
		c.createLeft1AHit(getImageFGid(chAH,6,9,8));
		c.createLeft2AHit(getImageFGid(chAH,7,9,8));
		c.createLeft3AHit(getImageFGid(chAH,8,9,8));
		c.createDown1AHit(getImageFGid(chAH,9,9,8));
		c.createDown2AHit(getImageFGid(chAH,10,9,8));
		c.createDown3AHit(getImageFGid(chAH,11,9,8));

		//Attack sprite
		c.createUp1Att(getImageFGid(chA,0,9,8));
		c.createUp2Att(getImageFGid(chA,1,9,8));
		c.createUp3Att(getImageFGid(chA,2,9,8));
		c.createRight1Att(getImageFGid(chA,3,9,8));
		c.createRight2Att(getImageFGid(chA,4,9,8));
		c.createRight3Att(getImageFGid(chA,5,9,8));
		c.createLeft1Att(getImageFGid(chA,6,9,8));
		c.createLeft2Att(getImageFGid(chA,7,9,8));
		c.createLeft3Att(getImageFGid(chA,8,9,8));
		c.createDown1Att(getImageFGid(chA,9,9,8));
		c.createDown2Att(getImageFGid(chA,10,9,8));
		c.createDown3Att(getImageFGid(chA,11,9,8));

		//Hit sprite
		c.createUp1Hit(getImageFGid(chH,0,9,8));
		c.createUp2Hit(getImageFGid(chH,1,9,8));
		c.createUp3Hit(getImageFGid(chH,2,9,8));
		c.createRight1Hit(getImageFGid(chH,3,9,8));
		c.createRight2Hit(getImageFGid(chH,4,9,8));
		c.createRight3Hit(getImageFGid(chH,5,9,8));
		c.createLeft1Hit(getImageFGid(chH,6,9,8));
		c.createLeft2Hit(getImageFGid(chH,7,9,8));
		c.createLeft3Hit(getImageFGid(chH,8,9,8));
		c.createDown1Hit(getImageFGid(chH,9,9,8));
		c.createDown2Hit(getImageFGid(chH,10,9,8));
		c.createDown3Hit(getImageFGid(chH,11,9,8));
		
		c.getImage("Down2");
		return c;
	}
	public static Demon createDemon(String type, Demon d){
		BufferedImage npc = new BufferedImage(8,8,BufferedImage.TYPE_4BYTE_ABGR);
		BufferedImage npcH = new BufferedImage(8,8,BufferedImage.TYPE_4BYTE_ABGR);
		BufferedImage npcA = new BufferedImage(8,8,BufferedImage.TYPE_4BYTE_ABGR);
		try {
			if(type.equals("E")){
				npc = ImageIO.read(new File("data/Character/demons/EasyD.png"));
				npcH = ImageIO.read(new File("data/Character/demons/EasyDH.png"));
				npcA = ImageIO.read(new File("data/Character/demons/EasyDA.png"));
			}else if(type.equals("M")){
				npc = ImageIO.read(new File("data/Character/demons/MedD.png"));
				npcH = ImageIO.read(new File("data/Character/demons/MedDH.png"));
				npcA = ImageIO.read(new File("data/Character/demons/MedDA.png"));
			}else if(type.equals("H")){
				npc = ImageIO.read(new File("data/Character/demons/HardD.png"));
				npcH = ImageIO.read(new File("data/Character/demons/HardDH.png"));
				npcA = ImageIO.read(new File("data/Character/demons/HardDA.png"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		d.createDown1(getImageFGid(npc,0,8,8));
		d.createDown2(getImageFGid(npc,1,8,8));
		d.createDown3(getImageFGid(npc,2,8,8));
		d.createRight1(getImageFGid(npc,3,8,8));
		d.createRight2(getImageFGid(npc,4,8,8));
		d.createRight3(getImageFGid(npc,5,8,8));
		d.createLeft1(getImageFGid(npc,6,8,8));
		d.createLeft2(getImageFGid(npc,7,8,8));
		d.createLeft3(getImageFGid(npc,8,8,8));
		d.createUp1(getImageFGid(npc,9,8,8));
		d.createUp2(getImageFGid(npc,10,8,8));
		d.createUp3(getImageFGid(npc,11,8,8));

		d.createDown1Att(getImageFGid(npcA,0,8,8));
		d.createDown2Att(getImageFGid(npcA,1,8,8));
		d.createDown3Att(getImageFGid(npcA,2,8,8));
		d.createRight1Att(getImageFGid(npcA,3,8,8));
		d.createRight2Att(getImageFGid(npcA,4,8,8));
		d.createRight3Att(getImageFGid(npcA,5,8,8));
		d.createLeft1Att(getImageFGid(npcA,6,8,8));
		d.createLeft2Att(getImageFGid(npcA,7,8,8));
		d.createLeft3Att(getImageFGid(npcA,8,8,8));
		d.createUp1Att(getImageFGid(npcA,9,8,8));
		d.createUp2Att(getImageFGid(npcA,10,8,8));
		d.createUp3Att(getImageFGid(npcA,11,8,8));
		
		d.createDown1Hit(getImageFGid(npcH,0,8,8));
		d.createDown2Hit(getImageFGid(npcH,1,8,8));
		d.createDown3Hit(getImageFGid(npcH,2,8,8));
		d.createRight1Hit(getImageFGid(npcH,3,8,8));
		d.createRight2Hit(getImageFGid(npcH,4,8,8));
		d.createRight3Hit(getImageFGid(npcH,5,8,8));
		d.createLeft1Hit(getImageFGid(npcH,6,8,8));
		d.createLeft2Hit(getImageFGid(npcH,7,8,8));
		d.createLeft3Hit(getImageFGid(npcH,8,8,8));
		d.createUp1Hit(getImageFGid(npcH,9,8,8));
		d.createUp2Hit(getImageFGid(npcH,10,8,8));
		d.createUp3Hit(getImageFGid(npcH,11,8,8));
		return d;
	}
	public static BufferedImage getImageFGid(BufferedImage npc,int gid,int w,int h){
		BufferedImage img = new BufferedImage(w,h,BufferedImage.TYPE_4BYTE_ABGR);
		int tileW = w;
		int tileH = h;
		int xCount = 0;
		int yCount = 0;
		int tileAmountW =npc.getWidth()/tileW ;
		int tileAmountH =npc.getHeight()/tileH;

		loop: for (int b = 0; b < tileAmountH; b++) {
			for (int c = 0; c <tileAmountW ; c++) {
				if (tileAmountW * yCount + xCount == gid	) {
					break loop;
				}
				xCount++;
			}
			yCount++;
			xCount = 0;
		}
		int tileXCoords = xCount * tileW;
		int tileYCoords = yCount * tileH;

		for (int x = 0; x < tileW; x++) {
			for (int y = 0; y <tileH; y++) {
				img.setRGB(x, y,npc.getRGB(tileXCoords+x, tileYCoords+y));
			}
		}
		return img;
	}
	
	//Just For Testing
	public static void main(String[] args) {

		new LoadGame("data/Maps/Map.tmx");

	}
}