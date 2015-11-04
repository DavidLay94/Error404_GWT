package movie.db.client;

public enum Formats {
	Worlmap,
	Piechart,
	Barchart;

	public String getName() {
		return this.toString().replace("_", " ");
	}
}
