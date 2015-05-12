package fer.unizg.ui.lab1;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class Searcher {
	// The map of nodes.
	private static HashMap<Point, FieldType> nodes;
	// Heuristic map.
	private static HashMap<Point, FieldType> hNodes;
	// Dimension of the map.
	private static int dimension;
	// Finish position.
	private static Point finish;
	// Start position.
	private static Point position;
	// Teleporter coordinates.
	private static ArrayList<Point> tps;
	// Shutle cordinates.
	private static ArrayList<Point> buss;
	// Which method do we use.
	private static int method = 3;
	// number of opened nodes.
	private static int openedNodes = 0;
	private static HashMap<Point, Integer> hValue;

	public static void main(String[] args) {
		nodes = new HashMap<Point, FieldType>();
		tps = new ArrayList<Point>();
		buss = new ArrayList<Point>();
		System.out.println("Begin");

		/*
		 * BufferedReader reader = new BufferedReader(new InputStreamReader(
		 * System.in));
		 */

		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader("input.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		parseInput(reader);

		if (method == 1) {
			FieldType fin = UCS();
			// fin = nodes.get(new Point(5, 2));
			System.out.println("Minimal cost: " + fin.getCost());
			System.out.println("Opened nodes: " + openedNodes);

			String pathTaken = "";
			while (fin.getCordinates() != position) {
				pathTaken = "->\n(" + fin.getCordinates().y + ","
						+ fin.getCordinates().x + ") " + pathTaken;
				fin = fin.getParent();
			}
			pathTaken = "(" + fin.getCordinates().y + ","
					+ fin.getCordinates().x + ") " + pathTaken;
			System.out.println(pathTaken);

		}

		else if (method == 2) {
			FieldType fin = aStar();

			System.out.println("Minimal cost: " + fin.getCost());
			System.out.println("Opened nodes: " + openedNodes);

			String pathTaken = "";
			while (fin.getCordinates() != position) {
				pathTaken = "->\n(" + fin.getCordinates().y + ","
						+ fin.getCordinates().x + ") " + pathTaken;
				fin = fin.getParent();
			}
			pathTaken = "(" + fin.getCordinates().y + ","
					+ fin.getCordinates().x + ") " + pathTaken;
			System.out.println(pathTaken);

		} else {

			heuristicTwoInit();

			FieldType fin = aStar();

			System.out.println("Minimal cost: " + fin.getCost());
			System.out.println("Opened nodes: " + openedNodes);

			String pathTaken = "";
			while (fin.getCordinates() != position) {
				pathTaken = "->\n(" + fin.getCordinates().y + ","
						+ fin.getCordinates().x + ") " + pathTaken;
				fin = fin.getParent();
			}
			pathTaken = "(" + fin.getCordinates().y + ","
					+ fin.getCordinates().x + ") " + pathTaken;
			System.out.println(pathTaken);

		}
		System.out.println("End");
	}

	private static void heuristicTwoInit() {
		hNodes = new HashMap<Point, FieldType>();
		createHMap();
		int value;
		FieldType f;
		hValue = new HashMap<Point, Integer>();
		for (int x = 1; x <= dimension; x++) {
			for (int y = 1; y <= dimension; y++) {
				
				f = hNodes.get(new Point(x, y));
				f.setCost(0);
				value = heuristicTwo(f);
				hValue.put(new Point(x, y), value);

			}
		}

	}

	/**
	 * The unified cost search algorithm for blind searching.
	 * 
	 * @return the finish linked to the start.
	 */
	private static FieldType UCS() {
		System.out.println("UCS");
		FieldType pomParent;
		int saveCost;
		ArrayList<FieldType> open = new ArrayList<FieldType>();
		open.add(nodes.get(position));
		openedNodes = 0;

		FieldType head;

		ArrayList<FieldType> closed = new ArrayList<FieldType>();
		while (!open.isEmpty()) {
			head = open.get(0);
			openedNodes++;

			open.remove(0);
			closed.add(head);
			if (head.getCordinates() == finish) {
				return head;

			}
			ArrayList<FieldType> toExpand = head.getSuccesors();
			for (FieldType expanding : toExpand) {
				if (!closed.contains(expanding)) {
					if (open.contains(expanding)) {

						expanding = expanding.copy();
					}

					expanding.setParent(head);
					open.add(expanding);

				}
			}
			Collections.sort(open);

		}
		return null;

	}

	/**
	 * The A* algoritham for heuristic searching.
	 * 
	 * @return the finish cordinate linked to the start.
	 */
	private static FieldType aStar() {
		System.out.println("A*");
		FieldType pomParent;
		int saveCost;
		ArrayList<FieldType> open = new ArrayList<FieldType>();
		open.add(nodes.get(position));
		// openedNodes++;
		FieldType head;
		FieldType openedHead;
		FieldType openNode;

		ArrayList<FieldType> closed = new ArrayList<FieldType>();
		while (!open.isEmpty()) {
			head = open.get(0);
			openedNodes++;
			if (method == 2) {
				head.setH(heuristicOne(head));
			} else {
				head.setH(hValue.get(head.getCordinates()));
			}

			open.remove(0);
			closed.add(head);
			if (head.getCordinates() == finish) {
				return head;

			}
			ArrayList<FieldType> toExpand = head.getSuccesors();
			for (FieldType expanding : toExpand) {
				if ((closed.contains(expanding)) || (open.contains(expanding))) {
					if (open.contains(expanding)) {
						openedHead = expanding.copy();
						openedHead.setParent(head);
						if (method == 2) {
							openedHead.setH(heuristicOne(openedHead));
						} else {
							openedHead.setH(hValue.get(openedHead
									.getCordinates()));
						}
						if (closed.contains(expanding)) {
							openNode = closed.get(closed.indexOf(expanding));
							if (openNode.getCost() > openedHead.getCost()) {
								closed.remove(closed.indexOf(expanding));
								openNode = open.get(open.indexOf(expanding));
								if (openNode.getCost() > openedHead.getCost()) {
									open.remove(open.indexOf(expanding));
									open.add(openedHead);
									// openedNodes++;
								}
							}
						} else {
							openNode = open.get(open.indexOf(expanding));
							if (openNode.getCost() > openedHead.getCost()) {
								open.remove(open.indexOf(expanding));
								open.add(openedHead);
								// openedNodes++;
							}

						}

					} else {
						expanding = expanding.copy();
						if (method == 2) {
							expanding.setH(heuristicOne(expanding));
						} else {
							expanding
									.setH(hValue.get(expanding.getCordinates()));
						}

						expanding.setParent(head);
						openNode = closed.get(closed.indexOf(expanding));
						if (openNode.getCost() > expanding.getCost()) {
							closed.remove(closed.indexOf(expanding));
							open.add(expanding);
							// openedNodes++;
						}
					}

				} else {
					if (method == 2) {
						expanding.setH(heuristicOne(expanding));
					} else {
						expanding.setH(hValue.get(expanding.getCordinates()));
					}
					expanding.setParent(head);
					open.add(expanding);
					// openedNodes++;
				}
				Collections.sort(open);
			}

		}
		return null;

	}

	private static int heuristicOne(FieldType node) {
		return (int) (Math.abs(node.getCordinates().getX() - finish.getX()) + Math
				.abs(node.getCordinates().getY() - finish.getY()));
	}

	/**
	 * The second heuristic function.
	 * 
	 * @param node
	 *            for which we calculate the heuristic.
	 * @return the heuristic value.
	 */
	private static int heuristicTwo(FieldType node) {
		FieldType pomParent;
		int saveCost;
		ArrayList<FieldType> open = new ArrayList<FieldType>();
		FieldType initHead = hNodes.get(node.getCordinates());
		initHead.setCost(0);
		open.add(initHead);

		FieldType head;

		ArrayList<FieldType> closed = new ArrayList<FieldType>();

		// openedNodes++;

		FieldType openedHead;
		FieldType openNode;

		while (!open.isEmpty()) {
			head = open.get(0);

			head.setH(heuristicOne(head));

			open.remove(0);
			closed.add(head);
			if (head.getCordinates() == finish) {
				return head.getCost();

			}
			ArrayList<FieldType> toExpand = head.getSuccesors();
			for (FieldType expanding : toExpand) {
				if ((closed.contains(expanding)) || (open.contains(expanding))) {
					if (open.contains(expanding)) {
						openedHead = expanding.copy();
						openedHead.setParent(head);
						openedHead.heristicCost();
						openedHead.setH(heuristicOne(openedHead));

						if (closed.contains(expanding)) {
							openNode = closed.get(closed.indexOf(expanding));
							if (openNode.getCost() > openedHead.getCost()) {
								closed.remove(closed.indexOf(expanding));
								openNode = open.get(open.indexOf(expanding));
								if (openNode.getCost() > openedHead.getCost()) {
									open.remove(open.indexOf(expanding));
									open.add(openedHead);
									// openedNodes++;
								}
							}
						} else {
							openNode = open.get(open.indexOf(expanding));
							if (openNode.getCost() > openedHead.getCost()) {
								open.remove(open.indexOf(expanding));
								open.add(openedHead);
								// openedNodes++;
							}

						}

					} else {
						expanding = expanding.copy();

						expanding.setH(heuristicOne(expanding));

						expanding.setParent(head);
						expanding.heristicCost();
						openNode = closed.get(closed.indexOf(expanding));
						if (openNode.getCost() > expanding.getCost()) {
							closed.remove(closed.indexOf(expanding));
							open.add(expanding);
							// openedNodes++;
						}
					}

				} else {

					expanding.setH(heuristicOne(expanding));

					expanding.setParent(head);
					expanding.heristicCost();
					open.add(expanding);
					// openedNodes++;
				}
				Collections.sort(open);
			}

		}
		return -1;

	}

	/**
	 * Creates the heuristic map.
	 */
	private static void createHMap() {
		FieldType f;
		Point hP;
		int x;
		int y;

		for (y = 1; y <= dimension; y++) {
			for (x = 1; x <= dimension; x++) {
				hP = new Point(x, y);
				f = nodes.get(hP).copy();
				if (f.getType() == "tele") {
					((TeleportField) f).setLabel(1);
				}
				hNodes.put(hP, f);
			}

		}
		linkMap(hNodes, true);

	}

	/**
	 * Parses the input and inits it in a map.
	 * 
	 * @param reader
	 *            the reader we read from.
	 */
	private static void parseInput(BufferedReader reader) {
		String line = "";
		try {
			line = reader.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String[] fields = line.split(" ");
		dimension = fields.length;
		int y = 1;
		int x;
		for (x = 1; x <= dimension; x++) {
			putInMap(x, y, fields[x - 1], nodes, false);
		}
		while (y < dimension) {
			try {
				y++;
				line = reader.readLine();
				fields = line.split(" ");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			for (x = 1; x <= dimension; x++) {
				putInMap(x, y, fields[x - 1], nodes, false);
			}

		}
		linkMap(nodes, false);

	}

	/**
	 * Links the whole map.
	 * 
	 * @param map
	 *            the map which we link.
	 * @param flag
	 *            is this for heuristic use.
	 */
	private static void linkMap(HashMap<Point, FieldType> map, boolean flag) {
		if (!flag) {
			map.get(position).setCost(0);
		}
		for (int x = 1; x <= dimension; x++) {
			for (int y = 1; y <= dimension; y++) {
				Point p = new Point(x, y);

				map.get(p).setSuccesors(links(p, flag, map));
			}
		}

	}

	/**
	 * Gets the links of a node.
	 * 
	 * @param pos
	 *            the position on the map.
	 * @param h
	 *            is this for the heuristic use.
	 * @param map
	 *            the map which we use for the links.
	 * @return all the nodes conected to the pos node.
	 */
	private static ArrayList<FieldType> links(Point pos, boolean h,
			HashMap<Point, FieldType> map) {
		ArrayList<FieldType> susedi = new ArrayList<FieldType>();
		Point susedCord;
		if ((pos.x > 1) && (pos.x != (dimension / 2 + 1))) {
			susedCord = new Point(pos.x - 1, pos.y);
			susedi.add(map.get(susedCord));
		}
		if ((pos.x != (dimension / 2)) && (pos.x < dimension)) {
			susedCord = new Point(pos.x + 1, pos.y);
			susedi.add(map.get(susedCord));
		}
		if (pos.y > 1) {
			susedCord = new Point(pos.x, pos.y - 1);
			susedi.add(map.get(susedCord));
		}
		if (pos.y < dimension) {
			susedCord = new Point(pos.x, pos.y + 1);
			susedi.add(map.get(susedCord));
		}
		if (map.get(pos).getType().equals("tele")) {
			for (Point t : tps) {
				if (((TeleportField) (map.get(pos))).getLabel() == ((TeleportField) (map
						.get(t))).getLabel()) {
					if (h) {
						if (t.x > (dimension / 2)
								&& (pos.getX() <= (dimension / 2))) {
							susedi.add(map.get(t));
						}

					} else {
						if (t != pos) {
							susedi.add(map.get(t));
						}
					}
				}
			}
		}
		if (map.get(pos).getType().equals("shutle")) {
			for (Point b : buss) {

				if (b != pos) {
					susedi.add(map.get(b));
				}

			}
		}

		return susedi;
	}

	/**
	 * Puts a new node in the map.
	 * 
	 * @param x
	 *            coordinate.
	 * @param y
	 *            coordinate.
	 * @param fieldString
	 *            the string we parse.
	 * @param map
	 *            in which we put in.
	 * @param flag
	 */
	private static void putInMap(int x, int y, String fieldString,
			HashMap<Point, FieldType> map, boolean flag) {
		Point cord = new Point(x, y);
		FieldType field = null;
		fieldString = fieldString.trim();
		if (fieldString.startsWith("T")) {
			if (flag) {
				int label = 1;
				field = new TeleportField(cord, label);
				tps.add(cord);
			} else {
				int label = Integer.parseInt(fieldString.substring(1));
				field = new TeleportField(cord, label);
				tps.add(cord);
			}

		} else if (fieldString.startsWith("S")) {
			field = new ShutleField(cord);
			buss.add(cord);
		} else if (fieldString.startsWith("P")) {
			position = cord;
			field = new NormalField(cord, 0);

		} else if (fieldString.startsWith("C")) {
			finish = cord;
			field = new NormalField(cord, 0);
		} else {
			int height = Integer.parseInt(fieldString);
			field = new NormalField(cord, height);
		}
		map.put(cord, field);

	}

}
