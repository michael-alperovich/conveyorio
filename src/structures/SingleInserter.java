package structures;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.ImageObserver;
import java.util.LinkedList;

import assets.SingleInserterAssets;
import conveyorio.Camera;
import conveyorio.Point;
import conveyorio.World;
import objects.GenericGameObject;


public class SingleInserter extends Structure {

    public DIRECTIONS direction;
    public GenericGameObject object;
    private int updateDelay;
    public long lastTime = System.currentTimeMillis();
    public int angle;
    public boolean canMove;
    public Structure source, sink;
    private final int RADIUS = 37;
    public Point center;

    public SingleInserter(Point loc, DIRECTIONS d) {
        super(loc, 50, 50);
        direction = d;
        updateDelay = 2;

        center = location.add(new Point(-12, -12));
        System.out.println(center);
    }
    public String getCode() {
		return String.format("Inserter %s %s %s", super.location.getX(),super.location.getY(), direction);
	}
    private void checkConveyors() {
        int targetx = location.getX() + 50 * Conveyor.toVector(direction)[0];
        int targety = location.getY() + 50 * Conveyor.toVector(direction)[1];
        source =  World.getTileAt(new Point(targetx, targety));

        if (source instanceof SingleInserter) {
            if (((SingleInserter) source).direction != this.direction) {
                    source = null;
            }
        }
        targetx = location.getX() - 50 * Conveyor.toVector(direction)[0];
        targety = location.getY() - 50 * Conveyor.toVector(direction)[1];

        sink =  World.getTileAt(new Point(targetx, targety));

        if (sink instanceof SingleInserter) {
            if (((SingleInserter) sink).direction != this.direction) {
                sink = null;
            }
        }
    }

    @Override
    public void onUpdate(Graphics g,  ImageObserver ref) {

    	this.checkConveyors();
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
        g.drawImage(SingleInserterAssets.phases[displayAngle], Camera.remapX(this.center.getX()), Camera.remapY(this.center.getY()), Camera.resizeX(75),Camera.resizeY(75), ref);
        if (object != null) {

            double newX = center.getX() + RADIUS * Math.cos((360 - displayAngle) % 360 / 180.0 * Math.PI) - object.dimx / 2.0 + RADIUS;
            double newY = center.getY() + RADIUS * Math.sin((360 - displayAngle) % 360 / 180.0 * Math.PI) - object.dimy / 2.0 + RADIUS;
            object.updatePosition(newX, newY);
            g.drawImage(object.getIcon(), Camera.remapX(object.getCurrentx()), Camera.remapY(object.getCurrenty()),Camera.resizeX(object.getIcon().getWidth()),Camera.resizeY(object.getIcon().getHeight()),ref);
        }


        canMove = true;
        if (angle == 0) {
            if (source != null && source.objects.size() != 0 && object == null) {
                double minDist = Double.MAX_VALUE;
                GenericGameObject closestObj = null;
                for (int i = 0; i < source.objects.size(); i++) {
                    GenericGameObject o = source.objects.get(i);
                    double dist = Math.hypot(this.center.getX() - o.getCurrentx(), this.center.getY() - o.getCurrenty());
                    if (dist < minDist && !source.voided.contains(o) && accepts(o)) { // source.canRemove(object)

                        minDist = dist;
                        closestObj = o;
                    }
                }
                
                
                if (closestObj == null) {
                	return;
                }
                source.onRemove(closestObj);
                object = closestObj;
                objects.add(object);
                voided.add(object);
                canMove = object != null;
            }
            else {
                canMove = object != null;
            }
        }
        else if (angle == 180) {
            if (sink != null && object != null && sink.canReceive(object, this)) {
                sink.onTake(object, this);
                voided.remove(object);
                objects.remove(object);
                object = null;
            }
            else {
                canMove = object == null;
            }
        }
        if (canMove) {
            long time = System.currentTimeMillis();
            if (angle < 180) {
                angle = ((int)Math.min(angle + (time - lastTime) / updateDelay, 180)) % 360;
            }
            else {
                angle = ((int)Math.min(angle + (time - lastTime) / updateDelay, 360)) % 360;

            }
            lastTime = time;
        }
        else {
            lastTime = System.currentTimeMillis();
        }
    }

    @Override
    public void onTake(GenericGameObject object, Structure source) {
    	debug("onTake from "+source);
        this.object = object;
        objects.add(object);
        voided.add(object);
    }
    public boolean accepts(GenericGameObject obj) {
    	return true;
    }
    @Override
    public boolean canReceive(GenericGameObject object, Structure s) {
        return (angle == 0 && this.object == null);
    }
    public void displayGUI(Graphics g, ImageObserver ref, int xWindowSize, int yWindowSize) {
    	Graphics2D g2 = (Graphics2D) g;
		g.setColor(new Color(100, 100, 100));
		g.fillRect(xWindowSize-200, 0, xWindowSize, 500);
		g.setColor(new Color(255,255,100));
		g2.drawString("Regular Inserter Belt", xWindowSize-190, 20);
		g2.drawString(this.toString(), xWindowSize-190, 40);
		g.setColor(new Color(255,255,255));
		g2.drawString("Direction: "+this.direction,xWindowSize-190, 70);
		g2.drawString("Cordinates: "+location.toString(),xWindowSize-170,180);
		if(source == null) {
			g.setColor(new Color(255,0,0));
			g2.drawString("Source Reference: nullref",xWindowSize-170 , 90);
		}
		else {
			g.setColor(new Color(0,255,0));
			g2.drawString("Source Reference: ",xWindowSize-170 , 90);
			g2.drawString(source.toString(), xWindowSize-190, 120);
		}
		
		if(sink  == null) {
			g.setColor(new Color(255,0,0));
			g2.drawString("Sink Reference: nullref",xWindowSize-170 , 140);
		}
		else {
			g.setColor(new Color(0,255,0));
			g2.drawString("Sink Reference: ",xWindowSize-170 , 140);
			g2.drawString(sink.toString(), xWindowSize-190, 160);
		}
		
		g.setColor(Color.BLUE);
		g2.drawString("Can Move: "+canMove, xWindowSize-170,200);
		g.setColor(new Color(255,255,0));
		g2.drawString("Contains: "+object, xWindowSize-190,220);
		boolean canRec =(angle == 0 && this.object == null);
		g2.drawString("Can Recieve: "+canRec,xWindowSize-190,240);
    }

	@Override
	public void onRemove(GenericGameObject g) {
		voided.remove(g);
		objects.remove(g);
		object = null;
		
	}
}
