package objects.GuiTiles;

import java.awt.image.BufferedImage;

import assets.ConveyorAssets;
import conveyorio.Point;
import properties.Placeable;
import structures.Conveyor;
import structures.DIRECTIONS;
import structures.Structure;

public class ConveyorTile extends Placeable{
	public DIRECTIONS dir = DIRECTIONS.EAST;
	public ConveyorTile() {
		super(50,50);
	}



	@Override
	public void onPlace(int xloc, int yloc) {
		new Conveyor(new Point(xloc,yloc) ,dir);
	}

	@Override
	public BufferedImage onRender() {
		long time = System.currentTimeMillis();
	    int cSecondPeriod = (int) (time % 1000);
	    cSecondPeriod /= 20; // standard animated tile (conveyor)
		switch(dir) {
			case NORTH:
				return ConveyorAssets.north[cSecondPeriod];
			case EAST:
				return ConveyorAssets.east[cSecondPeriod];
			case SOUTH:
				return ConveyorAssets.south[cSecondPeriod];
			case WEST:
				return ConveyorAssets.west[cSecondPeriod];
  			default:
				return null;
		}
	}

	@Override
	public void onRotate() {
		dir = Structure.rotateClockwise(dir);
	}



	@Override
	public String getName() {
		return "Conveyor Tile";
	}

}
