package assets;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class FurnaceAssets {
	public static BufferedImage[] AnimatedFire = new BufferedImage[4];
	public static BufferedImage  emptyFurnace;
	public static void loadAssets() throws IOException{
		AnimatedFire[0] = ImageIO.read(ChestAssets.class.getResource("assets/tiles/furnace/furnace_fire1.png"));
		AnimatedFire[1] = ImageIO.read(ChestAssets.class.getResource("assets/tiles/furnace/furnace_fire2.png"));
		AnimatedFire[2] = ImageIO.read(ChestAssets.class.getResource("assets/tiles/furnace/furnace_fire3.png"));
		AnimatedFire[3] = ImageIO.read(ChestAssets.class.getResource("assets/tiles/furnace/furnace_fire4.png"));
		emptyFurnace = ImageIO.read(ChestAssets.class.getResource("assets/tiles/furnace/furnace_top.png"));
	}
	
	public static BufferedImage getFire() {
		long ctime = System.currentTimeMillis()/100;
		ctime %= 3;
		return AnimatedFire[(int)ctime];
	}
	public static BufferedImage emptyFurance() {
		return emptyFurnace;		
	}

}
