package movie.db.server;

import java.sql.*;
import java.util.*;

import movie.db.client.MyService;
import movie.db.shared.DataResultAggregated;
import movie.db.shared.DataResultShared;
import movie.db.shared.Selection;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class Query extends RemoteServiceServlet implements MyService {

	@SuppressWarnings("finally")
	public Map<Integer, DataResultShared> getFilteredData(Selection selection) {
		Map<Integer, DataResultShared> dataResultMap = new HashMap<Integer, DataResultShared>();
		String sqlClause = selectionToSQLWhereClause(selection);

		Connection connection = ConnectionConfiguration.getConnection();
		Statement statement = null;

		try {
			statement = connection.createStatement();

			String sqlQuery = "SELECT id, name, year, country, language, genre FROM moviesAllInOne "
					+ sqlClause + " ORDER BY name LIMIT 0,500";
			/*
			 * "SELECT movies.id, countries.id, languages.id, genres.id, movies.name, movies.year, countries.name, languages.name, genres.name "
			 * + "FROM movies " + "JOIN movies_countries " +
			 * "ON movies.id=movies_countries.movie_id " + "JOIN countries " +
			 * "ON movies_countries.country_id=countries.id " +
			 * "JOIN movies_languages " +
			 * "ON movies.id=movies_languages.movie_id " + "JOIN languages " +
			 * "ON movies_languages.language_id=languages.id " +
			 * "JOIN movies_genres " + "ON movies.id=movies_genres.movie_id " +
			 * "JOIN genres " + "ON movies_genres.genre_id=genres.id " +
			 * sqlClause + "ORDER BY movies.name ";
			 */

			ResultSet queryResult = statement.executeQuery(sqlQuery);

			int movieId;
			String movieName;
			int year;
			String countriesSeparated;
			String languagesSeparated;
			String genresSeparated;

			while (queryResult.next()) {

				// Retrieve data by column name
				movieId = queryResult.getInt("id");
				movieName = queryResult.getString("name");
				year = queryResult.getInt("year");
				countriesSeparated = queryResult.getString("country");
				languagesSeparated = queryResult.getString("language");
				genresSeparated = queryResult.getString("genre");

				DataResultShared movie = new DataResultShared();
				movie.setMovieName(movieName);
				movie.setYear(year);

				if (countriesSeparated != null) {
					ArrayList<String> countries = new ArrayList<String>();
					countries.addAll(Arrays.asList(countriesSeparated
							.split(",")));
					movie.setCountries(countries);
				}
				if (languagesSeparated != null) {
					ArrayList<String> languages = new ArrayList<String>();
					languages.addAll(Arrays.asList(languagesSeparated
							.split(",")));
					movie.setLanguages(languages);
				}
				if (genresSeparated != null) {
					ArrayList<String> genres = new ArrayList<String>();
					genres.addAll(Arrays.asList(genresSeparated.split(",")));
					movie.setGenres(genres);
				}

				dataResultMap.put(movieId, movie);
				/*
				 * if (dataResultMap.containsKey(movieId)) {
				 * dataResultMap.get(movieId).addCountry(countryName);
				 * dataResultMap.get(movieId).addLanguage(languageName);
				 * dataResultMap.get(movieId).addGenre(genreName); } else {
				 * DataResultShared movie = new DataResultShared();
				 * movie.setMovieName(movieName); movie.setYear(year);
				 * movie.addCountry(countryName);
				 * movie.addLanguage(languageName); movie.addGenre(genreName);
				 * dataResultMap.put(movieId, movie); }
				 */
			}

		} catch (SQLException e1) {

			DataResultShared errorRes = new DataResultShared();
			errorRes.setMovieName(e1.getMessage());
			dataResultMap.put(-1, errorRes);
		} catch (IndexOutOfBoundsException e2) {
			e2.printStackTrace();
		} finally {
			try {
				statement.close();
			} catch (Exception e) {
			}
			try {
				connection.close();
			} catch (Exception e) {
			}

			return dataResultMap;
		}
	}

	@SuppressWarnings("finally")
	public ArrayList<DataResultAggregated> getWorldMapData(int selectedYear) {
		ArrayList<DataResultAggregated> resultArray = new ArrayList<DataResultAggregated>();

		String sqlQuery = "SELECT country, count(country) AS \"amount\" FROM moviesAllInOne WHERE year = " + selectedYear + " group by country";

		Connection connection = ConnectionConfiguration.getConnection();
		Statement statement = null;
		try {
			statement = connection.createStatement();
			ResultSet queryResult = statement.executeQuery(sqlQuery);

			while (queryResult.next()) {
				DataResultAggregated dataresult = new DataResultAggregated();
				dataresult.setCountryName(queryResult.getString("country"));
				dataresult.setAggregatedNumberOfMovies(queryResult
						.getInt("amount"));
				resultArray.add(dataresult);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				statement.close();
			} catch (Exception e) {
			}
			try {
				connection.close();
			} catch (Exception e) {
			}

			return resultArray;
		}
	}

	@SuppressWarnings("finally")
	public ArrayList<String> getColumnEntries(String column) {
		ArrayList<String> resultArrayList = new ArrayList<String>();

		String sqlQuery = "SELECT " + column + " FROM moviesAllInOne group by "
				+ column;

		Connection connection = ConnectionConfiguration.getConnection();
		Statement statement = null;
		try {
			statement = connection.createStatement();
			ResultSet queryResult = statement.executeQuery(sqlQuery);

			while (queryResult.next()) {
				ArrayList<String> aList = new ArrayList<String>();
				aList.addAll(Arrays.asList(queryResult.getString(column).split(
						",")));

				for (String s : aList) {
					if (!resultArrayList.contains(s))
						resultArrayList.add(s);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				statement.close();
			} catch (Exception e) {
			}
			try {
				connection.close();
			} catch (Exception e) {
			}
			Collections.sort(resultArrayList);
			resultArrayList.remove(" ");
			return resultArrayList;
		}
	}

	public String selectionToSQLWhereClause(Selection selection) {
		String selectionSQLWhereClause;
		if (selection == null) {
			selectionSQLWhereClause = " ";
		} else {
			selectionSQLWhereClause = "WHERE 1 = 1 ";

			if (!selection.getSelectedCountries().isEmpty()) {
				selectionSQLWhereClause = selectionSQLWhereClause
						+ "AND (country LIKE '%"
						+ String.join("%' OR country LIKE '%",
								selection.getSelectedCountries()) + "%')";
			}

			if (!selection.getSelectedLanguages().isEmpty()) {
				selectionSQLWhereClause = selectionSQLWhereClause
						+ "AND (language LIKE '%"
						+ String.join("%' OR language LIKE '%",
								selection.getSelectedLanguages()) + "%')";
			}

			if (!selection.getSelectedGenres().isEmpty()) {
				selectionSQLWhereClause = selectionSQLWhereClause
						+ "AND (genre LIKE '%"
						+ String.join("%' OR genre LIKE '%",
								selection.getSelectedGenres()) + "%')";
			}
		}
		return selectionSQLWhereClause;
	}
}
