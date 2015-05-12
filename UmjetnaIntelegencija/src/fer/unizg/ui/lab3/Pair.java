package fer.unizg.ui.lab3;

public class Pair {
	private String argument;
	private String value;
	
	public Pair (String argument, String value){
		this.argument=argument;
		this.value=value;
	}
	public Pair (String line){
		this.argument=line.split("=")[0].trim();
		this.value=line.split("=")[1].trim();
	}
	/**
	 * @return the argument
	 */
	public String getArgument() {
		return argument;
	}

	/**
	 * @param argument the argument to set
	 */
	public void setArgument(String argument) {
		this.argument = argument;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return argument + " = " + value;
	}
	
	

}
