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

	/**
	 * 
	 * @return Whether the creating of a new key was successful or not
	 */
	public boolean createWildcard() {
		boolean successful = false;

		String token = "ichmagkekse";

		if (!wildcardExists(token)) {
			try {
				PreparedStatement ps = connection
						.prepareStatement("insert into Wildcards (Token, Used) values ('" + token + "',0)");
				ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
				successful = false;
			}
		} else {
			return false;
		}

		return successful;
	}

	/**
	 * 
	 * @param id
	 *            ID to check, if it's used or not
	 * @return Whether the wildcard is used or not
	 */
	public boolean isUsed(int id) {
		try {
			PreparedStatement ps = connection
					.prepareStatement("select * from Wildcards where WildcardID = " + id + ";");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				if (rs.getInt("Used") == 0) {
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

	/**
	 * 
	 * @param wildcard
	 *            Wildcard-Token to get the ID from
	 * @return ID from the wildcard given
	 */
	public int getIDByWildcard(String wildcard) {
		if (wildcardExists(wildcard)) {
			try {
				PreparedStatement ps = connection
						.prepareStatement("select * from Wildcards where Token = '" + wildcard + "';");
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {
					return rs.getInt("WildcardID");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return 0;
	}

	/**
	 * 
	 * @param wildcard
	 *            The Wildcard to use
	 * @return Whether the using of the wildcard was successful or not
	 */
	public boolean useWildcard(String wildcard) {
		if (!isUsed(getIDByWildcard(wildcard))) {
			return toggleUsedStatus(getIDByWildcard(wildcard));
		} else {
			return false;
		}
	}

	/**
	 * 
	 * @param id
	 *            ID, to change the used status from
	 * @return Whether the changing of the used status was successful or not
	 */
	public boolean toggleUsedStatus(int id) {
		boolean used = isUsed(id);

		try {
			PreparedStatement ps;
			if (used == false) {
				ps = connection.prepareStatement("update Wildcards set Used = 1 where WildcardID = " + id + ";");
			} else {
				ps = connection.prepareStatement("update Wildcards set Used = 0 where WildcardID = " + id + ";");
			}
			ps.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 
	 * @param wildcard
	 *            The token to check if it exists or not
	 * @return Whether a wildcard with the given token exists or not
	 */
	public boolean wildcardExists(String wildcard) {
		try {
			PreparedStatement ps = connection
					.prepareStatement("select * from Wildcards where Token='" + wildcard + "';");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				return true;
			}
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

}
