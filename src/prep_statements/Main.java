package prep_statements;

/**
 * Main class in order to start the application.
 * 
 * @author Daniel May
 * @version 20160223.1
 *
 */
public class Main {

	/**
	 * main function that connects the pieces of the application
	 * 
	 * @param args
	 *            command line arguments
	 */
	public static void main(String[] args) {
		CLParser clp = new CLParser(args);
		/*
		 * System.out.println("DB: " + clp.getDatabase()); System.out.println(
		 * "Host: " + clp.getHost()); System.out.println("Port: " +
		 * clp.getPort()); System.out.println("User: " + clp.getUser());
		 * System.out.println("Password: " + clp.getPassword());
		 */
		DBConnector dbc = new DBConnector(clp.getHost(), clp.getPort(), clp.getDatabase(), clp.getUser(),
				clp.getPassword());
		dbc.connect();
	}
}