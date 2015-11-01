package movie.db.client;

public enum Countries {
	United_States_of_America,
	Norway,
	United_Kingdom,
	Germany,
	South_Africa,
	Argentina,
	Japan,
	Turkey;

	public String getName() {
		return this.toString().replace("_", " ");
	}
}
