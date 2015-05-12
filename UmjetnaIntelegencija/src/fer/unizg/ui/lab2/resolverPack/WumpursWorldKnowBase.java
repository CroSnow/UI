package fer.unizg.ui.lab2.resolverPack;

import java.util.LinkedList;

import fer.unizg.ui.lab2.Field;
import fer.unizg.ui.lab2.Point;

public class WumpursWorldKnowBase {
	// The base we use for backup.
	private KnowledgeBase base;
	// The base we work from.
	private KnowledgeBase workBase;

	// different points we use.
	private Point up;
	private Point left;
	private Point down;
	private Point right;
	private Point leftUp;
	private Point rightUp;
	private Point leftDown;
	private Point rightDown;
	private Point upp;
	private Point downn;
	private Point leftt;
	private Point rightt;

	/**
	 * Creates a new Wumpurs World Knowledge base.
	 * 
	 * @param dimension
	 */
	public WumpursWorldKnowBase() {
		this.base = new KnowledgeBase();
	}

	/**
	 * Updates the knowledge base with addition info from a field.
	 * 
	 * @param f
	 *            the field we update from.
	 */
	public void updateBase(Field f) {
		if (f.isBreeze()) {
			base.addKlauzul("B" + f.getCoords());
		} else {
			base.addKlauzul("-B" + f.getCoords());
		}
		if (f.isGlow()) {
			base.addKlauzul("G" + f.getCoords());
		} else {
			base.addKlauzul("-G" + f.getCoords());
		}
		if (f.isStench()) {
			base.addKlauzul("S" + f.getCoords());
		} else {
			base.addKlauzul("-S" + f.getCoords());
		}
	}

	/**
	 * Resets the work base.
	 */
	private void resetWorkBase() {
		this.workBase = new KnowledgeBase();
		this.workBase.setBase(new LinkedList<Klauzula>(base.getBase()));
	}

	/**
	 * Checks if on the given field is a teleporter.
	 * 
	 * @param p
	 *            the coordinates of the field we check.
	 * @return true if there is a teleporter.
	 */
	public boolean isTeleporter(Point p) {
		this.definePoints(p);
		this.resetWorkBase();

		this.workBase.addResolvant("-G" + left + "v-G" + up + "v-G" + right);
		if (this.workBase.resolve()) {
			return true;
		}
		this.resetWorkBase();

		this.workBase.addResolvant("-G" + up + "v-G" + right + "v-G" + down);
		if (this.workBase.resolve()) {
			return true;
		}
		this.resetWorkBase();
		this.workBase.addResolvant("-G" + right + "v-G" + down + "v-G" + left);
		if (this.workBase.resolve()) {
			return true;
		}
		this.resetWorkBase();
		this.workBase.addResolvant("-G" + down + "v-G" + left + "v-G" + up);
		if (this.workBase.resolve()) {
			return true;
		}
		this.resetWorkBase();

		this.workBase.addResolvant("G" + leftUp + "v-G" + up + "v-G" + left);
		if (this.workBase.resolve()) {
			return true;
		}
		this.resetWorkBase();
		this.workBase.addResolvant("G" + rightUp + "v-G" + up + "v-G" + right);
		if (this.workBase.resolve()) {
			return true;
		}
		this.resetWorkBase();
		this.workBase
				.addResolvant("G" + leftDown + "v-G" + down + "v-G" + left);
		if (this.workBase.resolve()) {
			return true;
		}
		this.resetWorkBase();
		this.workBase.addResolvant("G" + rightDown + "v-G" + right + "v-G"
				+ down);
		if (this.workBase.resolve()) {
			return true;
		}
		this.resetWorkBase();
		this.workBase.addResolvant("G" + rightDown + "vG" + leftDown + "vG"
				+ downn + "v-G" + down);
		this.workBase.addResolvant("G" + rightDown + "vG" + rightUp + "vG"
				+ rightt + "v-G" + right);
		this.workBase.addResolvant("G" + rightUp + "vG" + leftUp + "vG" + upp
				+ "v-G" + up);
		this.workBase.addResolvant("G" + leftDown + "vG" + leftUp + "vG"
				+ leftt + "v-G" + left);
		if (this.workBase.resolve()) {
			return true;
		}

		return false;
	}

	/**
	 * Checks if the field is a safe dead.
	 * 
	 * @param p
	 *            the coordinates of the field we check.
	 * @return true if there is a pit or wumpus.
	 */
	public boolean isDead(Point p) {
		return (this.isWump(p) || this.isPit(p));
	}

