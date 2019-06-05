package structures;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.ImageObserver;
import java.util.LinkedList;

import assets.CurvedConveyorAssets;
import conveyorio.Camera;
import conveyorio.Point;
import conveyorio.World;
import objects.GenericGameObject;

public class CurvedConveyor extends Structure {
	public DIRECTIONS direction;
	public boolean clockwise;
	public LinkedList<GenericGameObject> outerSection = new LinkedList<GenericGameObject>();
	public LinkedList<GenericGameObject> innerSection = new LinkedList<GenericGameObject>();
	public long lastTime;
	public CurvedConveyor(Point loc, DIRECTIONS d,boolean cw) {
		super(loc,50,50);
		direction = d;
		clockwise = cw;
		lastTime = System.currentTimeMillis();
	}
	
	@Override
	public void onUpdate(Graphics g, ImageObserver ref) {
		Graphics2D g2 = (Graphics2D)g;
		long time = System.currentTimeMillis() % 500;
		time /= 5;
		g2.drawImage(CurvedConveyorAssets.getPhase(direction, clockwise,(int)time ),Camera.remapX(location.getX()) , Camera.remapY(location.getY()), Camera.resizedX(50), Camera.resizedY(50), ref);
		// 47
		//for(int obj =0; obj < )
		
		
		
		
		
		
		
	}
	

	@Override
	public void onTake(GenericGameObject g, Structure source) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRemove(GenericGameObject g) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean canReceive(GenericGameObject object, Structure source) {
		// TODO Auto-generated method stub
		return false;
	}

}
