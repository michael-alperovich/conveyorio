package properties;

import java.awt.image.BufferedImage;

import conveyorio.Point;
import conveyorio.World;
import objects.GenericGameObject;
import structures.Structure;

public abstract class PlaceableItem extends Placeable {

	public PlaceableItem() {
		super(25,25);
		
	}

	public abstract String getName();
	public abstract BufferedImage onRender();
	@Override
	public void onPlace(int xloc, int yloc) {
		// TODO Auto-generated method stub
		int globalX = (int) (Math.floor(xloc/50.0)*50);
		int globalY = (int) (Math.floor(yloc/50.0)*50);
		Structure target = World.getTileAt(new Point(globalX,globalY));
		if (target == null) {return;}
		GenericGameObject object = getObject();
		object.updatePosition(xloc, yloc);
		if (target.canReceive(object, null)) {
			target.onTake(object, null);
		}
	}
	public abstract void onSwitch();
	public abstract GenericGameObject getObject();
	
	public void onRotate() {onSwitch();};

}
