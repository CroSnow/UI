package fer.unizg.ui.lab1;

import java.awt.Point;

/**
 * The class for a Shutle field on the map.
 * 
 * @author Ivan
 *
 */
public class ShutleField extends AbstractField {
	/**
	 * Basic constructor for the shutle field.
	 * 
	 * @param cords
	 *            the coordinates for this field.
	 */
	ShutleField(Point cords) {
		super(cords, 0, "shutle");
		// TODO Auto-generated constructor stub
	}

	/**
	 * Calculates the cost of the path
	 */
	@Override
	public int cost() {
		Point parentCords = this.getParent().getCordinates();
		if (this.getParent().getType() == "shutle") {
			return (int) (3 * (Math.abs(this.cordinates.getX()
					- parentCords.getX()) + Math.abs(this.cordinates.getY()
					- parentCords.getY())));
		} else {
			return Math.abs(this.getParent().getHeight() - this.getHeight());
		}
	}

	/**
	 * Returns a new copy of this field.
	 */
	public FieldType copy() {
		ShutleField field = new ShutleField(this.cordinates);
		field.setSuccesors(this.getSuccesors());
		return field;
	}

	/**
	 * The cost calculated for the heuristic function.
	 */
	public int heristicCost() {
		Point parentCords = this.getParent().getCordinates();
		if (this.getParent().getType() == "shutle") {
			this.cost = (int) (3 * (Math.abs(this.cordinates.getX()
					- parentCords.getX()) + Math.abs(this.cordinates.getY()
					- parentCords.getY())));
		} else {
			this.cost =1;
		}
		this.cost=this.getParent().getCost()+this.cost;
		return this.cost;
	}

}
