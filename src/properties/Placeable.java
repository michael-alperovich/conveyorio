package properties;

import structures.Structure;

public abstract class Placeable extends Viewable{
	
	public Placeable(int xsize, int ysize ) {	
		super(xsize,ysize);
	}
	abstract void onRender(); // animated placables?
	abstract Structure onPlace(int xloc, int yloc);
}
