package game;

import Animation.*;
import loader.Item;
import loader.LoadGame;
import loader.Trigers;
import java.util.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;

/**
 * The board class represents the left 2 dead game board
 *
 * @author timmyC
 */
public class Board {
	//width of the board
	private final int width;
	//height of the board
	private final int height;
	//number of players that the board should contain
	private int numPlayers;
	//current gamestate of the board
	public static final int WAITING = 0;
	public static final int READY = 1;
	public static final int PLAYING = 2;
	public static final int GAMEOVER = 3;
	public static final int GAMEWON = 4;

	private int state; // this is used to tell us what state we're in.
	//queue of hero portals
	private final Queue<GameCoordinate> heroPortals = new ArrayDeque<GameCoordinate>();
	//list of characters in the game
	private ArrayList<Characters> characters = new ArrayList<Characters>();
	//the map of board
	private ArrayList<BufferedImage> map = new ArrayList<BufferedImage>();
	//list of collision polygons
	private List<Polygon> colls = new ArrayList<Polygon>();
	//list of available hero spawns
	private List<Point> spawns = new ArrayList<Point>();
	//list of the available demon spawns
	private ArrayList<Demon> dSpawns = new ArrayList<Demon>();
	//items the player can collect
	private ArrayList<Item> items = new ArrayList<Item>();
	//triggers to activate events
	private ArrayList<Trigers> trigers = new ArrayList<Trigers>();
	//x translation
	private int xTrans = 80*4*-1;
	//y translation
	private int yTrans = 400*4*-1;
	//Buffered image used to convert xml to graphics
	private BufferedImage r;
	//can event to check if the player can pickup an item
	private static boolean canEvent = false;
	//scale the board size should be increased by
	private final int scale = 4;
	
