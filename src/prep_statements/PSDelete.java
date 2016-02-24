package prep_statements;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Simple class for testing Preparing Statements with Delete Operations.
 * 
 * @author Daniel May
 * @version 20160224.1
 *
 */
public class PSDelete {
	private PreparedStatement delete;

	/**
	 * constructor
	 * 
	 * @param con
	 *            the database connector
	 */
	public PSDelete(DBConnector con) {
		delete = con.prepareStatement("DELETE FROM person WHERE nummer = ?;");
	}

	/**
	 * Uses the deletePerson method to delete all Persons that were inserted
	 * previously.
	 * 
	 * @param count
	 *            how much Persons
	 * @param start
	 *            where does the the ID range start?
	 */
	public void deleteAll(int count, int start) {
		for (int i = start; i < count + start; i++) {
			deletePerson(i);
		}
	}

	/**
	 * Replaces the placeholder with the specified parameter and executes the
	 * statement.
	 *
	 * @param number
	 *            ID to identify the person
	 */
	private void deletePerson(int number) {
		try {
			delete.setInt(1, number);
			delete.execute();
		} catch (SQLException e) {
			System.err.println("Deleting a Person failed.");
			System.err.println(e.getMessage());
		}
	}
}