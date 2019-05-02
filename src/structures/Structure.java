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
	public static DIRECTIONS rotateClockwise(DIRECTIONS dir) {
		switch(dir) {
		case NORTH:
			return DIRECTIONS.EAST;
		case EAST:
			return DIRECTIONS.SOUTH;
		case SOUTH:
			return DIRECTIONS.WEST;
		case WEST:
			return DIRECTIONS.NORTH;
		default:
			System.out.println("bad rotation(cw): "+dir);
			return DIRECTIONS.NORTH;
		}
	}
	public static DIRECTIONS rotateCounterClockwise(DIRECTIONS dir) {
		switch(dir) {
		case NORTH:
			return DIRECTIONS.WEST;
		case EAST:
			return DIRECTIONS.NORTH;
		case SOUTH:
			return DIRECTIONS.EAST;
		case WEST:
			return DIRECTIONS.SOUTH;
		default:
			System.out.println("bad rotation(cc): "+dir);
			return DIRECTIONS.NORTH;
		}
	}
	public static int[] toVector(DIRECTIONS dir) {
		switch(dir) {
		case NORTH:
			return new int[] {0,-1};
		case SOUTH:
			return new int[] {0,1};
		case EAST:
			return new int[] {1,0};
		case WEST:
			return new int[] {-1,-0};
		default:
			System.out.println("bad direction: "+dir);
			return new int[] {0,0};
		}
	}
	public abstract void onUpdate(Graphics g, int px, int py, ImageObserver ref);
	
	
	abstract void onDelete();
	abstract boolean canReceive(GenericGameObject object);
	abstract void onTake(GenericGameObject g);
}
