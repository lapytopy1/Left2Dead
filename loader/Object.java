package loader;

public class Object {
	private String name;
	private String type;
	private int gid;
	private int x;
	private int y;

	private Points points;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getGid() {
		return gid;
	}

	public void setGid(int gid) {
		this.gid = gid;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setPoints(Points points) {
		this.points = points;
	}

	public int[] getXPoints() {
		return this.points.getX();
	}

	public int[] getYPoints() {
		return this.points.getY();
	}

}
