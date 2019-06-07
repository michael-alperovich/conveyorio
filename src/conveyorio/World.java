package conveyorio;

import java.awt.Graphics;
import java.awt.image.ImageObserver;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.StringTokenizer;

/*
 * Rendering Chunk, Can Render noGraphic or Graphics.
 */
import structures.Conveyor;
import structures.ConveyorLike;
import structures.CurvedConveyor;
import structures.DIRECTIONS;
import structures.FuranceInserter;
import structures.Furnace;
import structures.RegularChest;
import structures.SingleInserter;
import structures.Structure;


public class World {
	public static HashMap<Point, Structure> world = new HashMap<Point,Structure>();
	
	public static HashSet<Point> conveyorSources  = new HashSet<Point>();
	public static boolean saved = false;
	public World() {
	}
	public static void UpdateObjects(Graphics g,ImageObserver ref) {
		/*
		 * Current Problem
		 * Async register of a conveyorbelt may deregister a source while conveyorSources is being accessed from the line below.
		 * Causes a concurrentmodification exception, so we cant use a iterable.
		 */

		for (Object source : conveyorSources.toArray()) {
			Structure object = getTileAt((Point)source);
			if (object != null) {

				object.onUpdate(g,ref);	
			}
		}
		Iterator<Entry<Point, Structure>> it = world.entrySet().iterator();
		while(it.hasNext()) {
			Structure s = it.next().getValue();
			if (s instanceof Conveyor) {
				s.onUpdate(g, ref);
			}
		}
		it = world.entrySet().iterator();
		while(it.hasNext()) {
			Structure s = it.next().getValue();
			if (! (s instanceof Conveyor)) {
				s.onUpdate(g, ref);
			}
		}
		if (System.currentTimeMillis() / 1000 % 10 == 0) {
			if (!saved) {
				pushSave(System.currentTimeMillis());
			}
			saved = true;
		}
		else {
			saved = false;
		}
		
	}
	public static void addTile(Structure p) {
		world.put(p.location, p);
	}
	public static Structure getTileAt(Point targetPoint) {
		return  world.getOrDefault(targetPoint,null);
	}

	public static void registerSink(ConveyorLike previous) {
		if(previous == null) {
			return;
		}
		conveyorSources.add(previous.location);
		
	}
	public static void pushSave(long time) {
		File x = new File("Conveyorio.save");
		File backupdir = new File("WorldBackups");
		backupdir.mkdirs();
		
		File xbackup = new File("WorldBackups/Conveyorio_backup"+time+".save");
		
		try {
			PrintWriter toWrite = new PrintWriter(x);
			PrintWriter toWriteBackup = new PrintWriter(xbackup);
			Iterator<Entry<Point, Structure>> it = world.entrySet().iterator();
			while(it.hasNext()) {
				String target = it.next().getValue().getCode();
				toWrite.println(target);
				toWriteBackup.println(target);
			}
			toWrite.close();
			toWriteBackup.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("failed to make save file.");
		}
	}
	public static DIRECTIONS getDirection(String dir) {
		dir = dir.toUpperCase();
		switch(dir) {
		case "NORTH":
			return DIRECTIONS.NORTH;
		case "EAST":
			return DIRECTIONS.EAST;
		case "WEST":
			return DIRECTIONS.WEST;
		case "SOUTH":
			return DIRECTIONS.SOUTH;
		default:
			System.out.println("undefined direction: "+dir);
			return DIRECTIONS.NORTH;
		}
	}
	public static void readSave() { // only reads Conveyorio.save
		try {
			BufferedReader x = new BufferedReader(new FileReader("Conveyorio.save"));
			while(x.ready()) {
				StringTokenizer st = new StringTokenizer(x.readLine());
				String type = st.nextToken();
				switch(type) {
				case "Conveyor":
					new Conveyor(new Point(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken())), getDirection(st.nextToken()));
					break;
				case "CurvedConveyor":
					new CurvedConveyor(new Point(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken())), getDirection(st.nextToken()), st.nextToken().charAt(0) == 'y');
					break;
				case "Chest":
					new RegularChest(new Point(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken())), getDirection(st.nextToken()));
					break;
				case "Furnace":
					new Furnace(new Point(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken())));
					break;
				case "Inserter":
					new SingleInserter(new Point(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken())), getDirection(st.nextToken()));
					break;

				case "FuranceInserter":
					new FuranceInserter(new Point(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken())), getDirection(st.nextToken()));
					break;
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("No backup found.");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("A generic excpetion has occursed and we stopped doing anything");
			e.printStackTrace();
		}
		
	}
	public static void deregisterSink(ConveyorLike previous) {
		conveyorSources.remove(previous.location);
	}

	public static void removeTile(Structure s) {
		world.remove(s.location);
		if(conveyorSources.contains(s.location)) {
			conveyorSources.remove(s.location);
			registerSink(((Conveyor)s).previous);
		}
	}
	
}


