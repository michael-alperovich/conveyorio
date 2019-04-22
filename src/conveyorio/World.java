package conveyorio;

import java.util.HashMap;

import structures.Structure;

/*
 * Rendering Chunk, Can Render noGraphic or Graphics.
 */


public class World {
	public static HashMap<Point, Structure> world = new HashMap<Point,Structure>();
	
	public World() {
		
	
	}
	public static Structure getTileAt(int x, int y) {
		return  world.getOrDefault(arg0, arg1);
	}
	
}


class Point {
	public int x;
	public int y;
	public Point(int x,int y) {
		
	}
	@Override
	public int hashCode() {
		
		return 1;
	}
}