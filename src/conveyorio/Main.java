package conveyorio;

import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
import assets.CoalAssets;
import assets.ConveyorAssets;
import objects.Coal;
import structures.Conveyor;
import structures.DIRECTIONS;

public class Main {
	/*
	 * Launch different programs and subtests in order to run stuff;
	 */
	public static final Scanner sc = new Scanner(System.in);
	public static void main(String[] args) throws Exception {
		loadGraphics();
		String action = sc.nextLine();
		switch(action) {
			case "testbuf":
				System.out.println("Enter in a phase number");
				System.out.println(ConveyorAssets.east[sc.nextInt() % 50]);
				break;
			case "animate":
				System.out.println("running animation gui");
				openAnimation();
				break;
			case "trackfps":

				openTrackFPS();
				break;
			case "test":
				Conveyor c = new Conveyor(new Point(0,0), DIRECTIONS.SOUTH);
				System.out.println(Arrays.toString(c.toLocal(0,25)));
				
				break;
			default:
				System.out.println("defaulting to opengame");
				openGame();
				break;
		}
		
		
		
	}

	private static void openGame() throws Exception {
		System.out.println("<---------------opening game animation---------->");
		GameWindow g = new GameWindow(500,500);
		g.boot();
	}

	private static void openTrackFPS(){
		// TODO Auto-generated method stub
		System.out.println("<------------FPS Instrumented GUI----------->");
		Thread t = new Thread(new Runnable() {
			
			@Override
			public void run(){
				// TODO Auto-generated method stub
				ConveyorAnimationGui g;
				System.out.println("Enter in dimensions of window (must be multiples 50)");
				int xmax = sc.nextInt();
				int ymax = sc.nextInt();
				try {
					g = new ConveyorAnimationGui(xmax	,ymax);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					return;
				}
				
				g.boot(true);
				while(true) {
					long start = System.nanoTime();
					int cFrames = g.canv.cFrames;
					
					while(true) {
						try {
							Thread.sleep(2000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							System.out.println("recieved sigterm.");
							return;
						}
						long cElapsedTime = System.nanoTime()-start;
						System.out.println("cfps: "+((g.canv.cFrames-cFrames)/(cElapsedTime/1000000000.0)));
						start = System.nanoTime();
						cFrames = g.canv.cFrames;
					}
				}
			}
		});
		t.start();
	}

	public static void openAnimation() throws Exception {
		System.out.println("<---------------opening conveyor animation---------->");
		ConveyorAnimationGui g = new ConveyorAnimationGui(500,500);
		g.boot();
	}
	
	public static Responses loadGraphics() {
		
		System.out.println("<-----------------Loading Assets---------------->");
		try {
			ConveyorAssets.loadAssets();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("[-] Conveyor Assets Failed to Load Assets");
			e.printStackTrace();
			
		}
		try {
			CoalAssets.loadAssets();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("[-] Coal Assets Failed to Load Assets");
			e.printStackTrace();

		}
		return Responses.OK;
	}
}
enum Responses{
	OK, ERROR;
}