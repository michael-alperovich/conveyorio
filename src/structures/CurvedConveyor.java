package structures;

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
		long cTime = System.currentTimeMillis();
		Graphics2D g2 = (Graphics2D)g;
		long time = System.currentTimeMillis() % 500;
		time /= 5;
		g2.drawImage(CurvedConveyorAssets.getPhase(direction, clockwise,(int)time ),Camera.remapX(location.getX()) , Camera.remapY(location.getY()), Camera.resizedX(50), Camera.resizedY(50), ref);
		// 47
		//for(int obj =0; obj < )
		for(int idx = 0; idx < outerSection.size();idx+= 0) {
			PairAngle thetaProgress = outerSection.get(idx);
			if (thetaProgress.angl == 90) {
				if (next.canReceive(thetaProgress.ggo, this)) {
					next.onTake(outerSection.get(idx).ggo, this);
					outerSection.remove(idx);
				}
				else {
					idx++;
				}
			}
			else {
				if (idx == 0) {
					thetaProgress.angl = (int) Math.min(90, thetaProgress.angl + (cTime/1000.0)*(45));
				}
				else {
					thetaProgress.angl = (int) Math.min(outerSection.get(idx-1).angl + outerTheta, thetaProgress.angl + (cTime/1000.0)*(45));
					updateNewCords(thetaProgress);
				}
				
				idx ++;
			}
			switch(backDirection) {
				case NORTH:
					
					if (clockwise) {
						int[] gcords = rotate(location.getX() + 50, location.getY() + 50,35,180-thetaProgress.angl);
						g2.drawImage(thetaProgress.ggo.getIcon(), Camera.remapX(gcords[0]), Camera.remapY(gcords[1]),Camera.resizedX(25),Camera.resizedY(25),ref);
					}
					else {
						
					}
					break;
				case EAST:
					
					if (clockwise) {
						int[] gcords = rotate(location.getX() + 0, location.getY() + 50,35,90-thetaProgress.angl);
						g2.drawImage(thetaProgress.ggo.getIcon(), Camera.remapX(gcords[0]), Camera.remapY(gcords[1]),Camera.resizedX(25),Camera.resizedY(25),ref);
					}
					else {
						
					}
					break;
				case WEST:
					
					if (clockwise) {
						int[] gcords = rotate(location.getX() + 50, location.getY() + 0,35,270-thetaProgress.angl);
						g2.drawImage(thetaProgress.ggo.getIcon(), Camera.remapX(gcords[0]), Camera.remapY(gcords[1]),Camera.resizedX(25),Camera.resizedY(25),ref);
					}
					else {
						
					}
					break;
				case SOUTH:
					
					if (clockwise) {
						int[] gcords = rotate(location.getX() + 0, location.getY() + 0,35,-thetaProgress.angl);
						g2.drawImage(thetaProgress.ggo.getIcon(), Camera.remapX(gcords[0]), Camera.remapY(gcords[1]),Camera.resizedX(25),Camera.resizedY(25),ref);
					}
					else {
						
					}
					break;

			
			}
			
		}
		
		
		
		
		
		
	}
	
	private void updateNewCords(PairAngle thetaProgress) {
		// TODO Auto-generated method stub
		
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
	
	public int[] rotate(int ox, int oy, int r,  int theta) {
		return new int[] {(int) (ox + r*Math.cos(Math.toRadians(theta))), (int) (oy + r * Math.sin(Math.toRadians(theta))) };
	}
	
	@Override
	public boolean canReceive(GenericGameObject object, Structure source) {
		double dir = toLocalFixed(object);
		System.out.println(dir +"  "+ clockwise +"  "+ outerSection.size());
		if (clockwise) {
			if (dir == 0) {
				return outerSection.size() == 0 || (outerSection.get(outerSection.size()-1).angl - 0) > outerTheta;
			}
			else {
				// assuem 25
				return false;
			}
		}
		else {
			if (dir == 25) {
				return outerSection.size() == 0 || (outerSection.get(outerSection.size()-1).angl - 0) > outerTheta;
			}
			else {
				// assuem 25
				return false;
			}
		}
		
	}

	@Override
	public void onTake(GenericGameObject object, Structure source) {
		
		
		double dir = toLocalFixed(object);
		System.out.println("onTake: "+dir);
		if (clockwise) {
			if (dir == 0) {
				outerSection.add(new PairAngle(object, 0));
			}
			else {
				// assuem 25
			}
		}
		else {
			if (dir == 25) {
				outerSection.add(new PairAngle(object, 0));
			}
			else {
				// assuem 25
			}
		}
		System.out.println(outerSection.size());
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
}
class PairAngle{
	public GenericGameObject ggo;
	public int angl;
	public PairAngle( GenericGameObject a, int b) {
		angl = b;
		ggo = a;
	}
}
