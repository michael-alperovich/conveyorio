package objects.GuiTiles;

import java.awt.image.BufferedImage;

import assets.FurnaceAssets;
import conveyorio.Point;
import properties.Placeable;
import structures.Furnace;
import structures.Structure;

public class FuranceTile extends Placeable {

	public FuranceTile() {
		super(50,50);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Furance Tile";
	}

	@Override
	public BufferedImage onRender() {
		// TODO Auto-generated method stub
		return FurnaceAssets.getFire();
	}

	@Override
	public void onPlace(int xloc, int yloc) {
		// TODO Auto-generated method stub
		new Furnace(new Point(xloc,yloc));
	}

	@Override
	public void onRotate() {
		// TODO Auto-generated method stub

	}

}
