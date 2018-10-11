package wildcards;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import wildcards.connector.MySQL;

public class Wildcard {

	private Connection connection;
	
	public Wildcard(String host, int port, String database, String username, String password, boolean autoReconnect) {
		MySQL mysql = new MySQL(host, port, database, username, password, autoReconnect);
		mysql.connectToDB();
		
		connection = mysql.getConnection();
	}
	
	public boolean createWildcard() {
		boolean successful = false;
		
		String token = "ichmagkekse";
		
		try {
			PreparedStatement ps = connection.prepareStatement("insert into Wildcards (Token, Used) values ('" + token + "',0)");
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			successful = false;
		}
		
		return successful;
	}

}
