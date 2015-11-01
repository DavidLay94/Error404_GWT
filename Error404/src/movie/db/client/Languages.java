package movie.db.client;

public enum Languages {
	English,
	German,
	French,
	Turkish,
	Russian;

	public String getName() {
		return this.toString().replace("_", " ");
	}
}
