package wildcards.connector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import wildcards.connector.utils.Wildcard;

public class MySQL {

	private static String host;
	private static int port;
	private static String database;
	private static String username;
	private static String password;
	private static boolean autoReconnect;
	private static Connection con;

	public MySQL(String host, int port, String database, String username, String password, boolean autoReconnect) {
		MySQL.host = host;
		MySQL.port = port;
		MySQL.database = database;
		MySQL.username = username;
		MySQL.password = password;
		MySQL.autoReconnect = autoReconnect;
	}

	public Wildcard connectToDB() {
		Wildcard wc = null;
		try {
			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			con = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database + "?user=" + username
					+ "&password=" + password + "&autoReconnect=" + String.valueOf(autoReconnect));
			wc = new Wildcard(con);
			if(isConnected()) {
				DatabaseHelper dbh = new DatabaseHelper(con);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return wc;
	}

	public void disconnect() {
		if (isConnected()) {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public boolean isConnected() {
		return (con == null ? false : true);
	}

	public Connection getConnection() {
		return con;
	}
}
