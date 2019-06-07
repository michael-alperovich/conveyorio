package structures;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.ImageObserver;
import java.util.LinkedList;

import assets.CurvedConveyorAssets;
import conveyorio.Camera;
import conveyorio.Point;
import conveyorio.World;
import objects.Coal;
import objects.GenericGameObject;

public class CurvedConveyor extends ConveyorLike {
	private DIRECTIONS direction, trueDirection, backDirection;
	public boolean clockwise;
	public LinkedList<PairAngle> outerSection = new LinkedList<PairAngle>();
	public LinkedList<PairAngle> innerSection = new LinkedList<PairAngle>();
	public ConveyorLike previous, next;
	private int[] updateVector, backUpdateVector;
	private final int outerTheta = 30;
    public long lastTime = System.currentTimeMillis();

	public CurvedConveyor(Point loc, DIRECTIONS d,boolean cw) {
		super(loc,50,50);
		direction = d;
		clockwise = cw;
		lastTime = System.currentTimeMillis();
		switch (direction) {
			case NORTH:
				this.trueDirection = DIRECTIONS.SOUTH;
				break;
			case EAST:
				this.trueDirection = DIRECTIONS.WEST;
				break;
			case SOUTH:
				this.trueDirection = DIRECTIONS.NORTH;
				break;
			case WEST:
				this.trueDirection = DIRECTIONS.EAST;
				break;
		}
		switch (direction) {
			case NORTH:
				if (clockwise) {
					backDirection = DIRECTIONS.EAST;
				}
				else {
					backDirection = DIRECTIONS.WEST;
				}
				break;
			case EAST:
				if (clockwise) {
					backDirection = DIRECTIONS.NORTH;
				}
				else {
					backDirection = DIRECTIONS.SOUTH;
				}
				break;
			case SOUTH:
				if (clockwise) {
					backDirection = DIRECTIONS.WEST;
				}
				else {
					backDirection = DIRECTIONS.EAST;
				}
				break;
			case WEST:
				if (clockwise) {
					backDirection = DIRECTIONS.SOUTH;
				}
				else {
					backDirection = DIRECTIONS.NORTH;
				}
				break;
		}
		updateVector = Conveyor.toVector(trueDirection);
		backUpdateVector = Conveyor.toVector(backDirection);
	}
	public String getCode() {
    	return String.format("CurvedConveyor %s %s %s %s", location.getX(),location.getY(),direction, clockwise ? "y": "n");
    }
	private void updateReference() {
		int targetx = location.getX() + 50 * updateVector[0];
		int targety = location.getY() + 50 * updateVector[1];
		if (World.getTileAt(new Point(targetx, targety)) instanceof ConveyorLike) {
			next = (ConveyorLike) World.getTileAt(new Point(targetx, targety));
		} else {
			next = null;
		}
		targetx = location.getX() - 50 * backUpdateVector[0];
		targety = location.getY() - 50 * backUpdateVector[1];

		if (World.getTileAt(new Point(targetx, targety)) instanceof ConveyorLike) {
			previous = (ConveyorLike) World.getTileAt(new Point(targetx, targety));
		} else {
			previous = null;
		}
	}
	
