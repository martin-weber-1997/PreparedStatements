package prep_statements;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Simple class for testing Preparing Statements with Update Operations.
 * 
 * @author Daniel May
 * @version 20160224.1
 *
 */
public class PSUpdate {
	private PreparedStatement update;

	/**
	 * constructor
	 * 
	 * @param con
	 *            the database connector
	 */
	public PSUpdate(DBConnector con) {
		update = con.prepareStatement("UPDATE person SET vorname = ? WHERE nummer = ?;");
	}

	/**
	 * Uses the updatePerson method to update all Persons that were inserted
	 * previously.
	 * 
	 * @param count
	 *            how much Persons
	 * @param start
	 *            where does the the ID range start?
	 */
	public void updateAll(int count, int start) {
		for (int i = start; i < count + start; i++) {
			updatePerson("Vorname" + i, i);
		}
	}

	/**
	 * Replaces the placeholders with the specified parameters and executes the
	 * statement.
	 * 
	 * @param name
	 *            new name to set
	 * @param number
	 *            ID to identify the person
	 */
	public void updatePerson(String name, int number) {
		try {
			update.setString(1, name);
			update.setInt(2, number);
			update.execute();
		} catch (SQLException e) {
			System.err.println("Updating a Person failed.");
			System.err.println(e.getMessage());
		}
	}
}