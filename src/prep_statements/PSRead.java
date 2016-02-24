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
	 * Constructor for the prepared statement for a reading operation
	 * 
	 * @param con
	 *            the DB connection needed for the prepared statement
	 */
	public PSRead(DBConnector con) {
		read = con.prepareStatement("SELECT * FROM person WHERE nummer > ?");
	}

	/**
	 * Executes the prepared Statement with given int number for SELECT
	 * statement
	 * 
	 * @param border
	 *            defines at which person the select starts
	 * 
	 * @return the ResultSet containing all selected persons
	 * @throws SQLException
	 *             if the execution of the query failed SQLException is thrown
	 */
	public ResultSet readResult(int border) throws SQLException {
		read.setInt(1, border);
		return read.executeQuery();
	}

	/**
	 * prints the Values of the ResultSet given in the Parameter to Sysout
	 * @param rs the ResultSet
	 */
	public void printResult(ResultSet rs) {
		try {
			while (rs.next()) {
				int number = rs.getInt(1);
				String name = rs.getString(2);
				String surname = rs.getString(3);
				System.out.println(number + " " + name + " " + surname);
			}
		} catch (SQLException e) {
			System.err.println("printing the ResultSet Failed");
		}
	}
}