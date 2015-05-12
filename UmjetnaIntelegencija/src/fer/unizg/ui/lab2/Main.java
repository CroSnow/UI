package fer.unizg.ui.lab2;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import fer.unizg.ui.lab2.*;
import fer.unizg.ui.lab2.resolverPack.WumpursWorldKnowBase;

/**
 * The main class which guides Picard through the map.
 * 
 * @author Ivan
 *
 */
public class Main {
	// The map we move on.
	private static HashMap<Point, Field> map;
	// The current field we are on.
	private static Field curentField;
	// The coordinates of the field we are on.
	private static Point pos;
	// The safe coordinates.
	private static LinkedList<Point> safe;
	// Dimension of the map.
	private static int dimension;
	// The logic base.
	private static WumpursWorldKnowBase base;
	// List of unsafe cordinates (we dont know if we will die going on them).
	private static LinkedList<Point> unsafe;

	public static void main(String[] args) {
		map = new HashMap<Point, Field>();
		pos = new Point(1, 1);
		dimension = 1;
		safe = new LinkedList<Point>();
		safe.add(pos);
		BufferedReader reader = null;

		System.out.println("---BEGIN---");
		try {
			reader = new BufferedReader(new FileReader("inputMap.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		parseInput(reader);
		base = new WumpursWorldKnowBase();
		curentField = map.get(pos);
		work();
		System.out.println("----END----");

	}

	/**
	 * Picard goes on a adventure.
	 */
	private static void work() {

		boolean posChanged = true;
		List<Point> points;
		LinkedList<Point> doubleCheck;
		List<Point> pointsVisited = new LinkedList<Point>();
		unsafe = new LinkedList<Point>();
		while (posChanged) {

			System.out.println(curentField.getCoords());
			if (curentField.isTele()) {
				System.out.println("Beam me up scoty");
				return;
			}
			if (curentField.isPit()) {
				System.out.println("OOOH nooo its hoooot!!");
				return;
			}
			if (curentField.isWumpus()) {
				System.out.println("Oh what a nice giant snail!");
				System.out.println("Oh you can speak?");
				System.out.println("Wait...What are you doing?");
				System.out.println("Nooo help me, i am geting eaten alive!!");
				return;
			}
			posChanged = false;

			base.updateBase(curentField);
			pointsVisited.add(pos);

			points = new LinkedList<Point>();
			if (pos.getX() > 1) {
				points.add(new Point(pos.getX() - 1, pos.getY()));
			}

			if (pos.getX() < dimension) {
				points.add(new Point(pos.getX() + 1, pos.getY()));

			}

			if (pos.getY() > 1) {
				points.add(new Point(pos.getX(), pos.getY() - 1));

			}
			if (pos.getY() < dimension) {
				points.add(new Point(pos.getX(), pos.getY() + 1));
			}

			Collections.sort(points);
			for (Point p : points) {

				if (base.isTeleporter(p)) {
					pos = p;
					curentField = map.get(pos);
					posChanged = true;
					continue;

				}

			}
			for (Point p : points) {
				if (base.isSafe(p) && (!pointsVisited.contains(p))
						&& (!safe.contains(p))) {
					safe.add(p);
				}

			}

			for (Point p : points) {
				if (!base.isDead(p) && (!pointsVisited.contains(p))
						&& (!safe.contains(p))) {
					unsafe.add(p);
				}
			}
			if (safe.size() > 0) {
				Collections.sort(safe);
				while ((!posChanged) && (!safe.isEmpty())) {
					Point p = safe.pollFirst();
					if (!pointsVisited.contains(p)) {
						posChanged = true;
						pos = p;
						curentField = map.get(pos);
					}
				}
			}
			if (!unsafe.isEmpty() && !posChanged) {
				doubleCheck = new LinkedList<Point>();
				for (Point p : unsafe) {
					if (base.isSafe(p) && (!pointsVisited.contains(p))
							&& (!safe.contains(p))) {
						safe.add(p);
					}

				}
				if (!safe.isEmpty()) {
					Collections.sort(safe);
					posChanged = true;
					pos = safe.pollFirst();
					curentField = map.get(pos);
				}
				if (!posChanged) {
					for (Point p : unsafe) {
						if (!base.isDead(p) && (!pointsVisited.contains(p))
								&& (!safe.contains(p))) {
							doubleCheck.add(p);
						}
					}
				}
				unsafe = new LinkedList<Point>(doubleCheck);
				Collections.sort(unsafe);
				while ((!posChanged) && (!unsafe.isEmpty())) {
					Point p = unsafe.pollFirst();
					if (!pointsVisited.contains(p)) {
						posChanged = true;
						pos = p;
						curentField = map.get(pos);
					}
				}
				Collections.sort(unsafe);
			}

		}

	}

	/**
	 * Shows the given map.
	 */
	private static void showMap() {
		Set<Point> pointSet = map.keySet();
		for (Point p : pointSet) {
			System.out.println(map.get(p));
		}
	}

	/**
	 * Parses the map from the file.
	 * 
	 * @param reader
	 *            the reader of the input we read from.
	 */
	private static void parseInput(BufferedReader reader) {
		String line = "";
		Field f;
		try {
			line = reader.readLine();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while (line != null) {
			f = Field.parse(line);
			if (dimension < f.getCoords().getX()) {
				dimension = f.getCoords().getX();
			}
			map.put(f.getCoords(), f);
			try {
				line = reader.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

}
