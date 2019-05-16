package objects.GuiTiles;

import java.awt.image.BufferedImage;

import assets.ItemsAssets;
import objects.GenericGameObject;
import objects.MINERAL_TYPES;
import objects.Mineral;
import properties.PlaceableItem;

public class IronTile extends PlaceableItem {

	@Override
	public String getName() {
		return "Iron";
	}

	@Override
	public BufferedImage onRender() {
		return ItemsAssets.iron;
	}

	@Override
	public void onSwitch() {

	}

	@Override
	public GenericGameObject getObject() {
		return new Mineral(MINERAL_TYPES.IRON);
	}

}
