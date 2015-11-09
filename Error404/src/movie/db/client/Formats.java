package movie.db.client;


/**
 * Enum to declare if the data selected by the user should be 
 * presented as Table, Piechart or Barchart.
 */
public enum Formats {
	Table,
	Piechart,
	Barchart;
	
	/**
	 * Gets the name of the enum as a string.
	 * Replaces all "_" in the name.
	 * 
	 * @return String enum
	 */
	public String getName() {
		return this.toString().replace("_", " ");
	}
}
