package conveyorio;

import java.io.IOException;

import assets.ConveyorAssets;

public class Main {
	public static void main(String[] args) throws IOException {
		loadGraphics();
	}
	public static void loadGraphics() {
		System.out.println("<-----------------Loading Assets---------------->");
		try {
			ConveyorAssets.loadAssets();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("[-] Conveyor Assets Failed to Load Assets");
			e.printStackTrace();
		}
		
	}
}
