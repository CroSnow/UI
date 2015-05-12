package fer.unizg.ui.lab1;

import java.awt.Point;

/**
 * The class for a teleport field.
 * 
 * @author Ivan
 *
 */
public class TeleportField extends AbstractField {
	// The label of the teleport.
	private int label;

	/**
	 * The basic constructor.
	 * 
	 * @param cords
	 *            the cordinates for the teleport field.
	 * @param label
	 *            the label of the teleport field.
	 */
	TeleportField(Point cords, int label) {
		super(cords, 0, "tele");
		this.label = label;
		// TODO Auto-generated constructor stub
	}

	/**
	 * Calculates the cost of the path
	 */
	@Override
	public int cost() {
		if (this.getParent().getType() == "tele") {
			Point parentCords = this.getParent().getCordinates();
			return (int) (Math.abs(this.cordinates.getX() - parentCords.getX()) + Math
					.abs(this.cordinates.getY() - parentCords.getY()));
		} else {
			return Math.abs(this.getParent().getHeight() - this.getHeight());
		}
	}

	/**
	 * The cost calculated for the heuristic function.
	 */
	public int heristicCost() {
		Point parentCords = this.getParent().getCordinates();
		if (this.getParent().getType() == "tele") {
			this.cost = (int) ((Math.abs(this.cordinates.getX()
					- parentCords.getX()) + Math.abs(this.cordinates.getY()
					- parentCords.getY())));
		} else {
			this.cost =1;
					
		}
		this.cost=this.getParent().getCost()+this.cost;
		return this.cost;
	}

	/**
	 * Returns the label.
	 * 
	 * @return the label.
	 */
	public int getLabel() {
		return label;
	}

	/**
	 * Sets the label for this field.
	 * 
	 * @param label
	 *            the label.
	 */
	public void setLabel(int label) {
		this.label = label;
	}

	/**
	 * Returns a new copy of this field.
	 */
	public FieldType copy() {
		TeleportField field = new TeleportField(this.cordinates, label);
		field.setSuccesors(this.getSuccesors());
		return field;
	}

}
