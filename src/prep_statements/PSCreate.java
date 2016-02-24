package prep_statements;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

/**
 * Simple class for testing Preparing Statements with Create Operations.
 * 
 * @author Martin Weber
 * @version 20160224.1
 *
 */
public class PSCreate {
	private PreparedStatement create;

	/**
	 * constructor
	 * 
	 * @param con
	 *            the database connector
	 */
	public PSCreate(DBConnector con) {
		create = con.prepareStatement("INSERT INTO person VALUES(?,?,?)");
	}

	/**
	 * Method to insert a person into the postgresql database schokofabrik into
	 * the table person with Prepared Statements
	 * 
	 * @param number
	 *            id of the person
	 * @param name
	 *            the name of the person
	 * @param surname
	 *            the surname of the person
	 */
	public void insertPerson(int number, String name, String surname) {
		try {
			create.setInt(1, number);
			create.setString(2, name);
			create.setString(3, surname);
			create.execute();
		} catch (SQLException e) {
			System.err.println("Inserting a new Person failed");
			System.err.println(e.getMessage());
		}
	}

	/**
	 * Method to create a defined amount of random inserts with the insertPerson
	 * Method The generated inserts are filled with useless data
	 * 
	 * @param count
	 *            number of inserts
	 * @param start
	 *            id where the inserts should start
	 */
	public void createRandomPerson(int count, int start) {
		for (int i = start; i < count + start; i++) {
			/*
			 * Auf Stackoverflow gefunden generiert random ID
			 */
			String s = UUID.randomUUID().toString().substring(0, 20);
			insertPerson(i, s, s);
		}
	}
}