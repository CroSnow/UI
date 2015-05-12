package fer.unizg.ui.lab3;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Rules implements Comparable<Rules> {
	private LinkedList<Pair> LHS;
	private LinkedList<Pair> RHS;
	private int number;
	
	public Rules(){
		this.LHS=new LinkedList<Pair>();
		this.RHS=new LinkedList<Pair>();
	}
	public Rules(String line){
		this.LHS=new LinkedList<Pair>();
		this.RHS=new LinkedList<Pair>();
		this.setRule(line);
		
	}
	
	public void setRule(String line){
		System.out.println(line);
		String number=line.substring(0, line.indexOf(' ')).trim();
		line=line.substring(line.indexOf(' ')).trim();
		this.number=Integer.parseInt(number);
		String LeftSide=line.split("THEN")[0].trim();
		LeftSide=LeftSide.substring(3).trim();
		String RightSide=line.split("THEN")[1].trim();
		String[]leftArray=LeftSide.split("&");
		String[] rightArray=RightSide.split("&");
		for(String pair:leftArray){
			LHS.add(new Pair(pair));
		}
		for(String pair:rightArray){
			RHS.add(new Pair(pair));
		}
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder=new StringBuilder();
		builder.append(Integer.toString(number)+" ");
		builder.append("IF ");
		for (Pair p:LHS){
			builder.append(p);
			builder.append(" & ");
		}
		builder.deleteCharAt(builder.length()-2);
		builder.append("\nTHEN ");
		for (Pair p:RHS){
			builder.append(p);
			builder.append(" & ");
		}
		builder.deleteCharAt(builder.length()-2);
		
		return builder.toString();
	}
	public boolean rightSideContains(String argument){
		for(Pair p:RHS){
			if(argument.equals(p.getArgument())){
				return true;
			}
		}
		return false;
	}
	

	
	public boolean isResolvable(HashMap<String,String> base){
		Set<String> keySet=base.keySet();
		boolean match;
		for(Pair p:LHS){
			if(keySet.contains(p.getArgument())){
				match=false;
				for(String v:p.getValue().split("\\|")){
					if(v.trim().equals(base.get(p.getArgument().trim()).trim())){
						match=true;
					}
				}
				if (!match){
					return false;
				}
			}
		}
		return true;
	}
	
	public List<String> missingArguments (HashMap<String, String> knowledge){
		Set<String> keySet=knowledge.keySet();
		boolean match;
		List<String> missing=new LinkedList<String>();
		for(Pair p:LHS){
			if(!keySet.contains(p.getArgument())){
				missing.add(p.getArgument());
			}
		}
		return missing;

	}
	public void addKnowledgeIntoMap(HashMap<String, String> knowledge) {
		for(Pair p:RHS){
			knowledge.put(p.getArgument(), p.getValue());
		}		
	}
	/**
	 * @return the number
	 */
	public int getNumber() {
		return number;
	}
	/**
	 * @param number the number to set
	 */
	public void setNumber(int number) {
		this.number = number;
	}
	@Override
	public int compareTo(Rules o) {
		
		return this.number- o.getNumber();
	}
	
	
	
	
}
