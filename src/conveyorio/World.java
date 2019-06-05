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
import structures.ConveyorLike;
import structures.Structure;


public class World {
	public static HashMap<Point, Structure> world = new HashMap<Point,Structure>();
	
	public static HashSet<Point> conveyorSources  = new HashSet<Point>();

	public World() {
	}
	public static void UpdateObjects(Graphics g,ImageObserver ref) {
		/*
		 * Current Problem
		 * Async register of a conveyorbelt may deregister a source while conveyorSources is being accessed from the line below.
		 * Causes a concurrentmodification exception, so we cant use a iterable.
		 */

		for (Object source : conveyorSources.toArray()) {
			Structure object = getTileAt((Point)source);
			if (object != null) {

				object.onUpdate(g,ref);	
			}
		}
		Iterator<Entry<Point, Structure>> it = world.entrySet().iterator();
		while(it.hasNext()) {
			Structure s = it.next().getValue();
			if (s instanceof Conveyor) {
				s.onUpdate(g, ref);
			}
		}
		it = world.entrySet().iterator();
		while(it.hasNext()) {
			Structure s = it.next().getValue();
			if (! (s instanceof Conveyor)) {
				s.onUpdate(g, ref);
			}
		}
		
	}
	public static void addTile(Structure p) {
		world.put(p.location, p);
	}
	public static Structure getTileAt(Point targetPoint) {
		return  world.getOrDefault(targetPoint,null);
	}

	public static void registerSink(ConveyorLike previous) {
		if(previous == null) {
			return;
		}
		conveyorSources.add(previous.location);
		
	}
	
	public static void deregisterSink(ConveyorLike previous) {
		conveyorSources.remove(previous.location);
	}

	public static void removeTile(Structure s) {
		world.remove(s.location);
		if(conveyorSources.contains(s.location)) {
			conveyorSources.remove(s.location);
			registerSink(((Conveyor)s).previous);
		}
	}
	
}


