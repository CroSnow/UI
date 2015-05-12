package fer.unizg.ui.lab1;

import java.awt.Point;
import java.util.ArrayList;

/**
 * An interface for the fields of the planet.
 * 
 * @author Ivan
 *
 */
public interface FieldType extends Comparable {

	public ArrayList<FieldType> expand();
	public int cost();
	public int getHeight();
	public 	Point getCordinates();
	public String getType();
	public void setParent(FieldType parent);
	public void setSuccesors(ArrayList<FieldType> succesors) ;
	public ArrayList<FieldType> getSuccesors();
	public void setCost(int cost);
	public int getCost();
	public FieldType getParent();
	public FieldType copy();
	public void setH(int h);
	public int getH();
	public int gethCost();
	public void sethCost(int hCost);
	public int heristicCost();
	

}
