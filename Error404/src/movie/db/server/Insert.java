package movie.db.server;

import java.sql.*;
import movie.db.shared.*;

public class Insert {
	
	public void Import(DataResultShared dataResult) throws SQLException{
		
		Connection connection = ConnectionConfiguration.getConnection();
		Statement statement = connection.createStatement();
		
		String allreadyListedMovies = "SELECT movies.name, movies.year FROM movies";
		String update = "INSERT INTO movies (movies.name, movies.year) "
						+"VALUES ('Test', 2000) ";
						//+"WHERE some_column=some_value;";
		ResultSet getMovies = statement.executeQuery(allreadyListedMovies);
		boolean addMovie = true;
		String movieName;
		int year;
		
		outerloop:
		while(getMovies.next()){
			
			movieName = getMovies.getString("name");
			year = getMovies.getInt("year");
			
			if(year == dataResult.getYear() && movieName.equals(dataResult.getMovieName())){
				
				addMovie = false;
				break outerloop;
			}
		}
		
		if(addMovie == true){
			statement.executeUpdate(update);
			System.out.println("Added");
		}else{
			System.out.println("allready taken");
		}
		
	}

}
