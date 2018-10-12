package wildcards.connector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseHelper {
	
	private Connection connection;
	/**
	 * This method creates the table for the Wildcards itself.
	 * @param con Connection from the MySQL Connector
	 */
	public DatabaseHelper(Connection con) {
		connection = con;
		
		try {
			PreparedStatement ps = connection.prepareStatement("create table if not exists Wildcards(WildcardID int not null auto_increment primary key, Token varchar(256), Used tinyint);");
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * In case someone would like to drop the table automatically, this method should be called. Otherwise this method is very dangerous!
	 */
	public void dropTable() {
		try {
			PreparedStatement ps = connection.prepareStatement("drop table Wildcards;");
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
