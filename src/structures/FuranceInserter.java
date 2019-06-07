package structures;

import conveyorio.Point;
import objects.GenericGameObject;
import objects.Mineral;
import properties.Fuel;

public class FuranceInserter extends SingleInserter{

	public FuranceInserter(Point loc, DIRECTIONS d) {
		super(loc, d);
	}
	@Override
	public boolean accepts(GenericGameObject obj) {
		if (this.sink == null) {return false;}
		if (!(this.sink instanceof Furnace)) {return false;}
		
		
		if (((Furnace)this.sink).fuel < 5*Furnace.smeltingTime && (obj instanceof Fuel)) {return true;}
		if (((Furnace)this.sink).orelist.size() < 4 && (obj instanceof Mineral)) {return true;}
		
		return false;
	}
	@Override
	public String getCode() {
		return String.format("FuranceInserter %s %s %s", super.location.getX(),super.location.getY(), super.direction);
	}
}
