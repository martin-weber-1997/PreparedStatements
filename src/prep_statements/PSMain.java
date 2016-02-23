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
		dbc.connect();
	}
}