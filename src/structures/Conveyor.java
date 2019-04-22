package structures;

import java.awt.Graphics;
import java.util.LinkedList;

import objects.GenericGameObject;

public class Conveyor extends Structure {

    private DIRECTIONS direction;
    public Conveyor parent, child;
    public LinkedList<GenericGameObject> leftObjects;
    public LinkedList<GenericGameObject> rightObjects;
    public int speed;
    
    public Conveyor(int x, int y, DIRECTIONS d) {
        super(x, y, 2, 2);
        direction = d;
        // gain concious of where you are going.
        
    }
    
    @Override
    void onUpdate(Graphics g, int px, int py) {

    }

    @Override
    void onUpdate() {
    	// handling stuff off screen.
    }

    @Override
    void onPlace() {

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
    void onGet(int localX, int localY, GenericGameObject g) {
    	// convert it into cordinates we understand; (takes into account direction, assume that north is -y, south is +y, east is +x, west is -x)
    	
    }

    @Override
    void onTake(int localX, int localY, GenericGameObject g) {

    }

	@Override
	boolean canRecieve(int x, int y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	boolean canTake(int x, int y) {
		// TODO Auto-generated method stub
		return false;
	}
}
