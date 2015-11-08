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
			statement.setQueryTimeout(30);
			statement = connection.createStatement();
	
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
					+ "ON movies_genres.genre_id=genres.id "
					+ sqlClause + "ORDER BY movies.name ";

			ResultSet queryResult = statement.executeQuery(sqlQuery);

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

			DataResultShared errorRes =  new DataResultShared();
			errorRes.setMovieName(e1.getMessage());
			dataResultMap.put(-1,errorRes );
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
		
		String sqlQuery = "SELECT countries.name AS \"country\", count(countries.id) AS \"amount\" FROM movies JOIN movies_countries ON movies.id = movies_countries.movie_id "
				+ "JOIN countries ON movies_countries.country_id = countries.id "
				+ "WHERE movies.year = " + selectedYear + " group by countries.id";

		Connection connection = ConnectionConfiguration.getConnection();
		Statement statement = null;
		try {
			statement = connection.createStatement();
			ResultSet queryResult = statement.executeQuery(sqlQuery);

			while (queryResult.next()) {
				DataResultAggregated dataresult = new DataResultAggregated();
				dataresult.setCountryName(queryResult
						.getString("country"));
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
	public ArrayList<String> getColumnEntries(String column, String columnId) {
		ArrayList<String> resultArray = new ArrayList<String>();

		String sqlQuery = "SELECT " + column + ".name AS \"" + columnId +"\" FROM movies "
				+ "JOIN movies_" + column + " ON movies.id = movies_" + column + ".movie_id "
				+ "JOIN " + column + " ON movies_" + column + "."+ columnId + "_id = " + column + ".id group by " + column + ".id";

		Connection connection = ConnectionConfiguration.getConnection();
		Statement statement = null;
		try {
			statement = connection.createStatement();
			ResultSet queryResult = statement.executeQuery(sqlQuery);

			while (queryResult.next()) {
				resultArray.add(queryResult.getString(columnId));
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
	
	private String selectionToSQLWhereClause(Selection selection) {
		String selectionSQLWhereClause = "WHERE 1 = 1 ";
		/*if (selection.getSelectedMovieName() != null) {
			selectionSQLWhereClause = selectionSQLWhereClause + "AND name = '"
					+ selection.getSelectedMovieName() + "' ";
		}
		if (selection.getSelectedYear() != null) {
			selectionSQLWhereClause = selectionSQLWhereClause + "AND year = "
					+ Integer.toString(selection.getSelectedYear()) + " ";
		}
		*/		

		if (!selection.getSelectedCountries().isEmpty()) {
			selectionSQLWhereClause = selectionSQLWhereClause
					+ "AND (SELECT count(*) from movies_countries "  
					+ "JOIN countries ON movies_countries.country_id = countries.id "
					+ "WHERE countries.name IN ('"
					+ String.join("','", selection.getSelectedCountries()) + "') AND movie_id = movies.id) > 0 ";
		}

		if (!selection.getSelectedLanguages().isEmpty()) {
			selectionSQLWhereClause = selectionSQLWhereClause
					+ "AND (SELECT count(*) from movies_languages "  
					+ "JOIN languages ON movies_languages.language_id = languages.id "
					+ "WHERE languages.name IN ('"
					+ String.join("','", selection.getSelectedLanguages()) + "') AND movie_id = movies.id) > 0 ";
		}
				
		if (!selection.getSelectedGenres().isEmpty()) {
			selectionSQLWhereClause = selectionSQLWhereClause
					+ "AND (SELECT count(*) from movies_genres "  
					+ "JOIN genres ON movies_genres.genre_id = genres.id "
					+ "WHERE genres.name IN ('"
					+ String.join("','", selection.getSelectedGenres()) + "') AND movie_id = movies.id) > 0 ";
		}
		return selectionSQLWhereClause;
	}
}
