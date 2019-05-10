package conveyorio;

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
	   System.out.println("loading");
	   canv = new GameCavans('N',fpstracking,x,y);
	   mainFrame.add(canv, BorderLayout.NORTH);
	   mainFrame.setVisible(true);  
	   mainFrame.addKeyListener(canv);
	   canv.setVisible(true);

   }
}

class GameCavans extends JPanel implements KeyListener{
	private static final long serialVersionUID = 1L;
	public char direction;
	public int cFrames = 0;
	public boolean debugfps;
	public int xmax, ymax;
	public int camerax, cameray;
	public int cameraSpeedX, cameraSpeedY;
	public GameCavans(char dir,boolean trackFPS,int maxX, int maxY) {
		World.setView(new Point(0,0));
		direction = dir;
		debugfps = trackFPS;
		for (int i = 0; i < 9; i++) {
			Conveyor c = new Conveyor(new Point(0, 50 * i), DIRECTIONS.NORTH);
			new Conveyor(new Point(400,-50 + (50 * i)),DIRECTIONS.SOUTH);
			if (i == 8) {
				Coal coal = new Coal();

				coal.updatePosition(0, 50 * i + 25);
				c.onTake(coal);
			}
		}
		
		Conveyor lastreference = new Conveyor(new Point(50*(0+1),400),DIRECTIONS.WEST);
		for(int i = 1; i < 9;i++) {
			new Conveyor(new Point(50*i-50,-50),DIRECTIONS.EAST);
			lastreference = new Conveyor(new Point(50*(i + 1),400),DIRECTIONS.WEST);
		}
	
		Coal nextcoal = new Coal();
		nextcoal.updatePosition(490, 400);
		
		Coal nextcoal2 = new Coal();
		nextcoal2.updatePosition(440, 400);
		lastreference.onTake(nextcoal2);
		lastreference.onTake(nextcoal);

		SingleInserter inserter = new SingleInserter(new Point(200,  200), DIRECTIONS.NORTH);
		inserter.onTake(new Coal());
		new Conveyor(new Point(200, 250),DIRECTIONS.WEST);
		xmax = maxX;
		ymax = maxY;
		camerax = 0;
		cameray = 0;
		World.setView(new Point(camerax,cameray));
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
		this.camerax += cameraSpeedX;
		this.cameray -= cameraSpeedY;
		World.setView(new Point(camerax,cameray));
        World.UpdateObjects(g, this);
		
        this.repaint();
	}
	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		if (arg0.getKeyChar() == 'w') {
			this.cameraSpeedY += 1;
		}
		else if(arg0.getKeyChar() == 'a') {
			this.cameraSpeedX -= 1;
		}
		else if (arg0.getKeyChar() == 's') {
			this.cameraSpeedY -= 1;
		}
		else if (arg0.getKeyChar() == 'd') {
			this.cameraSpeedX += 1;
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
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	
}