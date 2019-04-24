package conveyorio;

import java.awt.Graphics;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;

import structures.*;
/*
 * Rendering Chunk, Can Render noGraphic or Graphics.
 */


public class World {
	public static HashMap<Point, Structure> world = new HashMap<Point,Structure>();
	
	public HashSet<Point> conveyorSources;
	public Point view;
	public Point getView() {
		return view;
	}
	public void setView(Point newP) {
		view = newP;
	}
	
	public World() {
		
	}
	public void UpdateObjects(Graphics g) {
		long fixedTime = System.currentTimeMillis();
		for (Point source : conveyorSources) {
			Structure object = getTileAt(source);
			object.onUpdate(g, view,fixedTime);	
		}
		Iterator<Entry<Point, Structure>> it = world.entrySet().iterator();
		while(it.hasNext()) {
			Structure s = it.next().getValue();
			if ( ! (s instanceof  Conveyor) ) {
				s.onUpdate(g,view, fixedTime);
			}
		}
		
	}
	public static Structure getTileAt(Point targetPoint) {
		return  world.getOrDefault(targetPoint,null);
	}
	
}


