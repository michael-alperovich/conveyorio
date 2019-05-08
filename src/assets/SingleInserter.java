package assets;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SingleInserter {
	public static BufferedImage[] phases;
	public static void loadAssets() throws IOException {
		for(int angle = 0; angle < 360;angle++) {
			phases[angle] = ImageIO.read(new File("assets/tiles/inserter/regular/originaltile_rotateframe("+angle+").png"));
			
		}
	}
}
