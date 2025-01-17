package conveyorio;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import objects.Coal;
import objects.GuiTiles.NullTile;
import properties.PlaceableItem;
import objects.Mineral;
import structures.Conveyor;
import structures.DIRECTIONS;
import objects.MINERAL_TYPES;
import structures.SingleInserter;

public class GameWindow {

	public Frame mainFrame;
	public GameCavans canv;
	public int x,y;

	public GameWindow() {
		x = 600;
		y = 600;
		prepareWindow();
	}
	public GameWindow(int xsize, int ysize) throws Exception{
		if (xsize % 50 != 0 || ysize % 50 != 0) {
			System.out.println("non 50 div edges. exiting");
			throw new Exception("Game Window Size: "+xsize+ " "+ysize+" are not both multiples of 50. (mod 50 : "+(xsize % 50)+ " "+(ysize % 50)+")");
		}
		x = xsize;
		y = ysize;
		prepareWindow();
	}

	private void prepareWindow(){
		mainFrame = new Frame("Conveyor(io)");
		mainFrame.setSize(x,y);
		mainFrame.setLayout(new GridLayout(1, 1));

		mainFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent){
				System.exit(0);
			}
		});
	}
	public void boot() {boot(false);}
	public void boot(boolean fpstracking) {

		World.readSave();
		canv = new GameCavans(x,y);
		mainFrame.add(canv, BorderLayout.NORTH);
		mainFrame.setVisible(true);
		mainFrame.addKeyListener(canv);
		mainFrame.addMouseListener(canv);
		mainFrame.addMouseWheelListener(canv);
		mainFrame.addMouseMotionListener(canv);
		mainFrame.addComponentListener(canv);
		canv.setVisible(true);

	}
}

class GameCavans extends JPanel implements KeyListener, ComponentListener, MouseListener, MouseWheelListener, MouseMotionListener {
	private static final long serialVersionUID = 1L;
	final int offsetX = 6; // offset X for mouse handler due to borders.
	final int offsetY = 31; // offset Y due to borders.
	public int cFrames = 0;
	public boolean debugfps;
	public int cameraSpeedX, cameraSpeedY;
	public boolean sufficentScreenSize;

	public GameCavans(int windowX, int windowY) {
		Camera.setView(new Point(0,0), 1);
		debugfps = false;


//		for (int i = 0; i < 9; i++) {
//			Conveyor c = new Conveyor(new Point(0, 50 * i), DIRECTIONS.NORTH);
//			new Conveyor(new Point(400,-50 + (50 * i)),DIRECTIONS.SOUTH);
//			if (i == 8) {
//				Coal coal = new Coal();
//
//				coal.updatePosition(0, 50 * i + 25);
//				c.onTake(coal, null);
//			}
//		}
//
//		Conveyor lastreference = new Conveyor(new Point(50*(0+1),400),DIRECTIONS.WEST);
//		for(int i = 1; i < 9;i++) {
//			new Conveyor(new Point(50*i-50,-50),DIRECTIONS.EAST);
//			lastreference = new Conveyor(new Point(50*(i + 1),400),DIRECTIONS.WEST);
//		}
//
//		Coal nextcoal = new Coal();
//		nextcoal.updatePosition(490, 400);
//
//		Mineral nextcoal2 = new Mineral(MINERAL_TYPES.DIAMOND_ORE);
//		nextcoal2.updatePosition(440, 400);
//		lastreference.onTake(nextcoal2, null);
//		lastreference.onTake(nextcoal, null);

		Camera.setView(new Point(0,0), 1);
		setSize(windowX, windowY);

	}
	
