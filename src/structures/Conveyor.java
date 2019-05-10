package structures;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.List;

import assets.ConveyorAssets;
import conveyorio.Camera;
import conveyorio.Point;
import conveyorio.World;
import objects.GenericGameObject;

public class Conveyor extends Structure {

    private DIRECTIONS direction;
    public Conveyor previous, next;
    public List<GenericGameObject> objects;
    private int[] updateVector;
    public long lastTime = System.currentTimeMillis();
    public double pixPerSecond = 100; 
    public final int LENGTH = 50;

    public Conveyor(Point loc, DIRECTIONS d) {
        super(loc, 50, 50);
        direction = d;
        updateVector = Conveyor.toVector(direction);
        
        objects = new ArrayList<>();

        int targetx = location.getX() + 50 * updateVector[0];
        int targety = location.getY() + 50 * updateVector[1];
        if (World.getTileAt(new Point(targetx, targety)) instanceof Conveyor) {
            next = (Conveyor) World.getTileAt(new Point(targetx, targety));
        }
        targetx = location.getX() - 50 * updateVector[0];
        targety = location.getY() - 50 * updateVector[1];

        if (World.getTileAt(new Point(targetx, targety)) instanceof Conveyor) {
            previous = (Conveyor) World.getTileAt(new Point(targetx, targety));
        }
       
        if (next == null || next.direction != this.direction) {
            World.registerSink(this);
        }
        if (previous != null && previous.direction == this.direction) {
            World.deregisterSink(previous);
        } else {
            previous = null;
        }
        if (next != null && next.direction == this.direction) {
            next.previous = this;
        }
        if (previous != null && previous.direction == this.direction) {
            previous.next = this;
        }

        World.addTile(this);

        // check left and right
        targetx = location.getX() - 50 * updateVector[1];
        targety = location.getY() - 50 * updateVector[0];
        Structure sideConveyor = World.getTileAt(new Point(targetx, targety));
        if (sideConveyor instanceof Conveyor) {
            Conveyor c = (Conveyor) sideConveyor;
            c.updateNext();
        }

        targetx = location.getX() + 50 * updateVector[1];
        targety = location.getY() + 50 * updateVector[0];
        sideConveyor = World.getTileAt(new Point(targetx, targety));
        if (sideConveyor instanceof Conveyor) {
            Conveyor c = (Conveyor) sideConveyor;
            c.updateNext();
        }
    }

    public void updateNext() {
        int targetx = location.getX() + 50 * updateVector[0];
        int targety = location.getY() + 50 * updateVector[1];
        next = (Conveyor) World.getTileAt(new Point(targetx, targety));
    }

    @Override
    public void onUpdate(Graphics g, ImageObserver ref) {
        // update object positions
        long time = System.currentTimeMillis();
        int cSecondPeriod = (int) (time % 1000);
        cSecondPeriod /= 20;
        switch (direction) {
            case NORTH:
                g.drawImage(ConveyorAssets.north[cSecondPeriod], Camera.remapX(location.getX()), Camera.remapY(location.getY()), Camera.resizedX(50), Camera.resizedY(50), ref);
                break;
            case EAST:
                g.drawImage(ConveyorAssets.east[cSecondPeriod], Camera.remapX(location.getX()), Camera.remapY(location.getY()), Camera.resizedX(50), Camera.resizedY(50), ref);
                break;
            case WEST:
                g.drawImage(ConveyorAssets.west[cSecondPeriod], Camera.remapX(location.getX()), Camera.remapY(location.getY()), Camera.resizedX(50), Camera.resizedY(50), ref);
                break;
            case SOUTH:
                g.drawImage(ConveyorAssets.south[cSecondPeriod], Camera.remapX(location.getX()), Camera.remapY(location.getY()), Camera.resizedX(50),  Camera.resizedY(50), ref);
                break;
            default:
                System.out.println("missed direction no render");
                break;
        }
        
        for (int i =  0; i < objects.size(); i++) {
            GenericGameObject object = objects.get(i);
            boolean northWestLogic = toLocal(object.getCurrentx(), object.getCurrenty())[1]>= 0;
            boolean southEastLogic = toLocal(object.getCurrentx(), object.getCurrenty())[1] >= -25;
            boolean northFree = (this.direction == DIRECTIONS.NORTH || this.direction == DIRECTIONS.WEST  ) && northWestLogic;
            boolean southFree = (this.direction == DIRECTIONS.SOUTH || this.direction == DIRECTIONS.EAST) && southEastLogic;
            
            if (northFree || southFree) { 
                if (next != null) { // if next conveyor exists
                    if (!next.objects.contains(object)) {// if object is not on next yet
                        if (next.canReceive(object)) {
                        	if (next.direction != this.direction) {
                        		
                        		object.updatePosition(object.getCurrentx() + updateVector[0]*25,
                                                    object.getCurrenty() + updateVector[1]*25);
                        	}
                            next.onTake(object);
                        } else {
                        }
                    }
                } 
                // remove the object if the object is outside the conveyor
                
                int cutoff = 0;
                if (southFree) {
                	cutoff = -25;
                }
                if (toLocal(object.getCurrentx(), object.getCurrenty())[1] - object.dimx >= cutoff) {

                    this.onRemove(object);
                }
            }
            // if object is still inside the conveyor
            else {
                // if it does not interferer with another object on the same conveyor
                boolean skipUpdate = false;
            	
                for (GenericGameObject secondObject : objects) {
                	
            		if (( secondObject != object && toLocal(object.getCurrentx(), object.getCurrenty())[1] >= toLocal(secondObject.getCurrentx(), secondObject.getCurrenty())[1] - (object.dimx) &&
        					toLocal(object.getCurrentx(), object.getCurrenty())[1] < toLocal(secondObject.getCurrentx(), secondObject.getCurrenty())[1]) ) {
        				skipUpdate = true;
                	}
	                
                }
            	long deltaTime = time-lastTime;
            	if (!skipUpdate) {
            		
            		object.updatePosition(object.getCurrentx() + updateVector[0]*pixPerSecond*deltaTime/1000.0,
                             object.getCurrenty() + updateVector[1]*pixPerSecond*deltaTime/1000.0);
            	}
            }

            g.drawImage(object.getIcon(), Camera.remapX(object.getCurrentx()), Camera.remapY(object.getCurrenty()),Camera.resizedX(object.getIcon().getWidth()),Camera.resizedY(object.getIcon().getHeight()), ref);
        }
        lastTime = time;
        if (previous != null) {
            previous.onUpdate(g, ref);
        }
        
    }

