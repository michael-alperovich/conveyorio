package objects;

import java.awt.image.BufferedImage;

import assets.CoalAssets;
import properties.Fuel;

public class Coal extends GenericGameObject implements Fuel {
	public int fuelValue = 10;
    public Coal() {
        super(0);
    }
    @Override
    public BufferedImage getIcon() {
        return CoalAssets.coal[0];
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
