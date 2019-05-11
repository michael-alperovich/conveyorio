package conveyorio;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.LinkedList;

import objects.ConveyorTile;
import objects.NullTile;
import properties.Placeable;
import structures.Structure;

public class UIUX {
	public static Structure targetinfo;
	public static int xWindowSize;
	public static int yWindowSize;
	public static boolean showLog;
	
	public static boolean requestFocus = false;
	public static Placeable toPlace = new NullTile();
	public static Point targetloc = new Point(-1,-1);
	
	public static LinkedList<Placeable> options;
	public static int localizedCameraY = -50;
	
	
	public static void updateDimensions(int xi, int yi) {
		xWindowSize = xi;
		yWindowSize = yi;
	}
	public static void tryPlace() {
		if (!World.world.containsKey(targetloc) && !(toPlace instanceof NullTile)) {
			Structure x = toPlace.onPlace(targetloc);
			
		}
	}
	public static void tryRemove() {
		
		if(World.world.containsKey(targetloc)) {
			World.getTileAt(targetloc).onDelete();
		}
	}
	public static void toggleLog() {
		showLog = !showLog;
	}
	public static void updateUi(Graphics g, ImageObserver ref) {
		
		if (targetinfo != null) {
			targetinfo.displayGUI(g,ref,xWindowSize,yWindowSize);
			if(showLog) {
				targetinfo.displayLog(g,30, 25);
			}
		}
		
		BufferedImage demoIcon = toPlace.onRender();
		if (!World.world.containsKey(targetloc) && !(toPlace instanceof NullTile)) {
			Graphics2D g2  = (Graphics2D) g;
			g2.drawImage(demoIcon,Camera.remapX(targetloc.getX()),Camera.remapY(targetloc.getY()), Camera.resizedX(UIUX.toPlace.dimx), Camera.resizedY(UIUX.toPlace.dimy) ,ref);
		}
				
		
		renderSelected(g,ref);
	}
	
	public static void renderSelectionGui(Graphics g, ImageObserver ref) {
		/*
		 * allocate 0 to  (xWindowSize - 200) in X axis.
		 * allocate 0 to  (yWindowSize - 200)
		 */
		Graphics2D g2 = (Graphics2D) g;
		g.setColor(new Color(122, 122, 122));
		g.fillRect(200, yWindowSize-500, xWindowSize-500, 300);
		int currentY = 0;
		
		g.setColor(new Color(255,255,255));
		g2.drawString("New Tile Selection", 250, yWindowSize-480);
		for (Placeable option : options) {
			String name = option.getName();
			BufferedImage icon = option.onRender();
			int iconH = 50;
			
			
			int pivotY = currentY-localizedCameraY;
			if (pivotY >= 50 && pivotY+iconH <= 300) {
				// within bounds.
				g.setColor(new Color(255,255,0));
				g2.drawString(name,220,pivotY + yWindowSize-500);
				g2.drawImage(icon,400,pivotY + yWindowSize - 500,50,50, ref);
				
			}
			currentY += 50;
			
			
			
			
		}
		
		
		g.setColor(Color.WHITE);
		
		
		
	}
	public static void renderSelected(Graphics g, ImageObserver ref) {
		g.setColor(new Color(155,155,155));
		g.fillRect(xWindowSize/2 - 200, yWindowSize-100, 400, 100);
		Graphics2D g2 = (Graphics2D) g;
		g.setColor(new Color(255,255,0));
		g2.drawString("In hand: ", xWindowSize/2-195, yWindowSize-50);
		BufferedImage icon = toPlace.onRender();
		String name = toPlace.getName();
		if (icon != null) {
			g2.drawImage(icon,xWindowSize/2-195 + 50, yWindowSize - 70, 50, 50, ref);
			g2.drawString(name, xWindowSize/2-195 + 50, yWindowSize-10);
		}
		
	}
	public static void scrollSelect(double ticks) {
		localizedCameraY += ticks * 30;
		localizedCameraY = (int) Camera.clip(localizedCameraY,-50,-50);
	}
	public static void keyPressed() {
		
	}
	public static void openSelection() { // entry point for gui
		requestFocus = true; 
		options = new LinkedList<Placeable>();
		options.add(new NullTile());
		options.add(new ConveyorTile());
	}
	public static void escape() {
		requestFocus = false;
	}
	public static void mouseClicked(int x, int y) {
		x -= Camera.offsetX;
		y -= Camera.offsetY;
		
		if ((y <= yWindowSize -500 + 40) || y >= yWindowSize-500+300) {return;} // too high.
		
		y -= yWindowSize - 500;
		if (x >= 200 && x <= xWindowSize-300) {
			// x value is inbounds.
			int delocalizedY = y + localizedCameraY;
			//System.out.println(delocalizedY);
			int index = (delocalizedY + 10)/50;
			if (index >= 0 && index < options.size()) {
				toPlace = options.get(index);
				
			}
		}
		
	}
	public static void setTargetPlacement(Point point) {
		targetloc = point;
		
	}
}