	@Override
	public void onUpdate(Graphics g, ImageObserver ref) {
		updateReference();
		int maxAngle = 70;
		long cTime = System.currentTimeMillis()-lastTime;
		lastTime = System.currentTimeMillis();
		Graphics2D g2 = (Graphics2D)g;
		long time = System.currentTimeMillis() % 500;
		time /= 5;
		g2.drawImage(CurvedConveyorAssets.getPhase(direction, clockwise,(int)time ),Camera.remapX(location.getX()) , Camera.remapY(location.getY()), Camera.resizedX(50), Camera.resizedY(50), ref);
		// 47
		//for(int obj =0; obj < )
		for(int idx = 0; idx < outerSection.size();idx+= 0) {
			PairAngle thetaProgress = outerSection.get(idx);
			if (thetaProgress.angl == maxAngle) {
				if (next != null && next.canReceive(thetaProgress.ggo, this)) {
					next.onTake(outerSection.get(idx).ggo, this);
					outerSection.remove(idx);
				}
				else {
					idx++;
				}
			}
			else {
				if (idx == 0) {
					thetaProgress.angl = (double) Math.min(maxAngle, thetaProgress.angl + (cTime/1000.0)*(135));
				}
				else {
					thetaProgress.angl = (double) Math.min(outerSection.get(idx-1).angl - outerTheta, thetaProgress.angl + (cTime/1000.0)*(135));
				}
				
				idx ++;
			}
			switch(backDirection) {
				case NORTH:
					
					if (clockwise) {
						int[] gcords = rotate(location.getX() + 0, location.getY() + 0,40,-thetaProgress.angl);
						thetaProgress.ggo.updatePosition(gcords[0], gcords[1]);
						g2.drawImage(thetaProgress.ggo.getIcon(), Camera.remapX(thetaProgress.ggo.getCurrentx()), Camera.remapY(thetaProgress.ggo.getCurrenty()),Camera.resizedX(25),Camera.resizedY(25),ref);
					}
					else {
						int[] gcords = rotate(location.getX() + 50, location.getY() + 0,30,180 + thetaProgress.angl);
						thetaProgress.ggo.updatePosition(gcords[0], gcords[1]);
						g2.drawImage(thetaProgress.ggo.getIcon(), Camera.remapX(thetaProgress.ggo.getCurrentx()), Camera.remapY(thetaProgress.ggo.getCurrenty()),Camera.resizedX(25),Camera.resizedY(25),ref);
					
					}
					break;
				case EAST:
					
					if (clockwise) {
						int[] gcords = rotate(location.getX() + 0, location.getY() + 50,40,90-thetaProgress.angl);
						thetaProgress.ggo.updatePosition(gcords[0], gcords[1]);
						g2.drawImage(thetaProgress.ggo.getIcon(), Camera.remapX(thetaProgress.ggo.getCurrentx()), Camera.remapY(thetaProgress.ggo.getCurrenty()),Camera.resizedX(25),Camera.resizedY(25),ref);
					}
					else {
						int[] gcords = rotate(location.getX() + 0, location.getY() + 0,30,270 + thetaProgress.angl);
						thetaProgress.ggo.updatePosition(gcords[0], gcords[1]);
						g2.drawImage(thetaProgress.ggo.getIcon(), Camera.remapX(thetaProgress.ggo.getCurrentx()), Camera.remapY(thetaProgress.ggo.getCurrenty()),Camera.resizedX(25),Camera.resizedY(25),ref);
					
					}
					break;
				case WEST:
					
					if (clockwise) {
						int[] gcords = rotate(location.getX() + 50, location.getY() + 0,20,270-thetaProgress.angl);
						thetaProgress.ggo.updatePosition(gcords[0], gcords[1]);
						g2.drawImage(thetaProgress.ggo.getIcon(), Camera.remapX(thetaProgress.ggo.getCurrentx()), Camera.remapY(thetaProgress.ggo.getCurrenty()),Camera.resizedX(25),Camera.resizedY(25),ref);
					}
					else {
						int[] gcords = rotate(location.getX() + 50, location.getY() + 50,30,90 + thetaProgress.angl);
						thetaProgress.ggo.updatePosition(gcords[0], gcords[1]);
						g2.drawImage(thetaProgress.ggo.getIcon(), Camera.remapX(thetaProgress.ggo.getCurrentx()), Camera.remapY(thetaProgress.ggo.getCurrenty()),Camera.resizedX(25),Camera.resizedY(25),ref);
					
					}
					break;
				case SOUTH:
					
					if (clockwise) {
						int[] gcords = rotate(location.getX() + 50, location.getY() + 50,40,180-thetaProgress.angl);
						thetaProgress.ggo.updatePosition(gcords[0], gcords[1]);
						g2.drawImage(thetaProgress.ggo.getIcon(), Camera.remapX(thetaProgress.ggo.getCurrentx()), Camera.remapY(thetaProgress.ggo.getCurrenty()),Camera.resizedX(25),Camera.resizedY(25),ref);
					}
					else {
						int[] gcords = rotate(location.getX() + 0, location.getY() + 50,30, thetaProgress.angl);
						thetaProgress.ggo.updatePosition(gcords[0], gcords[1]);
						g2.drawImage(thetaProgress.ggo.getIcon(), Camera.remapX(thetaProgress.ggo.getCurrentx()), Camera.remapY(thetaProgress.ggo.getCurrenty()),Camera.resizedX(25),Camera.resizedY(25),ref);
					
					}
					break;

			
			}
			
		}
		
		
		
		
		
		
	}
	
	

	private double toLocalFixed(GenericGameObject object) {
        if (this.backDirection == DIRECTIONS.NORTH || this.backDirection == DIRECTIONS.SOUTH) {
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
            if (object.getCurrenty() >= 0) {
                targetY2 = (int) (object.getCurrenty() / 25) * 25 + 25;
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
	
	public int[] rotate(int ox, int oy, int r,  double d) {
		return new int[] {(int) (ox - 25 + r*Math.cos(Math.toRadians(d))), (int) (oy - 25 - r * Math.sin(Math.toRadians(d))) };
	}
	
	@Override
	public boolean canReceive(GenericGameObject object, Structure source) {
		double dir = toLocalFixed(object);
		if (clockwise) {
			return outerSection.size() == 0 || (outerSection.get(outerSection.size() - 1).angl) > outerTheta;
			
		}
		else {
			return outerSection.size() == 0 || (outerSection.get(outerSection.size()-1).angl - 0) > outerTheta;
			
		}
		
	}

	@Override
	public void onTake(GenericGameObject object, Structure source) {
		
		
		double dir = toLocalFixed(object);
		if (clockwise) {
				outerSection.add(new PairAngle(object, 0));
			
		}
		else {
			
				outerSection.add(new PairAngle(object, 0));
			
		}
	}



	@Override
	public DIRECTIONS getDirection(boolean asNext) {
		if (asNext) {
			return trueDirection;
		}
		else {
			return backDirection;
		}
	}

	@Override
	public void onRemove(GenericGameObject g) {
		// TODO Auto-generated method stub
		
	}
	public void displayGUI(Graphics g, ImageObserver ref, int xWindowSize, int yWindowSize) {
        Graphics2D g2 = (Graphics2D) g;
        g.setColor(new Color(100, 100, 100));
        g.fillRect(xWindowSize - 200, 0, xWindowSize, 500);
        g.setColor(new Color(255, 255, 100));
        g2.drawString("Curved Conveyor Belt", xWindowSize - 190, 20);
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
        g.setColor(new Color(255, 255, 0));
        g2.drawString("BackDirection: " + this.backDirection, xWindowSize - 190, 200);
        g.setColor(new Color(255, 255, 0));
        g2.drawString("FrontDirection: " + this.trueDirection, xWindowSize - 190, 240);
        g2.drawString("Clockwise: "+clockwise, xWindowSize-190, 260);
        
    }
	
}
class PairAngle{
	public GenericGameObject ggo;
	public double angl;
	public PairAngle( GenericGameObject a, double b) {
		angl = b;
		ggo = a;
	}
}
