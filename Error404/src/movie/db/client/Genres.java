package movie.db.client;

public enum Genres {
	Romance,
	Comedy,
	Action,
	Horror,
	Thriller,
	Science_Fiction,
	Adventure,
	Supernatural;

	public String getName() {
		return this.toString().replace("_", " ");
	}
}
