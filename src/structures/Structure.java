package structures;

import java.awt.Graphics;
import java.awt.image.ImageObserver;

import conveyorio.Point;
import objects.GenericGameObject;
import properties.Viewable;

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
	
	public abstract void onUpdate(Graphics g, int px, int py, ImageObserver ref);

	
	abstract void onDelete();
	abstract boolean canReceive(GenericGameObject object);
	abstract void onTake(GenericGameObject g);
}
