package objects;

import java.awt.image.BufferedImage;

import assets.ConveyorAssets;
import conveyorio.Point;
import properties.Placeable;
import structures.Conveyor;
import structures.DIRECTIONS;
import structures.Structure;

public class ConveyorTile extends Placeable{
	public DIRECTIONS dir = DIRECTIONS.EAST;
	public ConveyorTile(int xsize, int ysize) {
		super(xsize, ysize);
		// TODO Auto-generated constructor stub
	}

	

	public ConveyorTile() {
		// TODO Auto-generated constructor stub
		super(0,0);
	}



	@Override
	public Structure onPlace(int xloc, int yloc) {
		return new Conveyor(new Point(xloc,yloc) ,dir);
	}

	@Override
	public BufferedImage onRender() {
		// TODO Auto-generated method stub
		switch(dir) {
			case NORTH:
				return ConveyorAssets.north[0];
			case EAST:
				return ConveyorAssets.east[0];
			case SOUTH:
				return ConveyorAssets.south[0];
			case WEST:
				return ConveyorAssets.west[0];
  			default:
				return null;
		}
	}

	@Override
	public void onRotate() {
		// TODO Auto-generated method stub
		dir = Structure.rotateClockwise(dir);
	}



	@Override
	public String getName() {
		return "Conveyor Tile";
	}

}
