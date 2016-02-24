package prep_statements;

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

		System.out.println("Connecting ...");
		dbc.connect();

		System.out.println("Inserting data ...");
		long a = System.currentTimeMillis();
		PSCreate psc = new PSCreate(dbc);
		psc.createRandomPerson(10000, 10000);
		long b = System.currentTimeMillis();
		System.out.println("Inserting needed " + (b - a) + " ms.");

		System.out.println("Updating data ...");
		a = System.currentTimeMillis();
		PSUpdate psu = new PSUpdate(dbc);
		psu.updateAll(10000, 10000);
		b = System.currentTimeMillis();
		System.out.println("Updating needed " + (b - a) + " ms.");

		System.out.println("Deleting data ...");
		a = System.currentTimeMillis();
		PSDelete psd = new PSDelete(dbc);
		psd.deleteAll(10000, 10000);
		b = System.currentTimeMillis();
		System.out.println("Deleting needed " + (b - a) + " ms.");
	}
}