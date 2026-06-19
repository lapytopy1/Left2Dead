package game;
/**
 *
 * @author Jon
 *
 */
public class GameCoordinate {

	private int x;
	private int y;
	private final String name;

	public GameCoordinate(int x, int y, String name) {
		//System.out.println("Here "+x+" "+y+" :GameCoord 14");
		this.x = x;
		this.y = y;
		this.name = name;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	/**
	 * Converts from position to a coordinate square of pixels
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean setCoordinate(int x, int y){
		this.x = x / 16;
		this.y = y /16;
		return true;
	}
	
	public boolean updateCoordinate(int x, int y){
		this.x = x;
		this.y = y;
		return true;
	}

	public GameCoordinate getCoordinate(){
		return this;
	}

	/**
	 *
	 * Moves the object by a 16x16 coordinate square
	 */
	public void move(int x, int y){
		this.x = x * 16;
		this.y = y * 16;
	}

	/**
	 * Moving coordinate methods by a number of pixels
	 */

	public void moveLeft(){
		this.x = this.x - 16;
	}

	public void moveRight(){
		this.x = this.x + 16;
	}

	public void moveDown(){
		this.y = this.y + 16;
	}

	public void moveUp(){
		this.y = this.y - 16;
	}
}
