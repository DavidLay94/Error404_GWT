package movie.db.server;

import java.sql.*;
import java.util.*;

import movie.db.client.MyService;
import movie.db.shared.DataResultShared;

<<<<<<< HEAD
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class Query extends RemoteServiceServlet implements MyService {

=======
>>>>>>> refs/remotes/origin/master
	@SuppressWarnings("finally")
	public Map<Integer, DataResultShared> getFilteredData() {
		Map<Integer, DataResultShared> dataResultMap = new HashMap<Integer, DataResultShared>();

		Connection connection = ConnectionConfiguration.getConnection();
		Statement statement = null;
		try {
			statement = connection.createStatement();
			// for creating
			// statements out of
			// the established
			// connection

			// try {

			String sqlQuery = "SELECT movies.id, countries.id, languages.id, genres.id, movies.name, movies.year, countries.name, languages.name, genres.name "
					+ "FROM movies "
					+ "JOIN movies_countries "
					+ "ON movies.id=movies_countries.movie_id "
					+ "JOIN countries "
					+ "ON movies_countries.country_id=countries.id "
					+ "JOIN movies_languages "
					+ "ON movies.id=movies_languages.movie_id "
					+ "JOIN languages "
					+ "ON movies_languages.language_id=languages.id "
					+ "JOIN movies_genres "
					+ "ON movies.id=movies_genres.movie_id "
					+ "JOIN genres "
					+ "ON movies_genres.genre_id=genres.id " + "WHERE 1=1 "
					// +"WHERE genres.name = 'Horror' "
					// +"AND languages.name = 'Deutsch'"
					+ "ORDER BY movies.name ";

			ResultSet queryResult = statement.executeQuery(sqlQuery);
			// int movieIndex = 0;

			int movieId;
			String movieName;
			int year;
			String countryName;
			String languageName;
			String genreName;

			while (queryResult.next()) {

				// Retrieve data by column name
				movieId = queryResult.getInt("movies.id");
				movieName = queryResult.getString("name");
				year = queryResult.getInt("year");
				countryName = queryResult.getString("countries.name");
				languageName = queryResult.getString("languages.name");
				genreName = queryResult.getString("genres.name");

				if (dataResultMap.containsKey(movieId)) {
					dataResultMap.get(movieId).addCountry(countryName);
					dataResultMap.get(movieId).addLanguage(languageName);
					dataResultMap.get(movieId).addGenre(genreName);
				} else {
					DataResultShared movie = new DataResultShared();
					movie.setMovieName(movieName);
					movie.setYear(year);
					movie.addCountry(countryName);
					movie.addLanguage(languageName);
					movie.addGenre(genreName);
					dataResultMap.put(movieId, movie);
				}
			}

		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			// }
			// } catch (Exception exc) {
			// System.out.println(exc);
		} finally {
			try {
				statement.close();
			} catch (Exception e) { /* ignored */
			}
			try {
				connection.close();
			} catch (Exception e) { /* ignored */
			}

			return dataResultMap;
		}
	}
}
