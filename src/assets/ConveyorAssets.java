package assets;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;


public final class ConveyorAssets{
	public static BufferedImage[] east = new BufferedImage[50];
	public static BufferedImage[] north = new BufferedImage[50];
	public static BufferedImage[] south = new BufferedImage[50];
	public static BufferedImage[] west = new BufferedImage[50];
	public static void loadAssets() throws IOException {
		for(int i = 0; i < 50;i++) {
			east[i] = ImageIO.read(ConveyorAssets.class.getResource("assets/tiles/conveyor/east/conveyor-small_phase"+i+".png"));
			north[i] = ImageIO.read(ConveyorAssets.class.getResource("assets/tiles/conveyor/north/conveyor-small_phase"+i+".png"));
			south[i] = ImageIO.read(ConveyorAssets.class.getResource("assets/tiles/conveyor/south/conveyor-small_phase"+i+".png"));
			west[i] = ImageIO.read(ConveyorAssets.class.getResource("assets/tiles/conveyor/west/conveyor-small_phase"+i+".png"));
		}
	}
}
