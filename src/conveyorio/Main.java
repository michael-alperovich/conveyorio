package conveyorio;

import java.io.IOException;
import java.util.Scanner;

import assets.ConveyorAssets;

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
		case "gui":
			System.out.println("running gui");
			openGame();
			break;
		case "trackfps":

			openTrackFPS();
			break;
		default:
			System.out.println("defaulting to opengame");
			openGame();
			break;
		}
		
		
		
	}

	private static void openTrackFPS(){
		// TODO Auto-generated method stub
		System.out.println("<------------FPS Instrumented GUI----------->");
		Thread t = new Thread(new Runnable() {
			
			@Override
			public void run(){
				// TODO Auto-generated method stub
				GameWindow g;
				System.out.println("Enter in dimensions of window (must be multiples 50)");
				int xmax = sc.nextInt();
				int ymax = sc.nextInt();
				try {
					g = new GameWindow(xmax	,ymax);
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

	public static void openGame() throws Exception {
		System.out.println("<---------------booting gui---------->");
		GameWindow g = new GameWindow(500,500);
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
		return Responses.OK;
	}
}
enum Responses{
	OK, ERROR;
}