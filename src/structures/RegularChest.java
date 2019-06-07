package structures;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.ImageObserver;

import assets.ChestAssets;
import conveyorio.Camera;
import conveyorio.Point;
import objects.GenericGameObject;

public class RegularChest extends Structure {
	private DIRECTIONS direction;
	public RegularChest(Point loc, DIRECTIONS d) {
		super(loc, 50,50);
		direction = d;
	}
	public String getCode() {
		return String.format("Chest %s %s %s", super.location.getX(),super.location.getY(), direction);
	}
	@Override
	public void onUpdate(Graphics g, ImageObserver ref) {
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(ChestAssets.getOrientation(direction), Camera.remapX(location.getX()), Camera.remapY(location.getY()), Camera.resizedX(50), Camera.resizedY(50), ref);
	}
	@Override
	public boolean canReceive(GenericGameObject object, Structure s) {
		return true;
	}
	@Override
	public void onTake(GenericGameObject g, Structure source) {
		// TODO Auto-generated method stub
		objects.add(g);
	}
	@Override
	public void onRemove(GenericGameObject g) {
		// TODO Auto-generated method stub
		objects.remove(g);
	}

	
}
