package conveyorio;

import java.io.IOException;
import java.util.Scanner;

import assets.ChestAssets;
import assets.CoalAssets;
import assets.ConveyorAssets;
import assets.SingleInserterAssets;
import structures.RegularChest;

public class Main {
	/*
	 * Launch different programs and subtests in order to run stuff;
	 */
	public static final Scanner sc = new Scanner(System.in);
	public static void main(String[] args) throws Exception {
		loadGraphics();
		openGame();
		//String action = sc.nextLine();
		/*
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
				System.out.println(Math.floor(-1.0/2));
				break;
			default:
				System.out.println("defaulting to opengame");
				openGame();
				break;
		}
		*/
	}

	private static void openGame() throws Exception {
		System.out.println("<---------------opening game animation---------->");
		GameWindow g = new GameWindow(1000,700);
		g.boot();
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
		} catch (Exception e) {
			System.out.println("[-] Conveyor Assets Failed to Load Assets");
			e.printStackTrace();
			
		}
		try {
			CoalAssets.loadAssets();
		} catch (Exception e) {
			System.out.println("[-] Coal Assets Failed to Load Assets");
			e.printStackTrace();

		}
		try {
			SingleInserterAssets.loadAssets();
		} catch (Exception e) {
			System.out.println("[-] Single Inserter Assets Failed to Load Assets");
			e.printStackTrace();

		}
		try {
			ChestAssets.loadAssets();
		} catch (Exception e) {
			System.out.println("[-] Chest Assets Failed to Load Assets");
			e.printStackTrace();

		}
		return Responses.OK;
	}
}
enum Responses{
	OK, ERROR;
}