package objects;

import assets.CoalAssets;

import java.awt.image.BufferedImage;

public class Coal extends GenericGameObject {

    public Coal() {
        super(0);
    }
    @Override
    BufferedImage getIcon() {
        return CoalAssets.coal[0];
    }
}
