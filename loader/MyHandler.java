package loader;
import java.util.ArrayList;
import java.util.List;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
public class MyHandler extends DefaultHandler {
	private int mapWidth;
	private int mapHeight;
	private int tileWidth;
	private int tileHeight;
	private TileSet prevTile = new TileSet();
	private Layers prevLayer = new Layers("", 0, 0);
	private ObjectGroup prevGroup = new ObjectGroup();
	private Object prevObj = new Object();
	private String lastQName = "";
	private int scale;
	private List<TileSet> tileSets = new ArrayList<TileSet>();
	private List<Layers> layers = new ArrayList<Layers>();
	private List<ObjectGroup> groups = new ArrayList<ObjectGroup>();
	
	public void setScale(int scale) {
		this.scale = scale;
	}
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		if (qName.equals("map")) {
			mapWidth = Integer.parseInt(attributes.getValue(2));
			mapHeight = Integer.parseInt(attributes.getValue(3));
			tileWidth = (Integer.parseInt(attributes.getValue(4)));
			tileHeight = (Integer.parseInt(attributes.getValue(5)));
		}
		if (qName.equals("tileset")) {
			TileSet tileSet = new TileSet();
			tileSet.setFirstgid(Integer.parseInt(attributes.getValue(0)));
			tileSet.setName(attributes.getValue(1));
			tileSet.setTileWidth(Integer.parseInt(attributes.getValue(2)));
			tileSet.setTileHeight(Integer.parseInt(attributes.getValue(3)));
			prevTile = tileSet;
			
		}
		if (qName.equals("image")) {
			for (int i = 0; i < attributes.getLength(); i++) {
				if (attributes.getQName(i).equals("width")) {
					prevTile.setImageWidth(Integer.parseInt(attributes
							.getValue(i)));
				} else if (attributes.getQName(i).equals("height")) {
					prevTile.setImageHeight(Integer.parseInt(attributes
							.getValue(i)));
				}
			}
			tileSets.add(prevTile);
		}
		if (qName.equals("layer")) {
			Layers layer = new Layers(attributes.getValue(0),
					Integer.parseInt(attributes.getValue(1)),
					Integer.parseInt(attributes.getValue(2)));
			prevLayer = layer;
		}
		if (qName.equals("tile")) {
			if (lastQName.equals("data") || lastQName.equals("tile")) {
				prevLayer.addGid(Integer.parseInt(attributes.getValue(0)));
			}
		}
		if (qName.equals("objectgroup")) {
			ObjectGroup group = new ObjectGroup();
			group.setName(attributes.getValue(0));
			group.setWidth(Integer.parseInt(attributes.getValue(1)));
			group.setHeight(Integer.parseInt(attributes.getValue(2)));
			prevGroup = group;
		}
		if (qName.equals("object")) {
			Object obj = new Object();			
			obj.setName(attributes.getValue(0));
			obj.setType(attributes.getValue(1));
			obj.setX(Integer.parseInt(attributes.getValue(2)));			
			obj.setY(Integer.parseInt(attributes.getValue(3)));
			prevObj = obj;
			prevGroup.addObj(prevObj);
		}
		if (qName.equals("polyline")) {
			String[] line = attributes.getValue(0).split(" ");
			Points points = new Points(line.length);
			for (int i = 0; i < line.length; i++) {
				String[] coords = line[i].split(",");
				points.addX(i, (Integer.parseInt(coords[0]) + prevObj.getX())
						* scale);
				points.addY(i, (Integer.parseInt(coords[1]) + prevObj.getY())
						* scale);
			}
			prevObj.setPoints(points);
		}
		lastQName = qName;
	}
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if (qName.equals("layer")) {
			layers.add(prevLayer);
		} else if (qName.equals("objectgroup")) {
			groups.add(prevGroup);
		}
	}
	public List<TileSet> getTileSets() {
		return this.tileSets;
	}
	public List<Layers> getLayers() {
		return this.layers;
	}
	public List<ObjectGroup> getGroups() {
		return this.groups;
	}
	public int getTileWidth() {
		return tileWidth;
	}
	public int getTileHeight() {
		return tileHeight;
	}
}