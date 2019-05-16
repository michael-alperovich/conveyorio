package objects.GuiTiles;

import java.awt.image.BufferedImage;

import assets.ItemsAssets;
import objects.GenericGameObject;
import objects.MINERAL_TYPES;
import objects.Mineral;
import properties.PlaceableItem;

public class DiamondTile extends PlaceableItem {

	@Override
	public String getName() {
		return "Diamond";
	}

	@Override
	public BufferedImage onRender() {
		return ItemsAssets.diamond;
	}

	@Override
	public void onSwitch() {
	}

	@Override
	public GenericGameObject getObject() {
		return new Mineral(MINERAL_TYPES.DIAMOND);
	}

}
