package conveyorio;

public class Point {
	private int x;
	private int y;

	public Point(int x,int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	@Override
	public int hashCode() {
		return (x * 31 + y) % (int)(10E9 + 7);
	}

	@Override
	public boolean equals(Object o) {
		return o instanceof Point && this.x == ((Point) o).getX() && this.y == ((Point) o).getY();

	}
	
	public Point add(Point otherPoint) {
		
		return new Point(x + otherPoint.getX(),y + otherPoint.getY());
	}
	
}