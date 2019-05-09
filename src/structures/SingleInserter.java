package structures;

import assets.SingleInserterAssets;
import conveyorio.Camera;
import conveyorio.Point;
import conveyorio.World;
import objects.GenericGameObject;

import java.awt.*;
import java.awt.image.ImageObserver;


public class SingleInserter extends Structure {

    private DIRECTIONS direction;
    public Structure previous, next;
    public GenericGameObject object;
    private int updateDelay;
    public long lastTime = System.currentTimeMillis();
    public int angle;
    private int[] initialPoint;
    private final int RADIUS = 37;

    public SingleInserter(Point loc, DIRECTIONS d) {
        super(loc, 50, 50);
        direction = d;
        updateDelay = 2;
        initialPoint = Conveyor.toVector(direction);
        initialPoint[0] = location.getX() - RADIUS * initialPoint[0] + RADIUS;
        initialPoint[1] = location.getY() - RADIUS * initialPoint[1];
        World.addTile(this);
    }


    @Override
    public void onUpdate(Graphics g,  ImageObserver ref) {
        int displayAngle = angle;
        if (angle > 180) {
            displayAngle = (360 - angle) % 360;
        }
        switch (direction) {
            case NORTH:
                displayAngle = (displayAngle + 90) % 360;
                break;
            case EAST:
                displayAngle = (displayAngle) % 360;
                break;
            case SOUTH:
                displayAngle = (displayAngle + 270) % 360;
                break;
            case WEST:
                displayAngle = (displayAngle + 180) % 360;
                break;
            default:
                System.out.println("missed direction no render");
                break;
        }
        g.drawImage(SingleInserterAssets.phases[displayAngle], Camera.remapX(this.location.getX()), Camera.remapY(this.location.getY()), Camera.resizeX(75),Camera.resizeY(75), ref);
        if (object != null) {
            double newX = initialPoint[0] + RADIUS * Math.cos((360 - displayAngle) % 360 / 180.0 * Math.PI) - object.dimx / 2.0;
            double newY = initialPoint[1] + RADIUS * Math.sin((360 - displayAngle) % 360  / 180.0 * Math.PI) - object.dimy / 2.0;
            object.updatePosition(newX, newY);
            g.drawImage(object.getIcon(), Camera.remapX(object.getCurrentx()), Camera.remapY(object.getCurrenty()),Camera.resizeX(object.getIcon().getWidth()),Camera.resizeY(object.getIcon().getHeight()),ref);
        }
        long time = System.currentTimeMillis();
        if (time - lastTime > updateDelay) {
            angle = (angle + 1) % 360;
            lastTime = time;
        }
    }

    @Override
    void onDelete() {

    }

    @Override
    public void onTake(GenericGameObject object) {
        this.object = object;
    }

    @Override
    public boolean canReceive(GenericGameObject object) {
        return (angle == 0 && object == null);
    }
}
