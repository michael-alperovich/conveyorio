package assets;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public final class ItemsAssets {
    public static BufferedImage coal, diamond, iron, diamond_ore, iron_ore;

    public static void loadAssets() throws IOException {
        coal = ImageIO.read(ItemsAssets.class.getResource("assets/tiles/items/coal.png"));
        diamond = ImageIO.read(ItemsAssets.class.getResource("assets/tiles/items/diamond.png"));
        iron = ImageIO.read(ItemsAssets.class.getResource("assets/tiles/items/iron.png"));
        diamond_ore = ImageIO.read(ItemsAssets.class.getResource("assets/tiles/items/diamond_ore.png"));
        iron_ore = ImageIO.read(ItemsAssets.class.getResource("assets/tiles/items/iron_ore.png"));
    }
}
