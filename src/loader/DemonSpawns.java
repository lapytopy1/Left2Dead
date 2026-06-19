package loader;

import java.util.ArrayList;
import Animation.Demon;

public class DemonSpawns {
	private ArrayList<Demon> weak = new ArrayList<Demon>();
	private ArrayList<Demon> med = new ArrayList<Demon>();
	private ArrayList<Demon> boss = new ArrayList<Demon>();
	
	public DemonSpawns(ArrayList<Demon> demonsEasy, ArrayList<Demon> demonsMed,
			ArrayList<Demon> demonsHard) {
		this.weak = demonsEasy;
		this.med = demonsMed;
		this.boss = demonsHard;
	}
			// TODO Auto-generated constructor stub
	
	public ArrayList<Demon> getWeak() {
		return weak;
	}
	
	public ArrayList<Demon> getMed() {
		return med;
	}
	
	public ArrayList<Demon> getBoss() {
		return boss;
	}
	
}
