package structures;

import conveyorio.Point;
import objects.GenericGameObject;
import properties.Fuel;

public class FuelInserter extends SingleInserter{

	public FuelInserter(Point loc, DIRECTIONS d) {
		super(loc, d);
	}
	@Override
	public boolean accepts(GenericGameObject obj) {
		if (! (obj instanceof Fuel) ) {return false;}
		return ((Fuel)obj).getFuelValue() > 0;
	}

}
