package conveyorio;

import java.util.HashMap;
import java.util.HashSet;

import structures.Structure;

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
	public void UpdateObjects() {
		for (Point source : conveyorSources) {
			
		}
	}
	public static Structure getTileAt(int x, int y) {
		return  world.getOrDefault(new Point(x, y),null);
	}
	
}


class Point {
	private int x;
	private int y;

	public Point(int x,int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	@Override
	public int hashCode() {
		return (x * 31 + y) % (int)(10E9 + 7);
	}
}