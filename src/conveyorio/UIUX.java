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
	public static boolean requestFocus = false;
	public static Placeable toPlace = new NullTile(0, 0);
	public static LinkedList<Placeable> options;
	public static int localizedCameraY = -109;
	public static void updateDimensions(int xi, int yi) {
		xWindowSize = xi;
		yWindowSize = yi;
	}
	public static void updateUi(Graphics g, ImageObserver ref) {
		
		if (targetinfo != null) {
			targetinfo.displayGUI(g,ref,xWindowSize,yWindowSize);
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
		
		int maxSize = 300;
		g.setColor(new Color(255,255,255));
		g2.drawString("New Tile Selection", 250, yWindowSize-480);
		for (Placeable option : options) {
			String name = option.getName();
			BufferedImage icon = option.onRender();
			int iconH = 50;
			
			
			int pivotY = currentY-localizedCameraY;
			if (pivotY > 50 && pivotY+iconH < 300) {
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
	}
	public static void scrollSelect(double ticks) {
		localizedCameraY += ticks * 30;
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
}
