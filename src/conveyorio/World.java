package conveyorio;

import java.awt.Graphics;
import java.awt.image.ImageObserver;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;

/*
 * Rendering Chunk, Can Render noGraphic or Graphics.
 */
import structures.Conveyor;
import structures.Structure;


public class World {
	public static HashMap<Point, Structure> world = new HashMap<Point,Structure>();
	
	public static HashSet<Point> conveyorSources  = new HashSet<Point>();
	public static Point view;
	public Point getView() {
		return view;
	}
	public static void setView(Point newP) {
		view = newP;
	}

	public World() {
	}
	public static void UpdateObjects(Graphics g,ImageObserver ref) {
		long fixedTime = System.currentTimeMillis();
		for (Point source : conveyorSources) {
			Structure object = getTileAt(source);
			object.onUpdate(g,view.getX(),view.getY(),ref);	
		}
		Iterator<Entry<Point, Structure>> it = world.entrySet().iterator();
		while(it.hasNext()) {
			Structure s = it.next().getValue();
			s.onUpdate(g,view.getX(),view.getY(),ref );
		}
		
	}
	public static void addTile(Structure p) {
		world.put(p.location, p);
	}
	public static Structure getTileAt(Point targetPoint) {
		return  world.getOrDefault(targetPoint,null);
	}

	public static void registerSink(Conveyor previous) {
		// TODO Auto-generated method stub
		conveyorSources.add(previous.location);
		
	}
	
	public static void deregisterSink(Conveyor previous) {
		// TODO Auto-generated method stub
		conveyorSources.remove(previous.location);
	}
	
}


