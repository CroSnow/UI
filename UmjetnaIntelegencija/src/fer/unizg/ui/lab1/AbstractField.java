package fer.unizg.ui.lab1;

import java.awt.Point;
import java.util.ArrayList;

/**
 * @author Ivan
 *
 */
public abstract class AbstractField implements FieldType {
	// The coordinates for this field.
	protected Point cordinates;
	// The height for this field.
	protected int height;
	// List of children.
	protected ArrayList<FieldType> succesors;
	// The parent for this node.
	protected FieldType parent;
	// The type of the field.
	protected String type = "";
	// The real cost.
	protected int cost;
	// The heuristic cost.
	protected int h = 0;
	// The heuristic cost.
	protected int hCost = 0;

	/**
	 * The constructor for the abstract Field.
	 * 
	 * @param cords
	 *            the cordinates of the field.
	 * @param heigh
	 *            the height of the field.
	 * @param type
	 *            the type of the field.
	 */
	AbstractField(Point cords, int heigh, String type) {
		this.cordinates = cords;
		this.height = heigh;
		this.type = type;
		this.succesors = new ArrayList<FieldType>();
	}

	/**
	 * @return the hCost
	 */
	public int gethCost() {
		return hCost;
	}

	/**
	 * @param hCost
	 *            the hCost to set
	 */
	public void sethCost(int hCost) {
		this.hCost = hCost;
	}

	/**
	 * @return the cost
	 */
	public int getCost() {
		return cost;
	}

	/**
	 * @param cost
	 *            the cost to set
	 */
	public void setCost(int cost) {
		this.cost = cost;
	}

	/**
	 * @return the cordinates
	 */
	public Point getCordinates() {
		return cordinates;
	}

	/**
	 * 
	 * @param cordinates
	 *            the cordinates to set
	 */
	public void setCordinates(Point cordinates) {
		this.cordinates = cordinates;
	}

	/**
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * 
	 * @param height
	 *            the height to set
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	/**
	 * @return the succesors
	 */
	public ArrayList<FieldType> getSuccesors() {
		return succesors;
	}

	/**
	 * @param succesors
	 *            the succesors to set
	 */
	public void setSuccesors(ArrayList<FieldType> succesors) {
		this.succesors = succesors;
	}

	/**
	 * @return the parent
	 */
	public FieldType getParent() {
		return parent;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @param parent
	 *            the parent to set
	 */
	public void setParent(FieldType parent) {
		this.parent = parent;
		this.cost = parent.getCost() + this.cost();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see fer.unizg.ui.lab1.FieldType#expand()
	 */
	@Override
	public ArrayList<FieldType> expand() {
		// TODO Auto-generated method stub
		return null;
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
		result = prime * result
				+ ((cordinates == null) ? 0 : cordinates.hashCode());
		return result;
	}

	/**
	 * Equals if 2 coordinates of a field are equal.
	 */
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractField other = (AbstractField) obj;
		if (cordinates == null) {
			if (other.cordinates != null)
				return false;
		} else if (!cordinates.equals(other.cordinates))
			return false;
		return true;
	}

	/**
	 * Compares two field by its costs.
	 */
	@Override
	public int compareTo(Object other) {
		return this.compareCost((AbstractField) other);
	}

	/**
	 * Compares two field by its costs.
	 * 
	 * @param other
	 *            field that we compare to.
	 * @return the diference in cost.
	 */
	public int compareCost(AbstractField other) {
		return (this.cost + this.h) - (other.getCost() + other.getH());
	}

	/**
	 * @return the h
	 */
	public int getH() {
		return h;
	}

	/**
	 * @param h
	 *            the h to set
	 */
	public void setH(int h) {
		this.h = h;
	}

}
