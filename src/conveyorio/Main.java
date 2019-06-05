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
		Thread t1 = new Thread(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				long cTime = System.currentTimeMillis();
				try {
					ConveyorAssets.loadAssets();
				} catch (Exception e) {
					System.out.println("[-] Conveyor Assets Failed to Load Assets");
					e.printStackTrace();	
				}
				System.out.println("ConveyorAssets Time: "+(System.currentTimeMillis()-cTime));
			}});
		Thread t2 = new Thread(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				long cTime = System.currentTimeMillis();
				try {

					ItemsAssets.loadAssets();
				} catch (IOException e) {
					System.out.println("[-] Coal Assets Failed to Load Assets");
					e.printStackTrace();

				}

				System.out.println("CoalAssets Time: "+(System.currentTimeMillis()-cTime));
			}});
		Thread lightThread = new Thread(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				long cTime = System.currentTimeMillis();
				try {
					ChestAssets.loadAssets();
				} catch (Exception e) {
					System.out.println("[-] Chest Assets Failed to Load Assets");
					e.printStackTrace();
				}

				System.out.println("Chest Time: "+(System.currentTimeMillis()-cTime));
				cTime = System.currentTimeMillis();
				try {
					MiscAssets.loadAssets();
				} catch (Exception e) {
					System.out.println("[-] Misc Assets Failed to Load Assets");
					e.printStackTrace();

				}

				System.out.println("MiscAssets Time: "+(System.currentTimeMillis()-cTime));
				cTime = System.currentTimeMillis();
				try {
					FurnaceAssets.loadAssets();
				} catch (Exception e) {
					System.out.println("[-] Furnace Assets Failed to Load Assets");
					e.printStackTrace();

				}

				System.out.println("FurnaceAssets Time: "+(System.currentTimeMillis()-cTime));
			}});
		Thread singleInserter = new Thread(new Runnable(){

			@Override
			public void run() {
				long cTime = System.currentTimeMillis();
				try {
					SingleInserterAssets.loadAssets();
				} catch (Exception e) {
					System.out.println("[-] Single Inserter Assets Failed to Load Assets");
					e.printStackTrace();

				}

				System.out.println("SingleInserter Time: "+(System.currentTimeMillis()-cTime));
					
			}});	
		Thread curvedLoad = new Thread(new Runnable(){

			@Override
			public void run() {
				long cTime = System.currentTimeMillis();
				try {
					CurvedConveyorAssets.loadAssets();
				} catch (Exception e) {
					System.out.println("[-] Curved Conveyor Assets Failed to Load Assets");
					e.printStackTrace();
				}

				System.out.println("CurvedAssets Time: "+(System.currentTimeMillis()-cTime));
				cTime = System.currentTimeMillis();
				
			}});	
				
		System.out.println("<-----------------Loading Assets---------------->");
		long start = System.currentTimeMillis();
		t1.start();
		t2.start();
		lightThread.start();
		singleInserter.start();
		curvedLoad.start();
		
		while(t1.isAlive() || t2.isAlive() || lightThread.isAlive());
		while(singleInserter.isAlive() || curvedLoad.isAlive());
		long diff = System.currentTimeMillis() - start;
		System.out.println("Overall Load time: "+diff+" ms");
		return Responses.OK;
	}
}
enum Responses{
	OK, ERROR;
}