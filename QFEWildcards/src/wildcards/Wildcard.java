package wildcards;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
	
	/**
	 * Return whether the wildcard is already used or ready for use.
	 * @param id ID to check, if it's used or not
	 * @return
	 */
	public boolean isUsed(int id) {
		try {
			PreparedStatement ps = connection.prepareStatement("select * from Wildcards where WildcardID = "  + id + ";");
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				if(rs.getInt("Used") == 0) {
					return false;
				} else {
					return true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return true;
	}

}
