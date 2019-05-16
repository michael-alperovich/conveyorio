package objects.GuiTiles;

import java.awt.image.BufferedImage;

import assets.ItemsAssets;
import objects.GenericGameObject;
import objects.MINERAL_TYPES;
import objects.Mineral;
import properties.PlaceableItem;

public class IronOreTile extends PlaceableItem {

	@Override
	public String getName() {
		return "Iron Ore";
	}

	@Override
	public BufferedImage onRender() {
		return ItemsAssets.iron_ore;
	}
	@Override
	public void onSwitch() {}
	@Override
	public GenericGameObject getObject() {
		return new Mineral(MINERAL_TYPES.IRON_ORE);
	}

}
