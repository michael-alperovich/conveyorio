package structures;

import java.awt.Graphics;

import objects.GenericGameObject;
import properties.Viewable;
import conveyorio.*;

public abstract class Structure extends Viewable{
	/*
	 * All Structures in the game
	 * Must implement Viewable interface for redering,
	 * Structure interface defines x, y, xlen, ylen.
	 * 
	 */
	public int xlen,ylen;
	public Point location;
	public Structure(Point loc,int dimX, int dimY) {
		super(dimX,dimY);
		location = loc;
		xlen = dimX;
		ylen = dimY;
	}
	
	public abstract void onUpdate(Graphics g,Point p, long systemTime);
	
	abstract void onPlace();
	
	abstract void onDelete();
	abstract boolean canRecieve(int x, int y);
	abstract boolean canTake(int x, int y);
	abstract void onGet(int localX, int localY, GenericGameObject g);
	abstract void onTake(int localX, int localY, GenericGameObject g);
}
