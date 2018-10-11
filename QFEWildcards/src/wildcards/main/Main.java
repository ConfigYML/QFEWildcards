package wildcards.main;

import wildcards.connector.MySQL;
import wildcards.connector.utils.Wildcard;

public class Main {
	/**
	 * This method establishes a connection to the MySQL database and enables all other Methods to be executed correct.
	 *
	 * @param host	 Sets the host for the MySQL connection
	 * @param port	 Sets the port for the MySQL connection
	 * @param database	 Sets the database for the MySQL connection
	 * @param username	 Sets the username for the MySQL connection
	 * @param password	 Sets the password for the MySQL connection
	 * @param autoReconnect	 Sets whether to automatically reconnect to the database
	 */
	public Wildcard establishConnection(String host, int port, String database, String username, String password, boolean autoReconnect) {
		MySQL mysql = new MySQL(host, port, database, username, password, autoReconnect);
		Wildcard wc = mysql.connectToDB();
		return wc;
	}
}
