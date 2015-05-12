package fer.unizg.ui.lab2;

/**
 * The class which saves a field and shows what attribute it has. If a flag is
 * false that means this field does not have that attribute. If a flag is true
 * that means this field has that attribute.
 * 
 * @author Ivan
 *
 */
public class Field {
	// The coordinates of this field.
	private Point coords;
	// The flag for the stench of the field.
	private boolean stench = false;
	// The flag for the breeze of this field.
	private boolean breeze = false;
	// The flag for the glow of this field.
	private boolean glow = false;
	// The flag for a wumpus of this field.
	private boolean wumpus = false;
	// The flag that shows if this field is has a pit.
	private boolean pit = false;
	// The flag that shows if this field has a teleporter.
	private boolean tele = false;

	/**
	 * Basic constructor which gets only a point and sets all flags to false;
	 * 
	 * @param p
	 */
	public Field(Point p) {
		this.coords = p;
	}

	/**
	 * Parses the line and returns a new Field with the given arguments. By
	 * which the line has to be "(x,y) S=0|1 B=0|1 G=0|1 W=0|1 P=0|1 T=0|1".
	 * 
	 * @param line
	 *            the line we parse.
	 * @return the parsed Field.
	 */
	public static Field parse(String line) {
		Point p = Point.parse(line);
		Field f = new Field(p);
		String flags = line.substring(line.indexOf(")") + 1).trim();
		while (!flags.isEmpty()) {
			if (flags.startsWith("S")) {
				if (flags.charAt(2) == '1') {
					f.setStench(true);
				}
				flags = flags.substring(3).trim();

			}
			if (flags.startsWith("B")) {
				if (flags.charAt(2) == '1') {
					f.setBreeze(true);
				}
				flags = flags.substring(3).trim();

			}
			if (flags.startsWith("G")) {
				if (flags.charAt(2) == '1') {
					f.setGlow(true);
				}
				flags = flags.substring(3).trim();

			}
			if (flags.startsWith("W")) {
				if (flags.charAt(2) == '1') {
					f.setWumpus(true);
				}
				flags = flags.substring(3).trim();

			}
			if (flags.startsWith("P")) {
				if (flags.charAt(2) == '1') {
					f.setPit(true);
				}
				flags = flags.substring(3).trim();

			}
			if (flags.startsWith("T")) {
				if (flags.charAt(2) == '1') {
					f.setTele(true);
				}
				flags = "";

			}
		}
		return f;
	}

	/**
	 * @return the coords
	 */
	public Point getCoords() {
		return coords;
	}

	/**
	 * @param coords
	 *            the coords to set
	 */
	public void setCoords(Point coords) {
		this.coords = coords;
	}

	/**
	 * @return the stench
	 */
	public boolean isStench() {
		return stench;
	}

	/**
	 * @param stench
	 *            the stench to set
	 */
	public void setStench(boolean stench) {
		this.stench = stench;
	}

	/**
	 * @return the breeze
	 */
	public boolean isBreeze() {
		return breeze;
	}

	/**
	 * @param breeze
	 *            the breeze to set
	 */
	public void setBreeze(boolean breeze) {
		this.breeze = breeze;
	}

	/**
	 * @return the glow
	 */
	public boolean isGlow() {
		return glow;
	}

	/**
	 * @param glow
	 *            the glow to set
	 */
	public void setGlow(boolean glow) {
		this.glow = glow;
	}

	/**
	 * @return the wumpus
	 */
	public boolean isWumpus() {
		return wumpus;
	}

	/**
	 * @param wumpus
	 *            the wumpus to set
	 */
	public void setWumpus(boolean wumpus) {
		this.wumpus = wumpus;
	}

	/**
	 * @return the pit
	 */
	public boolean isPit() {
		return pit;
	}

	/**
	 * @param pit
	 *            the pit to set
	 */
	public void setPit(boolean pit) {
		this.pit = pit;
	}

	/**
	 * @return the tele
	 */
	public boolean isTele() {
		return tele;
	}

	/**
	 * @param tele
	 *            the tele to set
	 */
	public void setTele(boolean tele) {
		this.tele = tele;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return coords + " S=" + stench + " B=" + breeze + " G=" + glow + " W="
				+ wumpus + " P=" + pit + " T=" + tele;
	}

}
