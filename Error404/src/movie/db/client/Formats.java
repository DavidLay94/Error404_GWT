package movie.db.client;

public enum Formats {
	Table;
	//Piechart,
	//Barchart;

	public String getName() {
		return this.toString().replace("_", " ");
	}
}
