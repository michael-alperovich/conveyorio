package conveyorio;

public abstract class Viewable {
	public int dimx = 0;
	public int dimy = 0;
	
	public Viewable(int x, int y) {
		dimx = x;
		dimy = y;
	}
	
	
	abstract void updateGraphics() ;
}
