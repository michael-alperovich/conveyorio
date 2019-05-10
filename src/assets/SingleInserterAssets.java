package assets;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SingleInserterAssets {
	public static BufferedImage[] phases = new BufferedImage[360];
	public static void loadAssets() throws IOException {
		for(int angle = 0; angle < 360;angle++) {
			phases[angle] = ImageIO.read(ConveyorAssets.class.getResource("assets/tiles/inserter/regular/originaltile_rotateframe("+angle+").png"));
			
		}
	}
}
