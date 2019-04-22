package assets;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public final class CoalAssets {
    public static BufferedImage[] coal = new BufferedImage[1];

    public static void loadAssets() throws IOException {
        coal[0] = ImageIO.read(new File("assets/tiles/coal/coal.png"));
    }
}
