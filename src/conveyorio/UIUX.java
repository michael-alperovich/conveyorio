package conveyorio;

import java.awt.Graphics;
import java.awt.image.ImageObserver;

import structures.Structure;

public class UIUX {
	public static Structure targetinfo;
	public static int xlen;
	public static int ylen;
	public static boolean requestFocus = false;
	
	public static void updateDimensions(int xi, int yi) {
		xlen = xi;
		ylen = yi;
	}
	public static void updateUi(Graphics g, ImageObserver ref) {
		
		if (targetinfo != null) {
			targetinfo.displayGUI(g,ref,xlen,ylen);
		}
	}
	public static void openSelectionGui(Graphics g) {
		
	}
	public static void keyPressed() {
		
	}
	public static void openSelection() {
		requestFocus = true;
	}
	public static void escape() {
		requestFocus = false;
	}
}
