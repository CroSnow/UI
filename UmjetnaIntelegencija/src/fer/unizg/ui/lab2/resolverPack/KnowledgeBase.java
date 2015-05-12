package fer.unizg.ui.lab2.resolverPack;

import java.util.LinkedList;

/**
 * A basic knowledbe base.
 * 
 * @author Ivan
 *
 */
public class KnowledgeBase {
	// The knowledge base we prove with.
	private LinkedList<Klauzula> base;
	// List of resolvants that we try to prove.
	private LinkedList<Klauzula> resolvant;

	/**
	 * Basic constructor.
	 */
	public KnowledgeBase() {
		base = new LinkedList<Klauzula>();
		resolvant = new LinkedList<Klauzula>();
	}

	/**
	 * Adds a klauzul in the knowledge base.
	 * 
	 * @param line
	 *            the klauzul.
	 */
	public void addKlauzul(String line) {
		this.base.add(new Klauzula(line));
	}

	/**
	 * Adds a klauzul in the knowledge base.
	 * 
	 * @param k
	 *            the klauzul.
	 */
	public void addKlauzul(Klauzula k) {
		this.base.add(k);
	}

	/**
	 * Adds a resolvant (in negative form)
	 * 
	 * @param k
	 *            the resolvant.
	 */
	public void addResolvant(Klauzula k) {
		this.resolvant.add(k);
	}

	/**
	 * Adds a resolvant (in negative form)
	 * 
	 * @param line
	 *            the resolvant.
	 */
	public void addResolvant(String line) {
		this.resolvant.add(new Klauzula(line));
	}

	/**
	 * Resolves the Resolvant with the base.
	 * 
	 * @return true if the resolvant coud be proven.
	 */
	public boolean resolve() {
		Klauzula k;
		this.base.addAll(resolvant);
		boolean change = true;
		LinkedList<Klauzula> toAdd = new LinkedList<Klauzula>();
		while (change) {
			/*
			 * System.out.println("------------BASE-----------");
			 * 
			 * for(Klauzula b:this.base){ System.out.println(b); }
			 * System.out.println("---------------------------");
			 */
			toAdd = new LinkedList<Klauzula>();
			change = false;
			for (Klauzula klauz : this.base) {
				for (Klauzula m : this.base) {
					if (!klauz.same(m)) {
						if (klauz.containsNeg(m)) {
							k = klauz.resolve(m);
							/*
							 * System.out.println("klauzul :"+klauz);
							 * System.out.println("second:" +m);
							 * System.out.println("Resolved :"+k);
							 */
							if (k.isEmpty()) {
								return true;
							}
							toAdd.add(k);
							change = true;
						}
					}
				}
			}
			this.base.addAll(toAdd);
			this.simplify();
		}

		return false;
	}

	/**
	 * @return the base
	 */
	public LinkedList<Klauzula> getBase() {
		return base;
	}

	/**
	 * @param base
	 *            the base to set
	 */
	public void setBase(LinkedList<Klauzula> base) {
		this.base = base;
	}

	private void simplify() {
		boolean change = true;
		LinkedList<Klauzula> simpler = new LinkedList<Klauzula>();
		while (change) {
			change = false;
			simpler = new LinkedList<Klauzula>();
			simpler.addAll(this.base);
			for (Klauzula k : this.base) {
				for (Klauzula test : this.base) {
					if (k.simplerThen(test)) {
						change = true;
						simpler.remove(test);
					}

				}
			}
			if (change) {
				this.base = new LinkedList<Klauzula>();
				base.addAll(simpler);
			}

		}
	}
}
