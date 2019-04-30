package structures;

import java.awt.Graphics;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
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
                g.drawImage(ConveyorAssets.north[cSecondPeriod], this.location.getX(), this.location.getY(), 50, 50, ref);
                break;
            case EAST:
                g.drawImage(ConveyorAssets.east[cSecondPeriod], this.location.getX(), this.location.getY(), ref);
                break;
            case WEST:
                g.drawImage(ConveyorAssets.west[cSecondPeriod], this.location.getX(), this.location.getY(), ref);
                break;
            case SOUTH:
                g.drawImage(ConveyorAssets.south[cSecondPeriod], this.location.getX(), this.location.getY(), ref);
                break;
            default:
                System.out.println("missed direction no render");
                break;
        }

        //g.drawImage(img, x, y, width, height, observer)
        for (int i =  0; i < objects.size(); i++) {
            GenericGameObject object = objects.get(i);
            // if object is about to be outside the current conveyor
            if (toLocal(object.getCurrentx(), object.getCurrenty())[1]>= 0) {
                boolean canMove = true;
                if (next != null) { // if next conveyor exists
                    if (!next.objects.contains(object)) {// if object is not on next yet
                        if (next.canReceive(object)) {
                            next.onTake(object);
                        } else {
                            canMove = false;
                        }
                    }
                } else {
                    canMove = false;
                }
                if (canMove) {
                    if (time % updateDelay == 0) {
                        object.updatePosition(object.getCurrentx() + updateVector[0],
                                object.getCurrenty() + updateVector[1]);
                    }
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
                            toLocal(secondObject.getCurrentx(), secondObject.getCurrenty())[1] - object.dimx) {
                        return;
                    }
                }
                if (time % updateDelay == 0) {
                    object.updatePosition(object.getCurrentx() + updateVector[0],
                            object.getCurrenty() + updateVector[1]);
                }
            }
            // TODO render object
            //System.out.println(toLocal(object.getCurrentx(), object.getCurrenty())[1]);
            //System.out.println(object.getCurrentx() + " " + object.getCurrenty());
            g.drawImage(object.getIcon(), object.getCurrentx(), object.getCurrenty(), ref);
        }
        if (previous != null) {
            previous.onUpdate(g, px, py, ref);
        }
    }

    @Override
    void onDelete() {

    }

    private int[] toLocal(int globalX, int globalY) {
        int[] ans = toLocalCartesian(globalX, globalY);
        ans[1] *= -1;
        return ans;
    }

    private int[] toLocalCartesian(int globalX, int globalY) {
        int dx = globalX - (location.getX());
        int dy = globalY - location.getY();
        switch (direction) {
            case NORTH:
                return new int[]{dx, dy};
            case EAST:
                return new int[]{-dy, globalX - (location.getX() + xlen)};
            case SOUTH:
                return new int[]{-(globalX - (location.getX() + xlen)), (globalY - (location.getY() + ylen))};
            case WEST:
                return new int[]{(globalY - (location.getY() - ylen)), dx};
            default:
                System.out.println("[-] error bad direction: " + direction + " sleeping 10 seconds");
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
        }
        return new int[]{0, 0};
    }

    @Override
    public void onTake(GenericGameObject object) {
        objects.add(object);
    }

    @Override
    public boolean canReceive(GenericGameObject object) {
        int[] coordinates = toLocal(object.getCurrentx(), object.getCurrenty());
        for (GenericGameObject storedObject : objects) {
            // check for objects intersection
            if (!(toLocal(storedObject.getCurrentx(), storedObject.getCurrenty())[1] > coordinates[1] + object.dimx ||
                    toLocal(storedObject.getCurrentx(), storedObject.getCurrenty())[1] + storedObject.dimx < coordinates[1])) {
                return false;
            }
        }
        return true;
    }
}
