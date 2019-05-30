package assets;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import structures.DIRECTIONS;

public class CurvedConveyorAssets {
	public static BufferedImage[] NE = new BufferedImage[90];
	public static BufferedImage[] NW = new BufferedImage[90];
	public static BufferedImage[] SE = new BufferedImage[90];
	public static BufferedImage[] SW = new BufferedImage[90];
	
	public static void loadAssets() throws IOException {
		String preformat = "assets/tiles/curvedconveyor/%s/%sCurved(%s).png";
		for(int i = 0; i < 90;i++) {
			NE[i] = ImageIO.read(ConveyorAssets.class.getResource(String.format(preformat, "NE","NE",i)));
			SE[i] = ImageIO.read(ConveyorAssets.class.getResource(String.format(preformat, "SE","SE",i)));
			NW[i] = ImageIO.read(ConveyorAssets.class.getResource(String.format(preformat, "NW","NW",i)));
			SW[i] = ImageIO.read(ConveyorAssets.class.getResource(String.format(preformat, "SW","SW",i)));
		}
	}
	public static BufferedImage getPhase(DIRECTIONS dir, boolean clockwise,int frame) {
		/*
		 * directions is the source directions!
		 * clockwise is default at true
		 * ccw is false.
		 */
		if (clockwise) {
			frame *= -1;
		}
		frame %= 90;
		frame += 90;
		frame %= 90;
		switch(dir) {
			case NORTH:
				return clockwise ? NE[frame] : NW[frame];
			case SOUTH:
				return clockwise ? SW[frame] : SE[frame];
			case EAST:
				return clockwise ? SE[frame] : NE[frame];
			case WEST:
				return clockwise ? NW[frame] : SW[frame];
			default:
				System.out.println("error bad direction curvedconveyorassets");
				return NE[frame];
			
		}
	}
}