package objects;

import assets.ItemsAssets;

import java.awt.image.BufferedImage;

public class Mineral extends GenericGameObject {

	private MINERAL_TYPES type;
	private boolean isOre;

    public Mineral(MINERAL_TYPES type) {
    	super();
		this.type = type;
		if (type == MINERAL_TYPES.DIAMOND_ORE || type == MINERAL_TYPES.IRON_ORE) {
			isOre = true;
		}
    }

	public boolean isOre() {
		return isOre;
	}

	public MINERAL_TYPES getType() {
		return type;
	}

	@Override
    public BufferedImage getIcon() {
    	switch (type) {
			case IRON:
				return ItemsAssets.iron;
			case DIAMOND:
				return ItemsAssets.diamond;
			case IRON_ORE:
				return ItemsAssets.iron_ore;
			case DIAMOND_ORE:
				return ItemsAssets.diamond_ore;
			default:
				System.out.println("Wrong mineral type");
				return null;
		}
    }

    public void makeNotOre0() {
		switch (type) {
			case IRON_ORE:
				type = MINERAL_TYPES.IRON;
				break;
			case DIAMOND_ORE:
				type = MINERAL_TYPES.DIAMOND;
				break;
		}

	}
}
