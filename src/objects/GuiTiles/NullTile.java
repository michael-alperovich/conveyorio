package objects.GuiTiles;

import java.awt.image.BufferedImage;

import assets.MiscAssets;
import properties.Placeable;
import structures.Structure;

public class NullTile extends Placeable{
	/*
	 * NullTile - has -1 functionality
	 */
	
	
	public NullTile() {
		super(0,0);
	}

	@Override
	public Structure onPlace(int xloc, int yloc) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onRotate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public BufferedImage onRender() {
		return MiscAssets.nullTile;
	}



	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Unselect";
	}

}
