package movie.db.server;

import java.sql.Connection;
import java.sql.DriverManager;

/*
 * This class offers a database connection through a static method.
 */
public class ConnectionConfiguration {	
	/**
	 * Establishes and returns a database connection 
	 * 
	 * @pre none
	 * @post connection was established
	 */
	public static Connection getConnection() {
		Connection connection = null;

		try {
			connection = DriverManager.getConnection("jdbc:mysql://login-83.hoststar.ch:3306/data", "project", "SOE2015chdalupa!");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return connection;
	}

}
