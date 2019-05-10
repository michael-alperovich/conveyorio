package conveyorio;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JPanel;

import assets.CoalAssets;
import assets.ConveyorAssets;

public class ConveyorAnimationGui {

   public Frame mainFrame;
   public AnimatedCanvas canv;
   public int x,y;
   public ConveyorAnimationGui() {
	   x = 500;
	   y = 500;
	   prepareWindow();
   }
   public ConveyorAnimationGui(int xsize, int ysize) throws Exception{
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
	   canv = new AnimatedCanvas('N',fpstracking,x,y);
	   mainFrame.addKeyListener(canv);
	   canv.requestFocusInWindow();
	   mainFrame.add(canv);
	   mainFrame.setVisible(true);  

   }
}
class AnimatedCanvas extends JPanel implements KeyListener{
	private static final long serialVersionUID = 1L;
	public char direction;
	public int cFrames = 0;
	public boolean debugfps;
	public int xmax, ymax;
	public AnimatedCanvas(char dir,boolean trackFPS,int maxX, int maxY) {
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
		if(debugfps) {
        	cFrames++;
        }

        super.paintComponent(g);
   		long time = System.currentTimeMillis();
		int cSecondPeriod = (int) (time % 1000);
		cSecondPeriod /= 20;
		for(int x = 0; x < xmax;x+= 50) {
			for(int y = 0; y < ymax;y += 50) {
				g.drawImage(ConveyorAssets.north[cSecondPeriod], x , y, this);
			}
		}
		g.drawImage(CoalAssets.coal[0], (int)((time % 10000) * 0.05) % xmax  , 50, this);
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