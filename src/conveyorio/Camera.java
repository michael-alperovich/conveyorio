package conveyorio;


public class Camera {
	/*
	 * Strictly Math based cordinate tracking system that allows us to zoom and translate the screen while allowing for items to render correctly.
	 */
	public static double ZoomFactor = 1;
	public static double wheelClicks = 0;
	public static int cameraX = 1;
	public static int cameraY = 1;
	public static int windowX = 0;
	public static int windowY = 0;
	final static int offsetX = 6; // offset X for mouse handler due to borders.
	final static int offsetY = 31; // offset Y due to borders.
	
	public static int resizedX(double xi) {
		return (int) Math.ceil(ZoomFactor*xi);
	}
	public static int resizedY(double yi) {
		return (int) Math.ceil(ZoomFactor*yi);
	}
	public static int remapX(double xi) {
		return  (int) (ZoomFactor*(xi-cameraX) + windowX/2);
	}
	public static int remapY(double yi) {
		return (int) (ZoomFactor*(yi-cameraY ) + windowY/2);
	}
	public static void setView(Point point, int i) {
		cameraX = point.getX();
		cameraY = point.getY();
		ZoomFactor = i;
		
	}
	public static void updateDimensions(int width, int height) {
		windowX = width;
		windowY = height;
	}
	public static void resetZoom() {setZoom(1);};
	public static void setZoom(double zoom) {
		ZoomFactor = zoom;
		// also need to set clicks, but not sure about type.
		wheelClicks = -10 * Math.log(zoom)/Math.log(2);
	}
	public static double clip(double inp, double li, double ri) {
		return Math.min(Math.max(inp, li), ri);
	}
	public static void updateZoomByWheel(double clicks) {
		wheelClicks += clicks;
		wheelClicks = clip(wheelClicks,-50,50);
		
		// 10 wheel clicks should 1/2 the whole screen.
		ZoomFactor = Math.pow(2, -wheelClicks/10.0);
		
	}
	public static int resizeX(double xi) {return resizedX(xi);}
	public static int resizeY(double yi) {return resizedY(yi);}
	public static int inverseX(int xi) { // invert from mouse cordinates to World Cordinates.
		return (int) ((xi-windowX/2 - offsetX)/ZoomFactor  + cameraX );
	}
	public static int inverseY(int yi) {
		return (int) ((yi-windowY/2 - offsetY)/ZoomFactor  + cameraY );
	}
	
	
	//int globalX = arg0.getX() + this.camerax-this.offsetX;
	//int globalY = arg0.getY() + this.cameray-this.offsetY;
	
}
