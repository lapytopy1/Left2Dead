package Animation;
import game.Board;
import game.GameCoordinate;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public interface Characters {
	public void moveLeft();
	public void moveRight();
	public void moveUp();
	public void moveDown();
	public void attack();
	public GameCoordinate getCoordinate();
	public boolean setCoordinate(int x, int y);
	public void ping(Board b);
	public Characters fromInputStream(DataInputStream din, int x, int y) throws IOException;
	public void toOutputStream(DataOutputStream dout) throws IOException;
}
