package fer.unizg.ui.lab3;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.Stack;

public class ExpertKnowledgeBase {
	private HashMap<String, List<String>> posibleValues;
	private HashMap<String, String> baseFields;
	private LinkedList<Rules> rules;
	private String pali;
	private boolean doesP;

	public ExpertKnowledgeBase() {
		rules = new LinkedList<Rules>();
		posibleValues = new HashMap<String, List<String>>();
		baseFields = new HashMap<String, String>();
	}

	public void addRule(Rules r) {
		this.rules.add(r);
		Collections.sort(rules);
	}

	public void resolve(BufferedReader reader) {
		Stack<String> stog = new Stack<String>();
		String line = "";
		HashMap<String, String> knowledge = new HashMap<String, String>();
		Set<String> posibleKeys = posibleValues.keySet();
		while (!posibleKeys.contains(line)) {
			System.out.println("Upišite traženu vrijednost");
			try {
				line = reader.readLine().trim();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		stog.add(line);
		boolean found = true;
		boolean ask = true;
		while (!stog.isEmpty()) {
			if (found) {
				this.ispisiRadnuMemoriju(knowledge);
			}
			ask=true;
			found = false;
			String toResolve = stog.pop();
			Set<String> allKeys = knowledge.keySet();
			if (allKeys.contains(toResolve)) {
				continue;
			}
			List<Integer> konflikt = new LinkedList<Integer>();
			doesP = false;
			System.out.println(stog);
			for (Rules r : rules) {
				if (r.rightSideContains(toResolve)) {
					ask = false;

					if (r.isResolvable(knowledge)) {

						konflikt.add(r.getNumber());
						if (!found) {
							List<String> mising = r.missingArguments(knowledge);
							found = true;
							/*
							 * for (String m : mising) { System.out.println(m);
							 * }
							 */
							if (mising.isEmpty()) {
								pali = Integer.toString(r.getNumber());
								doesP = true;
								r.addKnowledgeIntoMap(knowledge);
							} else {
								stog.add(toResolve);
								stog.add(mising.get(0));
								/*
								 * Collections.reverse(mising); for (String tR :
								 * mising) { if (!stog.contains(tR)) {
								 * stog.add(tR); } }
								 */
							}
						}
					}
				}
			}
			if (!konflikt.isEmpty()) {
				String ispis = "Konliktni skup:";
				for (int i : konflikt) {
					ispis += " " + Integer.toString(i);
				}
				System.out.println(ispis);
			}
			if (doesP) {
				System.out.println("PALI:" + pali);
			}
			if (ask && !stog.isEmpty()) {
				System.out.println("Upisite vrijednost za " + toResolve + ":");
				try {
					String value = reader.readLine().trim();
					knowledge.put(toResolve.trim(), value);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			if (stog.isEmpty()) {
				System.out.println("Rezultat upita za " + toResolve + ":"
						+ knowledge.get(toResolve));
			}

		}

	}

	private static void ispisiRadnuMemoriju(HashMap<String, String> base) {
		System.out.println("--------RADNA MEMORIJA--------");
		Set<String> keys = base.keySet();
		for (String k : keys) {
			System.out.println(k + ":" + base.get(k));
		}
		System.out.println("------------------------------");
	}

	public static ExpertKnowledgeBase parseInputFromReader(BufferedReader reader) {
		ExpertKnowledgeBase base = new ExpertKnowledgeBase();
		HashMap<String, List<String>> posibleValuesForParse = new HashMap<String, List<String>>();
		HashMap<String, String> emptyKnowledge = new HashMap<String, String>();
		String line = "";
		String[] arguments;
		String[] values;
		try {
			line = reader.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while (line != null) {
			line = line.trim();
			if (Character.isDigit(line.charAt(0))) {
				try {
					reader.mark(1000);
					String secondLine = reader.readLine().trim();
					while ((secondLine != null)
							&& (!Character.isDigit(secondLine.charAt(0)))) {

						line = line + " " + secondLine;
						reader.mark(100000000);
						secondLine = reader.readLine();
					}
					reader.reset();
					Rules rule = new Rules(line);
					base.addRule(rule);

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				if (line.length() > 0) {
					arguments = line.split("=");
					String key = arguments[0].trim();
					values = arguments[1].split(",");
					List<String> valuesList = new LinkedList<String>();
					for (String v : values) {
						valuesList.add(v.trim());
					}
					posibleValuesForParse.put(key, valuesList);
					// emptyKnowledge.put(key, "");//?

				}
			}
			try {
				line = reader.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		base.setPosibleValues(posibleValuesForParse);
		return base;
	}

	/**
	 * @return the posibleValues
	 */
	public HashMap<String, List<String>> getPosibleValues() {
		return posibleValues;
	}

	/**
	 * @param posibleValues
	 *            the posibleValues to set
	 */
	public void setPosibleValues(HashMap<String, List<String>> posibleValues) {
		this.posibleValues = posibleValues;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (String key : posibleValues.keySet()) {
			builder.append(key + " = ");
			List<String> values = posibleValues.get(key);
			for (String v : values) {
				builder.append(v);
				builder.append(",");
			}
			builder.deleteCharAt(builder.length() - 1);
			builder.append("\n");
		}
		builder.append("----------RULES--------\n");
		for (Rules r : rules) {
			builder.append(r + "\n");
		}
		return builder.toString();
	}

}
