package objects;

import properties.Viewable;

import java.awt.image.BufferedImage;

public abstract class GenericGameObject extends Viewable{
	/*
	 * Generic Object that can interface with the game
	 * These things are on the lines.
	 * Buildings have to implement
	 */
	
	private int objectID; // object ID so we can query them,
	private int objectTypeID; // objectTypeID so we can query loaded Graphics.
	
	private int currentx;
	private int currenty; // location variables
	
	
	public int getID() {
		return objectID;
	}
	public int getType() {
		return objectTypeID;
	}
	public  void setID(int newiD) {
		objectID = newiD;
	}
	public GenericGameObject(int objectTypeID) { // default constructor
		super(1,1); // they are 1 icon so we pass in a viewable dimension of 1,1.
		currentx = -1;
		currenty = -1;
	}
	abstract BufferedImage getIcon();
	
	
	public void updatePosition(int newx, int newy) {
		currentx = newx;
		currenty = newy;
	}
	
	public void unlink() {
		objectID = -1;
	}

	public int getCurrentx() {
		return currentx;
	}

	public int getCurrenty() {
		return currenty;
	}
}
// 25 42 --> 17 seconds.
// 21 seconds
// 35 seconds.
// 