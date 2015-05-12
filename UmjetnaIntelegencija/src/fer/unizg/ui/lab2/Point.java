package fer.unizg.ui.lab2;

public class Point implements Comparable<Point> {
	// The x coordinate.
	private int x;
	// The y coordinate.
	private int y;

	/**
	 * Basic constructor.
	 * 
	 * @param x
	 *            the x coordinate.
	 * @param y
	 *            the y coordinate.
	 */
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Parses a string that has a "(x,y)" format into a Point with those
	 * coordinates.
	 * 
	 * @param line
	 *            the line we parse.
	 * @return a new point that has the coordinates from the string.
	 */
	public static Point parse(String line) {
		String twoCords = line.substring(line.indexOf("(") + 1,
				line.indexOf(")")).trim();
		String[] cords = twoCords.split(",");
		int x = Integer.parseInt(cords[0]);
		int y = Integer.parseInt(cords[1]);

		return new Point(x, y);
	}

	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * @param x
	 *            the x to set
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}

	/**
	 * @param y
	 *            the y to set
	 */
	public void setY(int y) {
		this.y = y;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "(" + x + "," + y + ")";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Point other = (Point) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}

	public int compareTo(Point other) {
		return (this.getX() * 10 + this.getY() - other.getX() * 10 - other
				.getY());
	}

}
