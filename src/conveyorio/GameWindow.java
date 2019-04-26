package conveyorio;

import assets.CoalAssets;
import assets.ConveyorAssets;
import objects.Coal;
import structures.*;

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
	   System.out.println("loading");
	   canv = new GameCavans('N',fpstracking,400,400);
	   mainFrame.add(canv, BorderLayout.NORTH);
	   mainFrame.setVisible(true);  
	   canv.setVisible(true);

   }
}

class GameCavans extends JPanel implements KeyListener{
	private static final long serialVersionUID = 1L;
	public char direction;
	public int cFrames = 0;
	public boolean debugfps;
	public int xmax, ymax;
	public GameCavans(char dir,boolean trackFPS,int maxX, int maxY) {
		World.setView(new Point(0,0));
		direction = dir;
		debugfps = trackFPS;
		Conveyor c1 = new Conveyor(new Point(0,0), DIRECTIONS.NORTH);
		World.addTile(c1);
		Conveyor c2 = new Conveyor(new Point(0,50), DIRECTIONS.NORTH);
		World.addTile(c2);
		Coal c = new Coal();
		c.updatePosition(0, 50);
		c2.onTake(c);
		xmax = maxX;
		ymax = maxY;
		//System.out.println("opened with "+xmax+" "+ymax);
        setSize(xmax, ymax);
		//setBackground(Color.WHITE);
	}
	@Override
    public void paintComponent (Graphics g) {
        super.paintComponent(g);

		Graphics2D g2 = (Graphics2D)g;
		if(debugfps) {
        	cFrames++;
        }
		g.drawImage(ConveyorAssets.east[0], 200, 200, this);
        World.UpdateObjects(g, this);
		
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