package structures;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.ImageObserver;

import assets.CurvedConveyorAssets;
import conveyorio.Camera;
import conveyorio.Point;
import conveyorio.World;
import objects.Coal;
import objects.GenericGameObject;

public class CurvedConveyor extends ConveyorLike {
	private DIRECTIONS direction, trueDirection, backDirection;
	public boolean clockwise;
	public ConveyorLike previous, next;
	private int[] updateVector, backUpdateVector;

	public CurvedConveyor(Point loc, DIRECTIONS d,boolean cw) {
		super(loc,50,50);
		direction = d;
		clockwise = cw;
		switch (direction) {
			case NORTH:
				this.trueDirection = DIRECTIONS.SOUTH;
				break;
			case EAST:
				this.trueDirection = DIRECTIONS.WEST;
				break;
			case SOUTH:
				this.trueDirection = DIRECTIONS.NORTH;
				break;
			case WEST:
				this.trueDirection = DIRECTIONS.EAST;
				break;
		}
		switch (direction) {
			case NORTH:
				if (clockwise) {
					backDirection = DIRECTIONS.EAST;
				}
				else {
					backDirection = DIRECTIONS.WEST;
				}
				break;
			case EAST:
				if (clockwise) {
					backDirection = DIRECTIONS.NORTH;
				}
				else {
					backDirection = DIRECTIONS.SOUTH;
				}
				break;
			case SOUTH:
				if (clockwise) {
					backDirection = DIRECTIONS.WEST;
				}
				else {
					backDirection = DIRECTIONS.EAST;
				}
				break;
			case WEST:
				if (clockwise) {
					backDirection = DIRECTIONS.SOUTH;
				}
				else {
					backDirection = DIRECTIONS.NORTH;
				}
				break;
		}
		updateVector = Conveyor.toVector(trueDirection);
		backUpdateVector = Conveyor.toVector(backDirection);
	}

	private void updateReference() {
		int targetx = location.getX() + 50 * updateVector[0];
		int targety = location.getY() + 50 * updateVector[1];
		if (World.getTileAt(new Point(targetx, targety)) instanceof ConveyorLike) {
			next = (ConveyorLike) World.getTileAt(new Point(targetx, targety));
		} else {
			next = null;
		}
		targetx = location.getX() - 50 * backUpdateVector[0];
		targety = location.getY() - 50 * backUpdateVector[1];

		if (World.getTileAt(new Point(targetx, targety)) instanceof ConveyorLike) {
			previous = (ConveyorLike) World.getTileAt(new Point(targetx, targety));
		} else {
			previous = null;
		}
	}

	@Override
	public void onUpdate(Graphics g, ImageObserver ref) {
		updateReference();
		Graphics2D g2 = (Graphics2D)g;
		long time = System.currentTimeMillis() % 500;
		time /= 5;
		g2.drawImage(CurvedConveyorAssets.getPhase(direction, clockwise,(int)time ),Camera.remapX(location.getX()) , Camera.remapY(location.getY()), Camera.resizedX(50), Camera.resizedY(50), ref);
		
	}

	@Override
	public boolean canReceive(GenericGameObject object, Structure source) {
		return false;
	}

	@Override
	public void onTake(GenericGameObject g, Structure source) {

	}

	@Override
	public void onRemove(GenericGameObject g) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public DIRECTIONS getDirection(boolean asNext) {
		if (asNext) {
			return trueDirection;
		}
		else {
			return backDirection;
		}
	}
}