	/**
	 * This method is used to create the gameboard
	 * it also creates an instance of loadGame and initializes
	 * the map, collisions, spawns, items and triggers using the
	 * loadGame.
	 * 
	 * @param width int - width of the board
	 * @param height int - height of the board
	 */
	public Board(int width, int height) {
		this.width = width;
		this.height = height;
		
		LoadGame lg = new LoadGame("data/Maps/Map.tmx");
		this.map = lg.getImages();
		this.colls = lg.getCollisions();
		this.spawns = lg.getSpawns();
		this.items = lg.getItems();
		this.dSpawns = lg.getDSpawns();
		this.trigers = lg.getTrigers();
		
		try {
			r = ImageIO.read(new File("data/Title/R.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Used to return the current in game items
	 * 
	 * @return ArrayList<Item> - returns a list of items in the game
	 */
	public ArrayList<Item> getItems() {
		return this.items;
	}

	/**
	 * Used to return the current triggers
	 * 
	 * @return ArrayList<Trigers> - returns a list of triggers in the game
	 */
	public ArrayList<Trigers> getTrigers() {
		return this.trigers;
	}

	/**
	 * Used to return map as an arrayList of bufferedImage
	 * 
	 * @return ArrayList<BufferedImage> - returns an arrayList of BufferedImages
	 */
	public ArrayList<BufferedImage> getMap() {
		return this.map;
	}

	/**
	 * Used to set whether or not a player can pickup an item
	 * 
	 * @param can
	 *            Boolean - set the can events value (true or false)
	 */
	public void setCanEvent(boolean can) {
		canEvent = can;
	}

	/**
	 * Used to assert whether or not a player can pickup an item
	 * 
	 * @return - returns true if the player can pickup and item false otherwise
	 */
	public boolean getCanEvent() {
		return this.canEvent;
	}
	
	/**
	 * This method is used to update the entire game on screen,
	 * it draws the players, items, health and map according to the 
	 * current gamestate.
	 * 
	 * @param gr Graphics - which graphics pane to drawn onto
	 * @param x int - the x location to centre on
	 * @param y int - the y location to centre on
	 */
	public void draw(Graphics gr, int x, int y){

		Graphics2D g = (Graphics2D) gr;
		g.setBackground(Color.BLACK);
		g.translate(x, y);

		//draw the player map
		for(BufferedImage l : map){
			g.drawImage(l, 0, 0, l.getWidth() * scale,l.getHeight() * scale, null);
		}

		//draw the items on the map
		for(Item i : items){
			i.draw(g);
		}
		//draw the collision polygons
		g.setColor(Color.RED);
		for(int i = 0; i<colls.size();i++){
			g.drawPolygon(colls.get(i));
		}

		//draw trigger polygons
		g.setColor(Color.BLUE);
		for(int i = 0; i<trigers.size();i++){
			g.drawPolygon(trigers.get(i));
		}


		//draw all characters and demons
		for(Characters c : getCharacters()){
			if(c instanceof Hero){
				((Hero) c).draw(g);
				//draw an R if the demon is over an item
				if(((Hero) c).isCanEvent()){
					g.drawImage(r,((Hero) c).getCoordinate().getX()+16,((Hero) c).getCoordinate().getY()-16,16,16,null);
				}
			}
			if (c instanceof Demon) {
				((Demon) c).draw(g);	

			}
		}
	}

	/**
	 * Get the board width.
	 *
	 * @return int - board width
	 */
	public int width() {
		return width;
	}

	/**
	 * Get the board height.
	 *
	 * @return int - board height
	 */
	public int height() {
		return height;
	}

	

	/**
	 * Register a new hero portal on the board. A hero portal is a place where
	 * hero will appear from.
	 */

	public synchronized void registerHeroPortal() {
		for (Point p : spawns){
			heroPortals.add(new GameCoordinate((int)p.getX(), (int)p.getY(), " "));
		}


	}
	
	/**
	 * The UID is a unique identifier for all characters in the game. This is
	 * required in order to synchronize the movements of different players
	 * across boards.
	 */
	private static int uid = 1;

	/**
	 * Register a new Hero into the game. The Hero will be placed onto the next
	 * available portal.
	 *
	 * @return
	 */
	public synchronized Hero registerHero() {
		System.out.println("Registering hero! Uid: "+uid);
		if (heroPortals.size()!=0){
			//System.out.println("x: "+)
			Hero h = LoadGame.createCharacter("", uid,heroPortals.peek().getX(),heroPortals.poll().getY());
			h.setUid(uid);
			characters.add(h);
			uid++;
			//NEED ANOTHER WAY TO ADD ITEMS TO HERO TODO
			System.out.println("Char Array size: "+characters.size());
			return h;

		}
		else{
			throw new RuntimeException("board 222");
		}
	}

	/**
	 * Register a new demon into the game. The demon will be placed into the
	 * next available demon portal.
	 *
	 */
	public void registerDemon() {
		for(int i = 0; i< dSpawns.size();i++){
			getCharacters().add(dSpawns.get(i));
		}
	}
	/**
	 * This method is used to remove character from the 
	 * local board
	 * 
	 * @param character Characters - character to remove
	 */

	public void removeCharacter(Characters character) {
		for (int i = 0; i != getCharacters().size(); ++i) {
			Characters p = getCharacters().get(i);
			if (character == p) {
				// NOTE: we can't call remove here, since this results in a
				// concurrent modification exception, as this method will be
				// called indirectly from the clockTick() method.
				//getCharacters().set(i, null);
				return;
			}
		}
	}

	/**
	 * This method is used to disconnect Players from a multiplayer
	 * game. 
	 * 
	 * @param uid int - uid of the player to disconnect
	 */
	public void disconnectPlayer(int uid) {
		for (int i = 0; i != getCharacters().size(); ++i) {
			Characters p = getCharacters().get(i);

			if (p instanceof Hero && ((Hero) p).getUid() == uid) {
				getCharacters().set(i, null); // TODO
				// ross you need to figure out how to create a DeadHero here
				// cant cast from character TIm
			}
		}
	}
	/**
	 * This method is used to find players on the
	 * board by uid. This method is used when any of 
	 * the players variables need to be updated.
	 * 
	 * @param uid int - the supposed uid of the player
	 * @return Hero - return the hero with the matching uid
	 */

	public synchronized Hero player(int uid) {
		System.out.println("ARGUMENT: " + uid);
		System.out.println(getCharacters().size());
		for (Characters p : getCharacters()) {
			if (p instanceof Hero){
				if(((Hero) p).getUid() == uid) {
					return (Hero) p;
				}
			}
		}
		//return null;
		throw new IllegalArgumentException("Invalid Character UID " + uid +"\n arraySize: "+characters.size());


	}

	/**
	 * Iterate the characters in the game
	 *
	 * @return
	 */
	public List<Characters> characters() {
		return getCharacters();
	}

	/**
	 * Get current board state.
	 *
	 * @return
	 */
	public int state() {
		return state;
	}

	/**
	 * Set the board state.
	 * 
	 * @param state
	 */
	public void setState(int state) {
		this.state = state;
	}

	/**
	 * Check if the current hero can move up
	 * 
	 * @param p
	 *            - hero input to check
	 * @return Boolean - true if can move up, false otherwise
	 */
	public boolean canMoveUp(Characters p) {
		int x0 = p.getCoordinate().getX() + 15;
		int y0 = p.getCoordinate().getY() + 15;
		int x1 = x0 + 2;
		int y1 = y0;

		for (Polygon x : colls) {
			if (x.contains(x0, y0 - 16) || x.contains(x1, y1 - 16)) {
				return false;
			}
		}
		yTrans += 16;
		return true;
	}

	/**
	 * Check if the current hero can move down
	 * 
	 * @param p
	 *            - hero input to check
	 * @return Boolean - true if can move down, false otherwise
	 */
	public boolean canMoveDown(Characters p) {
		int x0 = p.getCoordinate().getX()+15;
		int y0 = p.getCoordinate().getY()+17;
		int x1 = x0+2;
		int y1 = y0;

		for (Polygon x : colls) {
			if (x.contains(x0,y0+16)||x.contains(x1,y1+16)) {
				return false;

			}
		}
		yTrans -= 16;
		return true;
	}
	
	/**
	 * Check if the current hero can move left
	 * 
	 * @param p - hero input to check
	 * @return Boolean - true if can move left, false otherwise
	 */
	public boolean canMoveLeft(Characters p) {

		int x0 = p.getCoordinate().getX()+15;
		int y0 = p.getCoordinate().getY()+15;
		int x1 = x0;
		int y1 = y0+2;

		for (Polygon x : colls) {
			if (x.contains(x0-16,y0)||x.contains(x1-16,y1)) {
				return false;

			}
		}
		xTrans += 16;
		return true;
	}
	
	/**
	 * Check if the current hero can move right
	 * 
	 * @param p - hero input to check
	 * @return Boolean - true if can move right, false otherwise
	 */
	public boolean canMoveRight(Characters p) {

		int x0 = p.getCoordinate().getX()+17;
		int y0 = p.getCoordinate().getY()+15;
		int x1 = x0;
		int y1 = y0+2;

		for (Polygon x : colls) {
			if (x.contains(x0+16,y0)||x.contains(x1+16,y1)) {
				return false;
			}
		}
		//never updates the clients view of the board only the MASTERS
		xTrans -= 16;
		return true;
	}
	/**
	 * Check if the current demon can move up
	 * 
	 * @param p - demon input to check
	 * @return Boolean - true if can move up, false otherwise
	 */

	public boolean canMoveUpDemon(Characters p) {
		int x0 = p.getCoordinate().getX()+15;
		int y0 = p.getCoordinate().getY()+15;
		int x1 = x0+2;
		int y1 = y0;

		for (Polygon x : colls) {
			if (x.contains(x0,y0-16) || x.contains(x1,y1-16)) {
				return false;
			}
		}
		//yTrans += 16;
		return true;
	}
	
	/**
	 * Check if the current demon can move down
	 * 
	 * @param p - demon input to check
	 * @return Boolean - true if can move down, false otherwise
	 */
	public boolean canMoveDownDemon(Characters p) {
		int x0 = p.getCoordinate().getX()+15;
		int y0 = p.getCoordinate().getY()+17;
		int x1 = x0+2;
		int y1 = y0;

		for (Polygon x : colls) {
			if (x.contains(x0,y0+16)||x.contains(x1,y1+16)) {
				return false;

			}
		}
	//	yTrans -= 16;
		return true;
	}
	
	/**
	 * Check if the current demon can move left
	 * 
	 * @param p - demon input to check
	 * @return Boolean - true if can move left, false otherwise
	 */
	public boolean canMoveLeftDemon(Characters p) {

		int x0 = p.getCoordinate().getX()+15;
		int y0 = p.getCoordinate().getY()+15;
		int x1 = x0;
		int y1 = y0+2;

		for (Polygon x : colls) {
			if (x.contains(x0-16,y0)||x.contains(x1-16,y1)) {
				return false;

			}
		}
	//	xTrans += 16;
		return true;
	}
	
	/**
	 * Check if the current demon can move right
	 * 
	 * @param p - demon input to check
	 * @return Boolean - true if can move right, false otherwise
	 */
	public boolean canMoveRightDemon(Characters p) {
		int x0 = p.getCoordinate().getX()+17;
		int y0 = p.getCoordinate().getY()+15;
		int x1 = x0;
		int y1 = y0+2;

		for (Polygon x : colls) {
			if (x.contains(x0+16,y0)||x.contains(x1+16,y1)) {
				return false;
			}
		}
		//never updates the clients view of the board only the MASTERS
	//	xTrans -= 16;

		return true;
	}

	/**
	 * The clock tick is essentially a clock trigger, which allows the board to
	 * update the current state. The frequency with which this is called
	 * determines the rate at which the game state is updated.
	 *
	 */
	public void clockTick() {
		//System.gc(); TODO
		if (state != PLAYING && state != GAMEOVER) {
			System.out.println("Inactive on tick board 380");
			return; // do nothing unless the game is active.
		}

		ArrayList<Characters> demons = new ArrayList<Characters>();

		int nplayers = 0;
		for (int i = 0; i != getCharacters().size(); ++i) {
			Characters hero = getCharacters().get(i);
			if(hero instanceof Hero){
				//System.out.println("NOT A DEMON");
				Hero h = (Hero)hero;
				h.ping(this);
			}

			Characters p = getCharacters().get(i);
			p.ping(this);
			// reread p, since it might be gone now ...
			p = getCharacters().get(i); //TODO
			if (p == null) {
				//dead character encountered, remove it.
				getCharacters().remove(i--);// TODO
				continue;
			}

			if (p instanceof Demon) {
				demons.add(p);
			}
		}



		if (nplayers == 0) {
			state = GAMEOVER;
		}
	}
	
	/**
	 * This method reads in the current state of the game
	 * that has been broadcast from the server on the 
	 * clients board. It then updates the clients board
	 * to represent the most current state broadcast
	 * 
	 * @param str String -input string to be read and update the currents state with
	 */
	public void updateBoard(String str) {
		//System.out.println("here updateboard size: "+str.length());
		int count= 0;
		//System.out.println("before iteration");
		while (str.charAt(count)!='|'){
			//System.out.println("here-1");
			if (str.charAt(count)=='|'){
				//System.out.println("BREAKING");
				//only one player has connected
				break;
			}
			if (str.charAt(count) == 'u' && str.charAt(count+1) == 'i'
					&& str.charAt(count+2) == 'd') {
				//System.out.println("NO BREAKING");
				// awesome uve found the players uid
				count += 4;// get to the coords
				int uid = Integer.parseInt(str.substring(count, count + 1));
				// now we start the readins
				readPlayerIn(str.substring(count + 1, str.length()), uid);
			}
			count++;

		}

	}
	
	/**
	 * This class reads in individual players coordinates and
	 * updates the game board accordingly
	 * 
	 * 
	 * @param str String - the current players information
	 * @param uid int - player uid (player being read in)
	 */
	private void readPlayerIn(String str, int uid){
		int index = 0;
		int newX = 0;
		int newY = 0;
		String s;
		//System.out.println("here1 readPLayerIn");
		while (str.charAt(index)!='-'){
			//first check uid matches
			//get the x coord
			index++;
			if (str.charAt(index) == 'x') {
				index++;
				int count=0;

				while(isInteger(str.substring(index, index+1))){
					count++;
					index++;
				}
				s = str.substring(index-count, index);
				newX = Integer.parseInt(s);
			}

			//now get y
			if(str.charAt(index)==','){
				index++;
				int count=0;
				while(isInteger(str.substring(index, index+1))){
					count++;
					index++;
				}
				s = str.substring(index-count, index);
				newY = Integer.parseInt(s);
				//System.out.println("newY "+newY);
				break;

			}

			//then update potions
			//then finish / do more stuff

		}

		try{
			player(uid).getCoordinate().updateCoordinate(newX, newY);

		} catch (IllegalArgumentException e){
			registerHeroPortal();
			registerHero();

		}
	}
	
	//TODO find out where this is being called
	public void writePlayerOut(String str){

		String newStr = "";
		for(Characters c : this.getCharacters()){
			if(c instanceof Hero){
				c = (Hero)c;
				newStr += "uid "+(((Hero) c).getUid())+" x"+c.getCoordinate().getX()+","+c.getCoordinate().getY()+" -";
			}
		}

	}
	
	/**
	 * This method is used to test whether a string contains
	 * an integer 
	 * 
	 * @param s String - string to check for integer contents
	 * @return boolean - true if contains int, false otherwise
	 */
	public static boolean isInteger(String s) {
	    try {
	        Integer.parseInt(s);
	    } catch(NumberFormatException e) {
	        return false;
	    }
	    // only got here if we didn't return false
	    return true;
	}

	/**
	 * This method returns the number of players currently 
	 * registered with the local board
	 * 
	 * @return int - Current number of plays in the game
	 */
	public int numPlayers(){
		return numPlayers;
	}
	
	/**
	 * This method is used to set the number of players currently 
	 * registered with the local board
	 * 
	 */
	public void numPlayers(int i){
		this.numPlayers = i;
	}

	public int getxTrans() {
		return xTrans;
	}

	public void setxTrans(int xTrans) {
		this.xTrans = xTrans;
	}

	public int getyTrans() {
		return yTrans;
	}

	public void setyTrans(int yTrans) {
		this.yTrans = yTrans;
	}
	
	/**
	 * This method returns the arrayList containing
	 * the characters on the board
	 * 
	 * @return ArrayList<Characters> - the array containing characters
	 */
	public ArrayList<Characters> getCharacters() {
		return characters;
	}
	
	/**
	 * This method sets the arrayList containing
	 * the characters on the board
	 * @param array ArrayList<Characters> - array to set the character list to.
	 */
	public void setCharacters(ArrayList<Characters> array) {
		this.characters = array;
	}
}