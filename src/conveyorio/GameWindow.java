package conveyorio;

import assets.CoalAssets;
import assets.ConveyorAssets;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GameWindow {

   public Frame mainFrame;
   public GameCavans canv;
   public int x,y;
   public GameWindow() {
	   x = 500;
	   y = 500;
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
      mainFrame.setSize(500,500);
      mainFrame.setLayout(new GridLayout(1, 1));
      
      mainFrame.addWindowListener(new WindowAdapter() {
         public void windowClosing(WindowEvent windowEvent){
            System.exit(0);
         }        
      });    
   }
   public void boot() {boot(false);}
   public void boot(boolean fpstracking) {
	   canv = new GameCavans('N',fpstracking,x,y);
	   mainFrame.addKeyListener(canv);
	   canv.requestFocusInWindow();
	   mainFrame.add(canv);
	   mainFrame.setVisible(true);  

   }
}

class GameCavans extends JPanel implements KeyListener{
	private static final long serialVersionUID = 1L;
	public char direction;
	public int cFrames = 0;
	public boolean debugfps;
	public int xmax, ymax;
	public GameCavans(char dir,boolean trackFPS,int maxX, int maxY) {
		direction = dir;
		debugfps = trackFPS;
		xmax = maxX;
		ymax = maxY;
		//System.out.println("opened with "+xmax+" "+ymax);
        setSize(xmax, ymax);
		setBackground(Color.WHITE);
	}
	@Override
    public void paintComponent (Graphics g) {
		Graphics2D g2;
        if(debugfps) {
        	cFrames++;
        }

        super.paintComponent(g);
   		g.drawRect(2 * xmax / 8, 6 * ymax / 8, xmax / 2, ymax / 8);
		this.repaint();
	}
	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	
}