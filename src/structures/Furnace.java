package structures;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.ImageObserver;
import java.util.LinkedList;

import assets.FurnaceAssets;
import conveyorio.Camera;
import conveyorio.Point;
import objects.GenericGameObject;
import objects.Mineral;
import properties.Fuel;

public class Furnace extends Structure{
	public final static int smeltingTime = 10000;
	public int fuel = 0;
	public LinkedList<Mineral> orelist = new LinkedList<Mineral>();	
	public long lastTime = System.currentTimeMillis();
	public long timeToSmelt = smeltingTime;
	
	public Furnace(Point loc) {
		super(loc, 50,50);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onUpdate(Graphics g, ImageObserver ref) {
		// TODO Auto-generated method stub
		Graphics2D g2 = (Graphics2D)g;
		long s = System.currentTimeMillis()-lastTime;
		if (orelist.size() > 0 && fuel > 0) {
			g2.drawImage(FurnaceAssets.getFire(), Camera.remapX(location.getX()), Camera.remapY(location.getY()), Camera.resizedX(50), Camera.resizedY(50), ref);
			if (fuel > 0) {
				if (s > timeToSmelt) {
					fuel = (int) Math.max(0, fuel - timeToSmelt);	
					
					timeToSmelt = 0;
				}
				else {
					timeToSmelt -= s;
					fuel = (int) Math.max(0, fuel - s);	
				}
							
			}
			if (timeToSmelt <= 0) {
				orelist.get(0).makeNotOre();
				objects.add(orelist.removeFirst());
				timeToSmelt = smeltingTime;
			}
		}
		else {
			g2.drawImage(FurnaceAssets.emptyFurance(), Camera.remapX(location.getX()), Camera.remapY(location.getY()), Camera.resizedX(50), Camera.resizedY(50), ref);
		}
		lastTime = System.currentTimeMillis();
		
	}

	@Override
	public boolean canReceive(GenericGameObject object, Structure s) {
		// TODO Auto-generated method stub
		return (object instanceof Mineral &&  orelist.size() < 4 ) || (object instanceof Fuel && fuel < 5* smeltingTime) ;
	}

	@Override
	public void onTake(GenericGameObject g, Structure source) {
		// TODO Auto-generated method stub
		if (g instanceof Fuel) {
			fuel += ((Fuel)g).getFuelValue();
		}
		else if (g instanceof Mineral) {
			if (((Mineral)g).isOre()){
				orelist.add((Mineral) g);
			}
			else {
				objects.add(g);
			}
		}
		else {
			objects.add(g);
		}
	}

	@Override
	public void onRemove(GenericGameObject g) {
		objects.remove(g);
		
	}
	
	public void displayGUI(Graphics g, ImageObserver ref, int xWindowSize, int yWindowSize) {
    	Graphics2D g2 = (Graphics2D) g;
		g.setColor(new Color(100, 100, 100));
		g.fillRect(xWindowSize-200, 0, xWindowSize, 500);
		g.setColor(new Color(255,255,100));
		g2.drawString("Regular Furnace", xWindowSize-190, 20);
		g2.drawString(this.toString(), xWindowSize-190, 40);
		g.setColor(new Color(255,255,255));
		g2.drawString("Cordinates: "+location.toString(),xWindowSize-170,180);
		g2.drawString("Fuel: "+fuel,xWindowSize-190,200);
		double progress = (smeltingTime-timeToSmelt)/(double)smeltingTime;
		int dist = (int) (150*progress);
		g.setColor(Color.BLACK);
		g2.fillRect(xWindowSize-190, 220, 150, 20);
		g.setColor(Color.GREEN);
		g2.fillRect(xWindowSize-190, 220, dist, 20);
		g.setColor(Color.WHITE);
		g2.drawString("Queue: "+objects.size(), xWindowSize-170, 260);
		g.setColor(new Color(135,206,250));
		int cy = 280;
		for (GenericGameObject o : orelist) {
			g2.drawString(o.toString() , xWindowSize-190, cy);
			cy += 20;
			g2.drawString(o.getCurrentx()+" "+o.getCurrenty(), xWindowSize-190,cy);
			cy += 20;
		}
	}

}
