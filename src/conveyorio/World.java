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