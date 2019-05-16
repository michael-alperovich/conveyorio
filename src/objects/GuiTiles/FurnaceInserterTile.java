package objects.GuiTiles;

import java.awt.image.BufferedImage;

import assets.SingleInserterAssets;
import conveyorio.Point;
import properties.Placeable;
import structures.DIRECTIONS;
import structures.FuranceInserter;
import structures.Structure;

public class FurnaceInserterTile extends Placeable {
	public FurnaceInserterTile() {
		super(75,75);
		// TODO Auto-generated constructor stub
	}

	public DIRECTIONS dir = DIRECTIONS.EAST;

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Smart Furnace Inserter";
	}

	@Override
	public BufferedImage onRender() {
		// TODO Auto-generated method stub
		switch (dir) {
		case NORTH:
			return SingleInserterAssets.phases[90];
		case SOUTH:
			return SingleInserterAssets.phases[270];
		case EAST:
			return SingleInserterAssets.phases[0];
		case WEST:
			return SingleInserterAssets.phases[180];
		default:
			System.out.println("bad dir onrender insertertile:39");
			return SingleInserterAssets.phases[0];
	}
	}

	@Override
	public void onPlace(int xloc, int yloc) {
		// TODO Auto-generated method stub
		new FuranceInserter(new Point(xloc,yloc), dir);
	}

	@Override
	public void onRotate() {
		// TODO Auto-generated method stub
		dir = Structure.rotateClockwise(dir);
	}

}
