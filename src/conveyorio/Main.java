package conveyorio;

import java.io.IOException;
import java.util.Scanner;

import assets.*;


public class Main {
	/*
	 * Launch different programs and subtests in order to run stuff;
	 */
	public static final Scanner sc = new Scanner(System.in);
	public static void main(String[] args) throws Exception {
		loadGraphics();
		openGame();
	}

	private static void openGame() throws Exception {
		System.out.println("<---------------opening game animation---------->");
		GameWindow g = new GameWindow(1000,700);
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

			ItemsAssets.loadAssets();
		} catch (IOException e) {
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
		try {
			MiscAssets.loadAssets();
		} catch (Exception e) {
			System.out.println("[-] Misc Assets Failed to Load Assets");
			e.printStackTrace();

		}
		return Responses.OK;
	}
}
enum Responses{
	OK, ERROR;
}