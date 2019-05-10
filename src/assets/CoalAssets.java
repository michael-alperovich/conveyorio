package assets;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public final class CoalAssets {
    public static BufferedImage[] coal = new BufferedImage[1];

    public static void loadAssets() throws IOException {
        coal[0] = ImageIO.read(CoalAssets.class.getResource("assets/tiles/coal/coal.png"));
    }
}
