package prep_statements;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Simple class for testing Preparing Statements with Read Operations.
 * 
 * @author Martin Weber
 * @version 20160224.1
 *
 */
public class PSRead {

	private PreparedStatement read;

	/**
	 * Constructor for the Prepared Statement
	 * 
	 * @param con
	 *            the DB connection needed for the prepared Statement
	 */
	public PSRead(DBConnector con) {
		read = con.prepareStatement("SELECT * FROM person WHERE nummer > ?");
	}

	public ResultSet readResult(int border) throws SQLException {
		try {
			read.setInt(1, border);
			return read.executeQuery();
		} catch (SQLException e) {
			System.err.println("Execution of Query failed" + e.getMessage());
			throw new SQLException();
		}
	}
}