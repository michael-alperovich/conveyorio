package structures;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.LinkedList;

import assets.ConveyorAssets;
import conveyorio.Point;
import objects.GenericGameObject;
public class Conveyor extends Structure {

    private DIRECTIONS direction;
    public Conveyor parent, child;
    public LinkedList<GenericGameObject> leftObjects;
    public LinkedList<GenericGameObject> rightObjects;
    public int speed;
    
    public Conveyor(Point loc, DIRECTIONS d) {
        super(loc, 2, 2);
        direction = d;
        // gain concious of where you are going.
        
    }
    
    @Override
	public void onUpdate(Graphics g, Point p, long systemTime) { 	
    }


    @Override
    void onPlace() {

    }

    @Override
    void onDelete() {

    }

    public int[] toLocal(int globalX, int globalY)  {
		int dx = globalX-(location.getX());
		int dy = globalY-location.getY();
		switch(direction) {
			case NORTH:
				return new int[] {dx,dy};
			case EAST:
				return new int[] {-dy,globalX-(location.getX() + xlen)};
			case SOUTH:
				return new int[] {-(globalX-(location.getX() + xlen) ),(globalY-(location.getY() + ylen))};
			case WEST:
				return new int[] {(globalY-(location.getY() - ylen)),-dx};
			default:
				System.out.println("[-] error bad direction: "+direction+" sleeping 10 seconds");
				try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return new int[] {0,0};
	}
    
    @Override
    void onGet(int localX, int localY, GenericGameObject g) {
    	// convert it into cordinates we understand; (takes into account direction, assume that north is -y, south is +y, east is +x, west is -x)
    	
    }

    @Override
    void onTake(int localX, int localY, GenericGameObject g) {

    }

	@Override
	boolean canReceive(int x, int y) {
		int[] coordinates = toLocal(x, y);

		return false;
	}

	@Override
	boolean canTake(int x, int y) {
		// TODO Auto-generated method stub
		return false;
	}
}
