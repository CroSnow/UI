package fer.unizg.ui.lab1;

import java.awt.Point;

/**
 * Class for a normal field.
 * 
 * @author Ivan
 * 
 */
public class NormalField extends AbstractField {
	/**
	 * Basic constructor for a normal field.
	 * 
	 * @param cords
	 *            the coordinates for the field.
	 * @param heigh
	 *            the height of the field.
	 */
	NormalField(Point cords, int heigh) {
		super(cords, heigh, "normal");
		// TODO Auto-generated constructor stub
	}

	/**
	 * Calculates the cost of the path
	 */
	@Override
	public int cost() {
		return Math.abs(this.getParent().getHeight() - this.getHeight());
	}

	/**
	 * Returns a new copy of this field.
	 */
	public FieldType copy() {
		NormalField field = new NormalField(this.cordinates, this.height);
		field.setSuccesors(this.getSuccesors());
		return field;
	}

	/**
	 * The cost calculated for the heuristic function.
	 */
	public int heristicCost() {
		this.cost = this.parent.getCost() + 1;
		return this.cost;
	}

}
