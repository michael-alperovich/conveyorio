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
	public int fuel = 0;
	public LinkedList<Mineral> orelist = new LinkedList<Mineral>();	
	public long lastTime = System.currentTimeMillis();
	public long timeToSmelt = 10 * 1000;
	
	public Furnace(Point loc) {
		super(loc, 50,50);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onUpdate(Graphics g, ImageObserver ref) {
		// TODO Auto-generated method stub
		Graphics2D g2 = (Graphics2D)g;
		long s = System.currentTimeMillis()-lastTime;
		if (orelist.size() > 0) {
			g2.drawImage(FurnaceAssets.getFire(), Camera.remapX(location.getX()), Camera.remapY(location.getY()), Camera.resizedX(50), Camera.resizedY(50), ref);
			if (fuel > 0) {
				timeToSmelt -= s;
				fuel = (int) Math.max(0, fuel - s);
				
			}
			if (timeToSmelt < 0) {
				orelist.get(0).makeNotOre();
				objects.add(orelist.removeFirst());
				timeToSmelt = 10 * 1000;
			}
		}
		else {
			g2.drawImage(FurnaceAssets.emptyFurance(), Camera.remapX(location.getX()), Camera.remapY(location.getY()), Camera.resizedX(50), Camera.resizedY(50), ref);
			
		}
		
	}

	@Override
	boolean canReceive(GenericGameObject object) {
		// TODO Auto-generated method stub
		return object instanceof Mineral && orelist.size() < 4;
	}

	@Override
	void onTake(GenericGameObject g, Structure source) {
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
	void onRemove(GenericGameObject g) {
		// TODO Auto-generated method stub
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
		g2.drawString("Objects: "+objects.size(), xWindowSize-170, 200);
		g.setColor(new Color(135,206,250));
		int cy = 220;
		for (GenericGameObject o : orelist) {
			g2.drawString(o.toString() , xWindowSize-190, cy);
			cy += 20;
			g2.drawString(o.getCurrentx()+" "+o.getCurrenty(), xWindowSize-190,cy);
			cy += 20;
		}
	}

}
