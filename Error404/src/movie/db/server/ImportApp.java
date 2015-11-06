package movie.db.server;

import java.sql.SQLException;

import movie.db.shared.*;

public class ImportApp {
	
	public static void main(String[] args) throws SQLException{
		
		Insert insert = new Insert();
		DataResultShared dataResult = new DataResultShared();
		
		dataResult.setMovieName("Test");
		
		insert.Import(dataResult);
		
	}

}
