package movie.db.server;

import java.sql.*;

public class App {

	public static void main(String [] args) {
		
		Query query = new Query();
		try {
			query.getAllData().get(0);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
		
}
