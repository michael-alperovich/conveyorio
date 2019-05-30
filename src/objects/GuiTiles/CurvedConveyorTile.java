package objects.GuiTiles;

import java.awt.image.BufferedImage;

import assets.CurvedConveyorAssets;
import conveyorio.Point;
import properties.Placeable;
import structures.Conveyor;
import structures.CurvedConveyor;
import structures.DIRECTIONS;

public class CurvedConveyorTile extends Placeable {
	public DIRECTIONS dir;
	public boolean clockwise;
	public int preset =0;
	public CurvedConveyorTile(int xsize, int ysize) {
		super(xsize,ysize);
	}
	public CurvedConveyorTile() {
		super(50,50);
		dir = DIRECTIONS.NORTH;
		clockwise = true;
	}

	@Override
	public String getName() {
		return "Curved Conveyor ";
	}

	@Override
	public BufferedImage onRender() {
		int fp = (int) (System.currentTimeMillis() % 500);
		fp /= 5;
		
		return CurvedConveyorAssets.getPhase(dir, clockwise, fp);
	}

	@Override
	public void onPlace(int xloc, int yloc) {
		new CurvedConveyor(new Point(xloc,yloc),dir,clockwise);
	}

	@Override
	public void onRotate() {
		// TODO Auto-generated method stub
		preset += 1;
		if (preset % 4 == 0) {
			clockwise = !clockwise;
		}
		dir = Conveyor.rotateClockwise(dir);
	}

}
