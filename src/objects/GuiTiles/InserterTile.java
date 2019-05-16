package objects.GuiTiles;

import java.awt.image.BufferedImage;

import assets.SingleInserterAssets;
import conveyorio.Point;
import properties.Placeable;
import structures.DIRECTIONS;
import structures.SingleInserter;
import structures.Structure;

public class InserterTile extends Placeable{
	public InserterTile() {
		super(75,75);
	}
	public InserterTile(int xsize, int ysize) {
		super(xsize, ysize);
	}

	public DIRECTIONS dir = DIRECTIONS.NORTH;
	@Override
	public String getName() {
		return "Inserter Tile";
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
		 new SingleInserter(new Point(xloc,yloc), dir);
	}

	@Override
	public void onRotate() {
		dir = Structure.rotateClockwise(dir);
		
	}


}
