package properties;

import java.awt.image.BufferedImage;

import conveyorio.Point;
import structures.Structure;

public abstract class Placeable extends Viewable{
	public Placeable(int xsize, int ysize ) {	
		super(xsize,ysize);
	}
	public abstract String getName();
	public abstract BufferedImage onRender(); // animated placables?
	public abstract void onPlace(int xloc, int yloc);
	public abstract void onRotate();
	public void onPlace(Point inputPoint) {
		onPlace(inputPoint.getX(), inputPoint.getY());		
	}
	
}
