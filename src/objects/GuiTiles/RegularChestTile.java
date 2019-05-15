package objects.GuiTiles;

import java.awt.image.BufferedImage;

import assets.ChestAssets;
import conveyorio.Point;
import properties.Placeable;
import structures.DIRECTIONS;
import structures.RegularChest;
import structures.Structure;

public class RegularChestTile extends Placeable {

	public DIRECTIONS cdir = DIRECTIONS.EAST;
	public RegularChestTile() {
		super(50,50);
	}

	@Override
	public String getName() {
		return "Regular Chest";
	}

	@Override
	public BufferedImage onRender() {
		return ChestAssets.getOrientation(cdir);
	}

	@Override
	public Structure onPlace(int xloc, int yloc) {
		return new RegularChest(new Point(xloc, yloc), cdir);
	}

	@Override
	public void onRotate() {
		cdir = Structure.rotateClockwise(cdir);
	}

}
