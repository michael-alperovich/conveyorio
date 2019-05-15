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

	private double currentx;
	private double currenty; // location variables
	
	
	public int getID() {
		return objectID;
	}
	public  void setID(int newiD) {
		objectID = newiD;
	}
	public GenericGameObject() { // default constructor
		super(25,25); // they are 1 icon so we pass in a viewable dimension of 1,1.
		currentx = -1;
		currenty = -1;
	}
	public abstract BufferedImage getIcon();
	
	
	public void updatePosition(double newx, double newy) {
		currentx = newx;
		currenty = newy;
	}
	
	public void unlink() {
		objectID = -1;
	}

	public double getCurrentx() {
		return currentx;
	}

	public double getCurrenty() {
		return currenty;
	}
}
// 25 42 --> 17 seconds.
// 21 seconds
// 35 seconds.
// 