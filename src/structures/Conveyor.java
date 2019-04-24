package structures;

import java.util.LinkedList;

import objects.GenericGameObject;

public class Conveyor extends Structure {

    private DIRECTIONS direction;
    public Conveyor previous, next;
    public LinkedList<GenericGameObject> objects;
    private int[] updateVector;
    public int speed;
    public final int LENGTH = 50;
    
    public Conveyor(int x, int y, DIRECTIONS d) {
        super(x, y, 2, 2);
        direction = d;
        switch (direction){
			case NORTH:
				updateVector = new int[]{0, 1};
			case EAST:
				updateVector = new int[]{1, 0};
			case SOUTH:
				updateVector = new int[]{0, -1};
			case WEST:
				updateVector = new int[]{-1, 0};
			default:
				System.out.println("[-] error bad direction: "+direction+" sleeping 10 seconds");
				try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		// TODO search for previous and next conveyor
        
    }
    
    @Override
    void onUpdate() {
		// update object positions
		for (GenericGameObject object: objects) {
			// if object intersects the next conveyor

			object.updatePosition(object.getCurrentx() + updateVector[0] * speed,
					object.getCurrenty() + updateVector[1] * speed);

			// remove the object if the object is outside the conveyor
			if (toLocal(object.getCurrentx(), object.getCurrenty())[0] > LENGTH) {
				objects.remove(object);
			}
		}
    }

    @Override
    void onDelete() {

    }

    public int[] toLocal(int localX, int localY)  {
    	int dx = localX-x;
    	int dy = localY-y;
    	switch(direction) {
    		case NORTH:
    			return new int[] {dx,dy};
    		case EAST:
    			return new int[] {dy,dx};
    		case SOUTH:
    			return new int[] {-dx,-dy};
    		case WEST:
    			return new int[] {-dy,-dx};
    		default:
    			System.out.println("[-] error bad direction: "+direction+" sleeping 10 seconds");
				try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    			return new int[] {0,0};
    	}
    }
    
    @Override
    void onTake(GenericGameObject object) {
		objects.add(object);
    }

	@Override
	boolean canReceive(GenericGameObject object) {
		int[] coordinates = toLocal(object.getCurrentx(), object.getCurrenty());
		for (GenericGameObject storedObject:objects) {
			// check for objects intersection
			if (!(toLocal(storedObject.getCurrentx(), storedObject.getCurrenty())[0] > coordinates[0] + object.dimx ||
					toLocal(storedObject.getCurrentx(), storedObject.getCurrenty())[0] + storedObject.dimx < coordinates[0])) {
				return false;
			}
		}
		return true;
	}
}