	@Override
	public void paintComponent (Graphics g) {
		super.paintComponent(g);

		if(debugfps) {
			cFrames++;
		}
		Camera.cameraX += cameraSpeedX;
		Camera.cameraY -= cameraSpeedY;


		World.UpdateObjects(g, this);
		UIUX.updateUi(g, this);
		g.setColor(Color.WHITE);

		if (isSelectionOpen()) {
			UIUX.renderSelectionGui(g, this);
		}
		//Graphics2D g2 = (Graphics2D)g;
		//g2.drawLine(0, Camera.remapY(0), 1000, Camera.remapY(0));// keep this line for calibration.

		this.repaint();
	}
	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		if (arg0.getKeyChar() == 'w') {
			this.cameraSpeedY = Math.min(10, this.cameraSpeedY + 1);
		}
		else if(arg0.getKeyChar() == 'a') {
			this.cameraSpeedX = Math.max(this.cameraSpeedX-1, -10);
		}
		else if (arg0.getKeyChar() == 's') {
			this.cameraSpeedY = Math.max(this.cameraSpeedY-1, -10);
		}
		else if (arg0.getKeyChar() == 'd') {
			this.cameraSpeedX = Math.min(10, this.cameraSpeedX + 1);
		}

	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		if (arg0.getKeyChar() == 'w') {
			this.cameraSpeedY = Math.min(0, cameraSpeedY);
		}
		else if(arg0.getKeyChar() == 'a') {
			this.cameraSpeedX = Math.max(0, cameraSpeedX);
		}
		else if (arg0.getKeyChar() == 's') {
			this.cameraSpeedY = Math.max(0, cameraSpeedY);
		}
		else if (arg0.getKeyChar() == 'd') {
			this.cameraSpeedX = Math.min(0, cameraSpeedX);
		}
		else if (arg0.getKeyChar() == 'e') {
			if(!UIUX.requestFocus) {
				UIUX.openSelection();
			}
			else {
				UIUX.escape();
			}
		}
		else if (arg0.getKeyChar() == 'p') {
			if ( UIUX.showLog) {
				JOptionPane.showMessageDialog(null, String.join("\n", UIUX.targetinfo.logBuffer));
				
			}
		}
		else if (arg0.getKeyChar() == 'r') {
			UIUX.toPlace.onRotate();
		}
		else if(arg0.getKeyChar() == 'o') {
			UIUX.toggleLog();
		}
		else if (arg0.getKeyChar() == 'c') {
			if(UIUX.showLog) {
				UIUX.targetinfo.logBuffer.clear();
			}
		}
		else if (arg0.getKeyChar() == 'q') {
			UIUX.toPlace = new NullTile();
		}
		else if (arg0.getKeyCode() == KeyEvent.VK_ESCAPE) {
			UIUX.escape();
		}
	}
	public boolean isSelectionOpen() {
		return UIUX.requestFocus && sufficentScreenSize;
	}
	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}
	@Override
	public void componentHidden(ComponentEvent arg0) {

	}
	@Override
	public void componentMoved(ComponentEvent arg0) {

	}
	@Override
	public void componentResized(ComponentEvent arg0) {
		UIUX.updateDimensions(this.getWidth(), this.getHeight());
		Camera.updateDimensions(this.getWidth(), this.getHeight());
		sufficentScreenSize = this.getWidth() >= 900 && this.getHeight() >= 600;
	}
	@Override
	public void componentShown(ComponentEvent arg0) {
		// TODO Auto-generated method stub

	}
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		if (!isSelectionOpen()) {
			int globalX = Camera.inverseX(arg0.getX());
			int globalY = Camera.inverseY(arg0.getY());
			globalX = (int) (Math.floor(globalX/50.0)*50);
			globalY = (int) (Math.floor(globalY/50.0)*50);
			UIUX.targetinfo = World.getTileAt(new Point(globalX, globalY));

		}
		else {
			UIUX.mouseClicked(arg0.getX(), arg0.getY());

		}
	}
	@Override
	public void mouseEntered(MouseEvent arg0) {
	}
	@Override
	public void mouseExited(MouseEvent arg0) {

	}
	@Override
	public void mousePressed(MouseEvent arg0) {
		// only gets called once so it wont handle the mouseDragged events.
		if ((SwingUtilities.isLeftMouseButton(arg0))) {
			if (!(UIUX.toPlace instanceof NullTile) && !isSelectionOpen()) {
				updatePlacement(arg0);
				UIUX.tryPlace();
			}
		}
		else if(SwingUtilities.isRightMouseButton(arg0)) {
			updatePlacement(arg0);
			UIUX.tryRemove();
		}
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {

	}
	@Override
	public void mouseWheelMoved(MouseWheelEvent arg0) {
		// TODO Auto-generated method stub
		if (isSelectionOpen()) {
			UIUX.scrollSelect(arg0.getWheelRotation(), arg0.getX());
		}
		else {
			Camera.updateZoomByWheel(arg0.getWheelRotation());
		}
	}
	@Override
	public void mouseDragged(MouseEvent arg0) {
		if(isSelectionOpen()) {return;}
		if ((SwingUtilities.isLeftMouseButton(arg0))) {
			if (!(UIUX.toPlace instanceof NullTile) && !isSelectionOpen()) {
				updatePlacement(arg0);
				UIUX.tryPlace();
			}
		}
		else if(SwingUtilities.isRightMouseButton(arg0)) {
			updatePlacement(arg0);

			UIUX.tryRemove();
		}

	}
	public void updatePlacement(MouseEvent arg0) {
		int globalX = Camera.inverseX(arg0.getX());
		int globalY = Camera.inverseY(arg0.getY());
		if (UIUX.toPlace instanceof PlaceableItem) {
			UIUX.setTargetPlacement(new Point(globalX,globalY));
			return;
		}
		globalX = (int) (Math.floor(globalX/50.0)*50);
		globalY = (int) (Math.floor(globalY/50.0)*50);
		UIUX.setTargetPlacement(new Point(globalX, globalY));
	}
	@Override
	public void mouseMoved(MouseEvent arg0) {
		if (! (UIUX.toPlace instanceof NullTile)) {
			// valid tile that we are controlling.
			updatePlacement(arg0);


		}
	}


}