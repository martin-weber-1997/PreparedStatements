package prep_statements;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Main class in order to start the application.
 * 
 * @author Daniel May
 * @version 20160223.1
 *
 */
public class PSMain {

	/**
	 * main function that connects the pieces of the application
	 * 
	 * @param args
	 *            command line arguments
	 */
	public static void main(String[] args) {
		CLParser clp = new CLParser(args);
		DBConnector dbc = new DBConnector(clp.getHost(), clp.getPort(), clp.getDatabase(), clp.getUser(),
				clp.getPassword());
		clp.clearPassword();
		int amount = clp.getAmount();

		System.out.println("Connecting ...");
		dbc.connect();
		long a, b;

		System.out.println("Inserting data ...");
		a = System.currentTimeMillis();
		PSCreate psc = new PSCreate(dbc);
		psc.createRandomPerson(amount, 100);
		b = System.currentTimeMillis();
		System.out.println("Inserting needed " + (b - a) + " ms.");

		System.out.println("Selecting data ...");
		a = System.currentTimeMillis();
		PSRead psr = new PSRead(dbc);
		try {
<<<<<<< HEAD
			ResultSet rs = psr.readResult(100);
			if (clp.showData())
=======
			ResultSet rs=psr.readResult(100);
			if(clp.showData().equals("true")){
>>>>>>> 99fc78794a3976a6eb50fd70c160f39440909227
				psr.printResult(rs);
		} catch (SQLException e) {
			System.err.println("Selecting data failed: " + e.getMessage());
		}
		b = System.currentTimeMillis();
		System.out.println("Selecting needed " + (b - a) + " ms.");

		System.out.println("Updating data ...");
		a = System.currentTimeMillis();
		PSUpdate psu = new PSUpdate(dbc);
		psu.updateAll(amount, 100);
		b = System.currentTimeMillis();
		System.out.println("Updating needed " + (b - a) + " ms.");

		System.out.println("Deleting data ...");
		a = System.currentTimeMillis();
		PSDelete psd = new PSDelete(dbc);
		psd.deleteAll(amount, 100);
		b = System.currentTimeMillis();
		System.out.println("Deleting needed " + (b - a) + " ms.");
	}
}