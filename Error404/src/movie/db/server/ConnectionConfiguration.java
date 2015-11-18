package movie.db.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * This class offers a database connection through a static method.
 * @Author Lukas Enggist
 */
public class ConnectionConfiguration {
	/**
	 * Establishes and returns a database connection
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * 
	 * @Author Lukas Enggist
	 * @pre none
	 * @post connection was established
	 */
	public static Connection getConnection() throws ClassNotFoundException,
			SQLException {
		Connection connection = null;		
		Class.forName("com.mysql.jdbc.Driver");
		connection = DriverManager
				.getConnection(
						"jdbc:mysql://login-83.hoststar.ch:3306/data?autoReconnect=true",
						"project", "SOE2015chdalupa!");
		return connection;
	}

}