	/**
	 * Checks in the knowledge base if the given point p is a field where the
	 * Wumpus lives.
	 * 
	 * @param p
	 *            the field we check.
	 * @return true if there is a wumpus.
	 */
	public boolean isWump(Point p) {
		this.definePoints(p);
		this.resetWorkBase();

		this.workBase.addResolvant("-S" + left + "v-S" + up + "v-S" + right);
		this.workBase.addResolvant("-S" + up + "v-S" + right + "v-S" + down);
		this.workBase.addResolvant("-S" + right + "v-S" + down + "v-S" + left);
		this.workBase.addResolvant("-S" + down + "v-S" + left + "v-S" + up);

		this.workBase.addResolvant("S" + leftUp + "v-S" + up + "v-S" + left);
		this.workBase.addResolvant("S" + rightUp + "v-S" + up + "v-S" + right);
		this.workBase
				.addResolvant("S" + leftDown + "v-S" + down + "v-S" + left);
		this.workBase.addResolvant("S" + rightDown + "v-S" + right + "v-S"
				+ down);

		this.workBase.addResolvant("S" + rightDown + "vS" + leftDown + "vS"
				+ downn + "v-S" + down);
		this.workBase.addResolvant("S" + rightDown + "vS" + rightUp + "vS"
				+ rightt + "v-S" + right);
		this.workBase.addResolvant("S" + rightUp + "vS" + leftUp + "vS" + upp
				+ "v-S" + up);
		this.workBase.addResolvant("S" + leftDown + "vS" + leftUp + "vS"
				+ leftt + "v-S" + left);

		return this.workBase.resolve();

	}

	/**
	 * Checks in the knowledge base if the given point p is a field where the a
	 * pit is.
	 *
	 * @param p
	 *            coordinates we check.
	 * @return true if there is a pit.
	 */
	public boolean isPit(Point p) {
		this.definePoints(p);
		this.resetWorkBase();

		this.workBase.addResolvant("-B" + left + "v-B" + up + "v-B" + right);
		this.workBase.addResolvant("-B" + up + "v-B" + right + "v-B" + down);
		this.workBase.addResolvant("-B" + right + "v-B" + down + "v-B" + left);
		this.workBase.addResolvant("-B" + down + "v-B" + left + "v-B" + up);

		this.workBase.addResolvant("B" + leftUp + "v-B" + up + "v-B" + left);
		this.workBase.addResolvant("B" + rightUp + "v-B" + up + "v-B" + right);
		this.workBase
				.addResolvant("B" + leftDown + "v-B" + down + "v-B" + left);
		this.workBase.addResolvant("B" + rightDown + "v-B" + right + "v-B"
				+ down);

		this.workBase.addResolvant("P" + rightDown + "vP" + leftDown + "vP"
				+ downn + "v-P" + down);
		this.workBase.addResolvant("P" + rightDown + "vP" + rightUp + "vP"
				+ rightt + "v-P" + right);
		this.workBase.addResolvant("P" + rightUp + "vP" + leftUp + "vP" + upp
				+ "v-P" + up);
		this.workBase.addResolvant("P" + leftDown + "vP" + leftUp + "vP"
				+ leftt + "v-P" + left);

		return this.workBase.resolve();
	}

	/**
	 * Checks if the given coordinates are safe.
	 * 
	 * @param p
	 *            the point representation of the coordinates.
	 * @return true if safe.
	 */
	public boolean isSafe(Point p) {
		return (this.notPit(p) && this.notWump(p));

	}

	/**
	 * Checks in the knowledge base if the given point p is not a field where
	 * the a pit is.
	 *
	 * @param p
	 *            coordinates we check.
	 * @return true if there is no pit.
	 */
	public boolean notPit(Point p) {
		this.definePoints(p);
		this.resetWorkBase();

		this.workBase.addResolvant("B" + left);
		this.workBase.addResolvant("B" + up);
		this.workBase.addResolvant("B" + right);
		this.workBase.addResolvant("B" + down);

		return this.workBase.resolve();
	}

	/**
	 * Checks in the knowledge base if the given point p is not a field where
	 * the Wumpus lives.
	 * 
	 * @param p
	 *            the field we check.
	 * @return true if there is no wumpus.
	 */
	public boolean notWump(Point p) {
		this.definePoints(p);
		this.resetWorkBase();

		this.workBase.addResolvant("S" + left);
		this.workBase.addResolvant("S" + up);
		this.workBase.addResolvant("S" + right);
		this.workBase.addResolvant("S" + down);

		return this.workBase.resolve();
	}

	/**
	 * Defines the surounding points for the Point P.
	 * 
	 * @param p
	 *            the point we calculate the neighbors from.
	 */
	private void definePoints(Point p) {

		up = new Point(p.getX(), p.getY() + 1);
		upp = new Point(p.getX(), p.getY() + 2);
		left = new Point(p.getX() - 1, p.getY());
		leftt = new Point(p.getX() - 2, p.getY());
		down = new Point(p.getX(), p.getY() - 1);
		downn = new Point(p.getX(), p.getY() - 2);
		right = new Point(p.getX() + 1, p.getY());
		rightt = new Point(p.getX() + 2, p.getY());
		leftUp = new Point(p.getX() - 1, p.getY() + 1);
		rightUp = new Point(p.getX() + 1, p.getY() + 1);
		leftDown = new Point(p.getX() - 1, p.getY() - 1);
		rightDown = new Point(p.getX() + 1, p.getY() - 1);

	}

}
