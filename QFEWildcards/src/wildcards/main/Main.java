package wildcards.main;

import wildcards.connector.MySQL;

public class Main {
	
	public void establishConnection(String host, int port, String database, String username, String password, boolean autoReconnect) {
		MySQL mysql = new MySQL(host, port, database, username, password, autoReconnect);
		mysql.connectToDB();
	}
}
