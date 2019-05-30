package structures;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.ImageObserver;

import assets.ConveyorAssets;
import conveyorio.Camera;
import conveyorio.Point;
import conveyorio.UIUX;
import conveyorio.World;
import objects.GenericGameObject;

public class Conveyor extends Structure {

    private DIRECTIONS direction;
    public Conveyor previous, next;

    private int[] updateVector;
    public long lastTime = System.currentTimeMillis();
    public double pixPerSecond = 100;
    public final int LENGTH = 50;

    public Conveyor(Point loc, DIRECTIONS d) {
        super(loc, 50, 50);
        direction = d;
        updateVector = Conveyor.toVector(direction);


    }

    private void updateReference() {
        int targetx = location.getX() + 50 * updateVector[0];
        int targety = location.getY() + 50 * updateVector[1];
        if (World.getTileAt(new Point(targetx, targety)) instanceof Conveyor) {
            next = (Conveyor) World.getTileAt(new Point(targetx, targety));
        } else {
            next = null;
        }
        targetx = location.getX() - 50 * updateVector[0];
        targety = location.getY() - 50 * updateVector[1];

        if (World.getTileAt(new Point(targetx, targety)) instanceof Conveyor) {
            previous = (Conveyor) World.getTileAt(new Point(targetx, targety));
        } else {
            previous = null;
        }

        if (next == null || next.direction != this.direction) {
            World.registerSink(this);
        }
        if (previous != null && previous.direction == this.direction && World.conveyorSources.contains(previous.location)) {
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
    }

    @Override
    public void onUpdate(Graphics g, ImageObserver ref) {
        // update object positions
        updateReference();
        if (next == null) {
            voided.clear();
        }
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
                g.drawImage(ConveyorAssets.south[cSecondPeriod], Camera.remapX(location.getX()), Camera.remapY(location.getY()), Camera.resizedX(50), Camera.resizedY(50), ref);
                break;
            default:
                System.out.println("missed direction no render");
                break;
        }

        for (int i = 0; i < objects.size(); i++) {
            GenericGameObject object = objects.get(i);
            fixObjectPosition(object);
            boolean northWestLogic = toLocal(object.getCurrentx(), object.getCurrenty())[1] >= 0;
            boolean southEastLogic = toLocal(object.getCurrentx(), object.getCurrenty())[1] >= -25;
            boolean northFree = (this.direction == DIRECTIONS.NORTH || this.direction == DIRECTIONS.WEST) && northWestLogic;
            boolean southFree = (this.direction == DIRECTIONS.SOUTH || this.direction == DIRECTIONS.EAST) && southEastLogic;

            if (northFree || southFree) {
                if (next != null) { // if next conveyor exists
                    if (!next.objects.contains(object) && !voided.contains(object)) {// if object is not on next yet
                        if (next.canReceive(object, this)) {
                            if (next.direction != this.direction) {

                                object.updatePosition(object.getCurrentx() + updateVector[0] * 25,
                                        object.getCurrenty() + updateVector[1] * 25);
                            }
                            next.onTake(object, this);
                            voided.add(object);
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

                    if ((secondObject != object && toLocal(object.getCurrentx(), object.getCurrenty())[1] >= toLocal(secondObject.getCurrentx(), secondObject.getCurrenty())[1] - (object.dimx) &&
                            toLocal(object.getCurrentx(), object.getCurrenty())[1] < toLocal(secondObject.getCurrentx(), secondObject.getCurrenty())[1])) {
                        if ((Math.abs(toLocalFixed(secondObject) - toLocalFixed(object)) < 10E-5)) {
                            skipUpdate = true;
                        }
                    }

                }
                long deltaTime = time - lastTime;
                if (!skipUpdate) {

                    object.updatePosition(object.getCurrentx() + updateVector[0] * pixPerSecond * deltaTime / 1000.0,
                            object.getCurrenty() + updateVector[1] * pixPerSecond * deltaTime / 1000.0);
                }
            }

            g.drawImage(object.getIcon(), Camera.remapX(object.getCurrentx()), Camera.remapY(object.getCurrenty()), Camera.resizedX(object.getIcon().getWidth()), Camera.resizedY(object.getIcon().getHeight()), ref);
        }
        lastTime = time;
        if (previous != null) {
            previous.onUpdate(g, ref);
        }

    }

    private void fixObjectPosition(GenericGameObject object) {
        if (this.direction == DIRECTIONS.NORTH || this.direction == DIRECTIONS.SOUTH) {
            double targetX1 = (int) (object.getCurrentx() / 25) * 25;
            double targetX2;
            if (object.getCurrentx() <= 0) {
                targetX2 = (int) (object.getCurrentx() / 25) * 25 - 25;
            } else {
                targetX2 = (int) (object.getCurrentx() / 25) * 25 + 25;
            }
            if (Math.abs(targetX1 - object.getCurrentx()) < Math.abs(targetX2 - object.getCurrentx())) {
                object.updatePosition(targetX1, object.getCurrenty());
            } else {
                object.updatePosition(targetX2, object.getCurrenty());
            }
        } else {
            double targetY1 = (int) (object.getCurrenty() / 25) * 25;
            double targetY2;
            if (object.getCurrenty() >= 0) {
                targetY2 = (int) (object.getCurrenty() / 25) * 25 + 25;
            } else {
                targetY2 = (int) (object.getCurrenty() / 25) * 25 - 25;
            }

            if (Math.abs(targetY1 - object.getCurrenty()) < Math.abs(targetY2 - object.getCurrenty())) {
                object.updatePosition(object.getCurrentx(), targetY1);
            } else {
                object.updatePosition(object.getCurrentx(), targetY2);
            }
        }
    }

    private double toLocalFixed(GenericGameObject object) {
        if (this.direction == DIRECTIONS.NORTH || this.direction == DIRECTIONS.SOUTH) {
            double targetX1 = (int) (object.getCurrentx() / 25) * 25;
            double targetX2;
            if (object.getCurrentx() >= 0) {
                targetX2 = (int) (object.getCurrentx() / 25) * 25 + 25;
            } else {
                targetX2 = (int) (object.getCurrentx() / 25) * 25 - 25;
            }
            if (Math.abs(targetX1 - object.getCurrentx()) < Math.abs(targetX2 - object.getCurrentx())) {
                return targetX1 - this.location.getX();
            } else {
                return targetX2 - this.location.getX();
            }
        } else {

            double targetY1 = (int) (object.getCurrenty() / 25) * 25;
            double targetY2;
            if (object.getCurrentx() >= 0) {
                targetY2 = (int) (object.getCurrenty() / 25) * 25 - 25;
            } else {
                targetY2 = (int) (object.getCurrenty() / 25) * 25 - 25;
            }

            if (Math.abs(targetY1 - object.getCurrenty()) < Math.abs(targetY2 - object.getCurrenty())) {
                return targetY1 - this.location.getY();
            } else {
                return targetY2 - this.location.getY();
            }
        }
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
                return new double[]{-dy, -(d - (location.getX() + xlen))};
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
    public void onTake(GenericGameObject object, Structure source) {
        if (UIUX.targetinfo == this && UIUX.showLog) {
            System.out.println("");
        }
        debug("adding: " + object + " from " + source);
        objects.add(object);
    }

    public void onRemove(GenericGameObject object) {
        debug("removing: " + object + " from " + objects.size() + " objects.");
        int i = 0;
        while (i < objects.size()) {
            if (objects.get(i) == object) {
                objects.remove(i);
            } else {
                i++;
            }
        }
        voided.remove(object);
        debug("removed object:  " + object + " new size is: " + objects.size());
    }

    @Override
    public boolean canReceive(GenericGameObject object, Structure source) {
        double[] coordinates = toLocal(object.getCurrentx(), object.getCurrenty());
        if (!(source instanceof Conveyor)) {
            double fixedPosition = toLocalFixed(object);
            if (fixedPosition < 0 || fixedPosition > 25) {
                return false;
            }
            fixObjectPosition(object);
        }
        for (GenericGameObject storedObject : objects) {
            if (!(toLocal(storedObject.getCurrentx(), storedObject.getCurrenty())[1] >= coordinates[1] + object.dimx ||
                    toLocal(storedObject.getCurrentx(), storedObject.getCurrenty())[1] + storedObject.dimx <= coordinates[1])) {
                if (!(toLocal(storedObject.getCurrentx(), storedObject.getCurrenty())[0] >= coordinates[0] + object.dimx ||
                        toLocal(storedObject.getCurrentx(), storedObject.getCurrenty())[0] + storedObject.dimx <= coordinates[0])) {
                    return false;
                }
            }
        }
        return true;
    }

    public void displayGUI(Graphics g, ImageObserver ref, int xWindowSize, int yWindowSize) {
        Graphics2D g2 = (Graphics2D) g;
        g.setColor(new Color(100, 100, 100));
        g.fillRect(xWindowSize - 200, 0, xWindowSize, 500);
        g.setColor(new Color(255, 255, 100));
        g2.drawString("Regular Conveyor Belt", xWindowSize - 190, 20);
        g2.drawString(this.toString(), xWindowSize - 190, 40);
        g.setColor(new Color(255, 255, 255));
        g2.drawString("Direction: " + this.direction, xWindowSize - 190, 70);
        g2.drawString("Cordinates: " + location.toString(), xWindowSize - 170, 180);
        if (this.previous == null) {
            g.setColor(new Color(255, 0, 0));
            g2.drawString("Previous Reference: nullref", xWindowSize - 170, 90);
        } else {
            g.setColor(new Color(0, 255, 0));
            g2.drawString("Previous Reference: ", xWindowSize - 170, 90);
            g2.drawString(this.previous.toString(), xWindowSize - 190, 120);
        }

        if (this.next == null) {
            g.setColor(new Color(255, 0, 0));
            g2.drawString("Next Reference: nullref", xWindowSize - 170, 140);
        } else {
            g.setColor(new Color(0, 255, 0));
            g2.drawString("Next Reference: ", xWindowSize - 170, 140);
            g2.drawString(this.next.toString(), xWindowSize - 190, 160);
        }

        g.setColor(Color.WHITE);
        g2.drawString("Objects: " + objects.size(), xWindowSize - 170, 200);
        g.setColor(new Color(135, 206, 250));
        int cy = 220;
        for (GenericGameObject o : objects) {
            g2.drawString(o.toString(), xWindowSize - 190, cy);
            cy += 20;
            g2.drawString(o.getCurrentx() + " " + o.getCurrenty(), xWindowSize - 190, cy);
            cy += 20;
        }
    }


}
