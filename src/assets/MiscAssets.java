package assets;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public final class MiscAssets {
    public static BufferedImage nullTile;

    public static void loadAssets() throws IOException {
        nullTile = ImageIO.read(MiscAssets.class.getResource("assets/tiles/misc/null_tile.png"));
    }
}
