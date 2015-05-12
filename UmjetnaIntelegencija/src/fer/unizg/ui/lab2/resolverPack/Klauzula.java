package fer.unizg.ui.lab2.resolverPack;

import java.util.LinkedList;
/**
 * Basic class for saving atoms.
 * @author Ivan
 *
 */
public class Klauzula {
	//The atoms.
	private LinkedList<String> atoms;
/**
 * Basic constructor which parses a line example "Av-B"
 * @param line the line we get the atoms from
 */
	public Klauzula(String line) {
		atoms = new LinkedList<String>();
		line = line.trim();
		String[] parted = line.split("v");
		for (String a : parted) {
			atoms.add(a);
		}
	}
/**
 * Basic constructor.
 * @param list ,list of atoms.
 */
	public Klauzula(LinkedList<String> list) {
		atoms = new LinkedList<String>();
		atoms.addAll(list);
	}
/**
 * Checks if the other klauzul is the same as this.
 * @param k the other klauzul.
 * @return true if it is the same.
 */
	public boolean same(Klauzula k) {
		LinkedList<String> secondAtoms = k.getAtoms();

		if (this.atoms.size() != secondAtoms.size()) {
			return false;
		}
		if (atoms.containsAll(secondAtoms)) {
			return true;
		}

		return false;
	}
/**
 * Checks if this klauzul is a simpler form of the other klauzul.
 * @param k the other klauzul.
 * @return true if it is a simpler form.
 */
	public boolean simplerThen(Klauzula k) {
		LinkedList<String> secondAtoms = k.getAtoms();
		if (secondAtoms.containsAll(atoms)) {
			if (secondAtoms.size() > atoms.size()) {
				return true;
			}
		}
		return false;

	}

	/**
	 * @return the atoms
	 */
	public LinkedList<String> getAtoms() {
		return atoms;
	}

	/**
	 * @param atoms
	 *            the atoms to set
	 */
	public void setAtoms(LinkedList<String> atoms) {
		this.atoms = atoms;
	}
/**
 * Checks if the other klauzul k contains a negative form of a atom from this klauzul.
 * @param k the other klauzul we check with.
 * @return true if it contains.
 */
	public boolean containsNeg(Klauzula k) {
		LinkedList<String> otherList = k.getAtoms();
		for (String a : this.atoms) {
			if (a.startsWith("-")) {
				if (otherList.contains(a.substring(1))) {
					return true;
				}
			} else {
				if (otherList.contains("-" + a)) {
					return true;
				}
			}
		}
		return false;
	}
/**
 * Gets the atom that is neg in the klauzul k
 * @param k the klauzul we check with.
 * @return the same atom.
 */
	public String getSame(Klauzula k) {
		LinkedList<String> otherList = k.getAtoms();
		for (String a : this.atoms) {
			if (a.startsWith("-")) {
				if (otherList.contains(a.substring(1))) {
					return a;
				}
			} else {
				if (otherList.contains("-" + a)) {
					return a;
				}
			}
		}
		return null;

	}
/**
 * Tries to resolve the klauzul with the given klauzul k.
 * @param k the klauzul we resolv with.
 * @return the new resolved klauzul.
 */
	public Klauzula resolve(Klauzula k) {
		LinkedList<String> toRet = new LinkedList<String>();
		LinkedList<String> otherList = k.getAtoms();
		LinkedList<String> copyThis = this.getAtoms();

		if (copyThis.size() > 1) {
			copyThis.remove(this.getSame(k));
			toRet.addAll(copyThis);
		}
		
		if (otherList.size() > 1) {
			otherList.remove(k.getSame(this));
			toRet.addAll(otherList);
		}

		return new Klauzula(toRet);
	}

	public boolean isEmpty() {
		return this.atoms.isEmpty();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {

		String ret = "";
		for (String a : atoms) {
			if (ret.isEmpty()) {
				ret = a;
			} else {
				ret = ret + "v" + a;
			}
		}
		return ret;
	}
	

}
