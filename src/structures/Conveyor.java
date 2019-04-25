package structures;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import conveyorio.Point;
import objects.GenericGameObject;

public class Conveyor extends Structure {

    private DIRECTIONS direction;
    public Conveyor previous, next;
    public List<GenericGameObject> objects;
    private int[] updateVector;
    private int speed;
    public final int LENGTH = 50;

    public Conveyor(Point loc, DIRECTIONS d) {
        super(loc, 2, 2);
        direction = d;
        switch (direction) {
            case NORTH:
                updateVector = new int[]{0, 1};
            case EAST:
                updateVector = new int[]{1, 0};
            case SOUTH:
                updateVector = new int[]{0, -1};
            case WEST:
                updateVector = new int[]{-1, 0};
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

    }

    @Override
    public void onUpdate(Graphics g, int px, int py) {
        // update object positions
        for (GenericGameObject object : objects) {
            // if object is about to be outside the current conveyor
            if (toLocal(object.getCurrentx(), object.getCurrenty())[0] > LENGTH) {
                boolean canMove = true;
                if (next != null) { // if next conveyor exists
                    if (!next.objects.contains(object)) {// if object is not on next yet
                        if (next.canReceive(object)) {
                            next.onTake(object);
                        }
                        else {
                            canMove = false;
                        }
                    }
                }
                else {
                    canMove = false;
                }
                if (canMove) {
                    object.updatePosition(object.getCurrentx() + updateVector[0] * speed,
                            object.getCurrenty() + updateVector[1] * speed);
                }
                // remove the object if the object is outside the conveyor
                if (toLocal(object.getCurrentx(), object.getCurrenty())[0] - object.dimx > LENGTH) {
                    objects.remove(object);
                }
            }
            // if object is still inside the conveyor
            else {
                // if it does not interferer with another object on the same conveyor
                for (GenericGameObject secondObject : objects) {
                    if (secondObject != object && toLocal(object.getCurrentx(), object.getCurrenty())[0] + object.dimx >=
                            toLocal(secondObject.getCurrentx(), secondObject.getCurrenty())[0]) {
                        return;
                    }
                }
                object.updatePosition(object.getCurrentx() + updateVector[0] * speed,
                        object.getCurrenty() + updateVector[1] * speed);
            }
            // TODO render object
        }
    }

    @Override
    void onDelete() {

    }

    private int[] toLocal(int globalX, int globalY) {
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
                return new int[]{(globalY - (location.getY() - ylen)), -dx};
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
    void onTake(GenericGameObject object) {
        objects.add(object);
    }

    @Override
    public boolean canReceive(GenericGameObject object) {
        int[] coordinates = toLocal(object.getCurrentx(), object.getCurrenty());
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
