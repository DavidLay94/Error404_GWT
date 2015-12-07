package movie.db.shared;
/**
 * Enum to sort duration in 4 classes.
 * @Author Christoph Weber
 */
public enum Duration {
	Short,
	Medium,
	Long,
	ExtraLong,
	NA;
	
	/**
	 * Gets the name of the enum as a string.
	 * Adds some explanation in brackets
	 * 
	 * @return String enum
	 */
	public String getName() {
		String name;
		switch(this.toString()){
		case "Short":
			name = "Short (0-30min)";
			break;
		case "Medium":
			name = "Medium (30-60min)";
			break;
		case "Long":
			name = "Long (60-120min)";
			break;
		case "ExtraLong":
			name = "Extra Long (>120min)";
			break;
		default:
			name = "NA";
		}
		return name;
	}
}
