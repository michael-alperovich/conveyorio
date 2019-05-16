package objects.GuiTiles;

import java.awt.image.BufferedImage;

import assets.ItemsAssets;
import objects.Coal;
import objects.GenericGameObject;
import properties.PlaceableItem;

public class CoalTile extends PlaceableItem {
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Coal";
	}

	@Override
	public BufferedImage onRender() {
		// TODO Auto-generated method stub
		return ItemsAssets.coal;
	}

	@Override
	public void onSwitch() {		
	}

	@Override
	public GenericGameObject getObject() {
		return new Coal();
	}

}