    @Override
    void onDelete() {

    }

    public double[] toLocal(double d, double e) {
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
                return new double[]{-dy, -( d - (location.getX() + xlen) )};
            case SOUTH:
                return new double[]{-(d - (location.getX() + xlen)), -(e2 - (location.getY() + ylen))};
            case WEST:
                return new double[]{(e2 - (location.getY() - ylen)), dx};
            default:
                System.out.println("[-] error bad direction: " + direction + " sleeping 10 seconds");
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
        }
        return new double[]{0, 0};
    }

    @Override
    public void onTake(GenericGameObject object) {
        objects.add(object);
    }

    public void onRemove (GenericGameObject object) {
        objects.remove(object);
    }

    @Override
    public boolean canReceive(GenericGameObject object) {
        double[] coordinates = toLocal(object.getCurrentx(), object.getCurrenty());
        for (GenericGameObject storedObject : objects) {
            if (!(toLocal(storedObject.getCurrentx(), storedObject.getCurrenty())[1] > coordinates[1] + object.dimx ||
                    toLocal(storedObject.getCurrentx(), storedObject.getCurrenty())[1] + storedObject.dimx < coordinates[1])) {
                return false;
            }
        }
        return true;
    }
    
    public void displayGUI(Graphics g, ImageObserver ref, int xWindowSize, int yWindowSize) {
		Graphics2D g2 = (Graphics2D) g;
		g.setColor(new Color(100, 100, 100));
		g.fillRect(xWindowSize-200, 0, xWindowSize, 500);
		g.setColor(new Color(255,255,100));
		g2.drawString("Regular Conveyor Belt", xWindowSize-190, 20);
		g2.drawString(this.toString(), xWindowSize-190, 40);
		g.setColor(new Color(255,255,255));
		g2.drawString("Direction: "+this.direction,xWindowSize-190, 70);
		g2.drawString("Cordinates: "+location.toString(),xWindowSize-170,180);
		if(this.previous == null) {
			g.setColor(new Color(255,0,0));
			g2.drawString("Previous Reference: nullref",xWindowSize-170 , 90);
		}
		else {
			g.setColor(new Color(0,255,0));
			g2.drawString("Previous Reference: ",xWindowSize-170 , 90);
			g2.drawString(this.previous.toString(), xWindowSize-190, 120);
		}
		
		if(this.next  == null) {
			g.setColor(new Color(255,0,0));
			g2.drawString("Next Reference: nullref",xWindowSize-170 , 140);
		}
		else {
			g.setColor(new Color(0,255,0));
			g2.drawString("Next Reference: ",xWindowSize-170 , 140);
			g2.drawString(this.next.toString(), xWindowSize-190, 160);
		}
		
		g.setColor(Color.WHITE);
		g2.drawString("Objects: "+objects.size(), xWindowSize-170, 200);
	}
    
    
}
