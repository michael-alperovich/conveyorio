package assets;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import structures.DIRECTIONS;

public class ChestAssets {
	public static BufferedImage[] chestassets = new BufferedImage[4];
	public static void loadAssets() throws IOException{
		chestassets[0] = ImageIO.read(ChestAssets.class.getResource("assets\\tiles\\chest\\regular\\chest_north.png"));
		chestassets[1] = ImageIO.read(ChestAssets.class.getResource("assets\\tiles\\chest\\regular\\chest_east.png"));
		chestassets[2] = ImageIO.read(ChestAssets.class.getResource("assets\\tiles\\chest\\regular\\chest_west.png"));
		chestassets[3] = ImageIO.read(ChestAssets.class.getResource("assets\\tiles\\chest\\regular\\chest_south.png"));
	}
	public static BufferedImage getOrientation(DIRECTIONS dir) {
		switch(dir) {
		case NORTH:
			return chestassets[0];
		case WEST:
			return chestassets[2];
		case EAST:
			return chestassets[1];
		case SOUTH:
			return chestassets[3];
		default:
			System.out.println("broken dir");
			return chestassets[0];
		}
	}
	
}
