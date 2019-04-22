package structures;

import java.awt.Graphics;

import objects.GenericGameObject;
import properties.Viewable;

public abstract class Structure extends Viewable{
	/*
	 * All Structures in the game
	 * Must implement Viewable interface for redering,
	 * Structure interface defines x, y, xlen, ylen.
	 * 
	 */
	public int x,y,xlen,ylen;
	
	public Structure(int xplace, int yplace,int dimX, int dimY) {
		super(dimX,dimY);
		x = xplace;
		y = yplace;
		xlen = dimX;
		ylen = dimY;
	}
	
	abstract void onUpdate(Graphics g, int px, int py);
	abstract void onUpdate();
	
	abstract void onPlace();
	
	abstract void onDelete();
	abstract boolean canRecieve(int x, int y);
	abstract boolean canTake(int x, int y);
	abstract void onGet(int localX, int localY, GenericGameObject g);
	abstract void onTake(int localX, int localY, GenericGameObject g);
}
