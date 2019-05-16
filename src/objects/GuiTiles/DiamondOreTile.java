package objects.GuiTiles;

import java.awt.image.BufferedImage;

import assets.ItemsAssets;
import objects.GenericGameObject;
import objects.MINERAL_TYPES;
import objects.Mineral;
import properties.PlaceableItem;

public class DiamondOreTile extends PlaceableItem {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Diamond Ore";
	}

	@Override
	public BufferedImage onRender() {
		// TODO Auto-generated method stub
		return ItemsAssets.diamond_ore;
	}

	@Override
	public void onSwitch() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public GenericGameObject getObject() {
		// TODO Auto-generated method stub
		return new Mineral(MINERAL_TYPES.DIAMOND_ORE);
	}

}
