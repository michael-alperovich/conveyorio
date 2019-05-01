package structures;

import java.awt.Graphics;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import assets.ConveyorAssets;
import conveyorio.Point;
import conveyorio.World;
import objects.GenericGameObject;

public class Conveyor extends Structure {

    private DIRECTIONS direction;
    public Conveyor previous, next;
    public List<GenericGameObject> objects;
    private int[] updateVector;
    private int updateDelay;
    public long lastTime = System.currentTimeMillis();
    public double pixPerSecond = 50; // TODO: pixels per second speed.
    public final int LENGTH = 50;

    public Conveyor(Point loc, DIRECTIONS d) {
        super(loc, 50, 50);
        direction = d;
        updateDelay = 5;
        switch (direction) {
            case NORTH:
                updateVector = new int[]{0, -1};
                break;
            case EAST:
                updateVector = new int[]{1, 0};
                break;
            case SOUTH:
                updateVector = new int[]{0, 1};
                break;
            case WEST:
                updateVector = new int[]{-1, 0};
                break;
            default:
                System.out.println("[-] error bad direction: " + direction + " sleeping 10 seconds");
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
        }
        objects = new ArrayList<>();
        // TODO search for previous and next conveyor
        int targetx = location.getX() + 50 * updateVector[0];
        int targety = location.getY() + 50 * updateVector[1];
        next = (Conveyor) World.getTileAt(new Point(targetx, targety));
        targetx = location.getX() - 50 * updateVector[0];
        targety = location.getY() - 50 * updateVector[1];
        previous = (Conveyor) World.getTileAt(new Point(targetx, targety));
        if (next == null || next.direction != this.direction) {
            World.registerSink(this);
        }
        if (previous != null && previous.direction == this.direction) {
            World.deregisterSink(previous);
        } else {
            previous = null;
        }
        if (next != null) {
            next.previous = this;
        }
        if (previous != null) {
            previous.next = this;
        }

        World.addTile(this);
    }

    @Override
    public void onUpdate(Graphics g, int px, int py, ImageObserver ref) {
        // update object positions
        //System.out.println("getting updated: "+direction);
        //System.out.println(this.location.getX() + " "+this.location.getY() + " "+dimy + " "+dimx);
        long time = System.currentTimeMillis();
        int cSecondPeriod = (int) (time % 1000);
        cSecondPeriod /= 20;
        switch (direction) {
            case NORTH:
                g.drawImage(ConveyorAssets.north[cSecondPeriod], this.location.getX()-px, this.location.getY()-py, 50, 50, ref);
                break;
            case EAST:
                g.drawImage(ConveyorAssets.east[cSecondPeriod], this.location.getX()-px, this.location.getY()-py, ref);
                break;
            case WEST:
                g.drawImage(ConveyorAssets.west[cSecondPeriod], this.location.getX()-px, this.location.getY()-py, ref);
                break;
            case SOUTH:
                g.drawImage(ConveyorAssets.south[cSecondPeriod], this.location.getX()-px, this.location.getY()-py, ref);
                break;
            default:
                System.out.println("missed direction no render");
                break;
        }
        
        for (int i =  0; i < objects.size(); i++) {
            GenericGameObject object = objects.get(i);
            // if object is about to be outside the current conveyor
            if (toLocal(object.getCurrentx(), object.getCurrenty())[1]>= 0) {
                boolean canMove = true;
                if (next != null) { // if next conveyor exists
                    if (!next.objects.contains(object)) {// if object is not on next yet
                        if (next.canReceive(object)) {
                        	if (next.direction != this.direction) {
                        		object.updatePosition(object.getCurrentx() + updateVector[0]*25,
                                                    object.getCurrenty() + updateVector[1]*25);
                        	}
                            next.onTake(object);
                        } else {
                            canMove = false;
                        }
                    }
                } else {
                    canMove = false;
                }
                if (canMove) {
                	long deltaTime = time-lastTime;
                    //object.updatePosition(object.getCurrentx() + updateVector[0]*pixPerSecond*deltaTime/1000.0,
                    //            object.getCurrenty() + updateVector[1]*pixPerSecond*deltaTime/1000.0);
                    
                }
                // remove the object if the object is outside the conveyor
                if (toLocal(object.getCurrentx(), object.getCurrenty())[1] - object.dimx >= 0) {
                    objects.remove(object);
                }
            }
            // if object is still inside the conveyor
            else {
                // if it does not interferer with another object on the same conveyor
            	
                for (GenericGameObject secondObject : objects) {
                    if (secondObject != object && toLocal(object.getCurrentx(), object.getCurrenty())[1] >=
                            toLocal(secondObject.getCurrentx(), secondObject.getCurrenty())[1] - (object.dimx)) {
                    	continue;
                    }
                }
            	long deltaTime = time-lastTime;
                object.updatePosition(object.getCurrentx() + updateVector[0]*pixPerSecond*deltaTime/1000.0,
                             object.getCurrenty() + updateVector[1]*pixPerSecond*deltaTime/1000.0);
                
            }
            // TODO render object
            //System.out.println(toLocal(object.getCurrentx(), object.getCurrenty())[1]);
            //System.out.println(object.getCurrentx() + " " + object.getCurrenty());
            g.drawImage(object.getIcon(), (int) object.getCurrentx()-px, (int) object.getCurrenty()-py, ref);
        }
        lastTime = time;
        if (previous != null) {
            previous.onUpdate(g, px, py, ref);
        }
        
    }

    @Override
    void onDelete() {

    }

    private double[] toLocal(double d, double e) {
        double[] ans = toLocalCartesian(d, e);
        ans[1] *= -1;
        return ans;
    }

    private double[] toLocalCartesian(double d, double e2) {
        double dx = d - (location.getX());
        double dy = e2 - location.getY();
        switch (direction) {
            case NORTH:
                return new double[]{dx, dy};
            case EAST:
                return new double[]{-dy, d - (location.getX() + xlen)};
            case SOUTH:
                return new double[]{-(d - (location.getX() + xlen)), (e2 - (location.getY() + ylen))};
            case WEST:
                return new double[]{(e2 - (location.getY() - ylen)), dx};
            default:
                System.out.println("[-] error bad direction: " + direction + " sleeping 10 seconds");
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
        }
        return new double[]{0, 0};
    }

    @Override
    public void onTake(GenericGameObject object) {
        objects.add(object);
    }

    @Override
    public boolean canReceive(GenericGameObject object) {
        double[] coordinates = toLocal(object.getCurrentx(), object.getCurrenty());
        for (GenericGameObject storedObject : objects) {
            // check for objects intersection
            if (!(toLocal(storedObject.getCurrentx(), storedObject.getCurrenty())[0] > coordinates[0] + object.dimx ||
                    toLocal(storedObject.getCurrentx(), storedObject.getCurrenty())[0] + storedObject.dimx < coordinates[0])) {
                return false;
            }
        }
        return true;
    }
}
