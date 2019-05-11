package properties;

import java.awt.image.BufferedImage;

import structures.Structure;

public abstract class Placeable extends Viewable{
	public Placeable(int xsize, int ysize ) {	
		super(xsize,ysize);
	}
	public abstract String getName();
	public abstract BufferedImage onRender(); // animated placables?
	public abstract Structure onPlace(int xloc, int yloc);
	public abstract void onRotate();
	
}
