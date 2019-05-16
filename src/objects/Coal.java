package objects;

import java.awt.image.BufferedImage;

import assets.ItemsAssets;
import properties.Fuel;

public class Coal extends GenericGameObject implements Fuel {
	public int fuelValue = 20*1000;
    public Coal() {
        super();
    }
    @Override
    public BufferedImage getIcon() {
        return ItemsAssets.coal;
    }
	@Override
	public int getFuelValue() {
		// TODO Auto-generated method stub
		return fuelValue;
	}
	@Override
	public void subtractFuelValue(int x) {
		// TODO Auto-generated method stub
		fuelValue -=x;
	}
}
