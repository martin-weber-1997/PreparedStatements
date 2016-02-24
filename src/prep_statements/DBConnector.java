package prep_statements;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.postgresql.ds.PGSimpleDataSource;

/**
 * The DBConnector is used to connect to a database.
 * 
 * @author Daniel May
 * @version 20160224.1
 *
 */
public class DBConnector {

	private PGSimpleDataSource ds;
	private Connection con;

	/**
	 * constructor that generates and configures a new DataSource
	 * 
	 * @param host
	 *            host name
	 * @param port
	 *            port number
	 * @param database
	 *            database name
	 * @param user
	 *            user name
	 * @param password
	 *            password
	 */
	public DBConnector(String host, int port, String database, String user, String password) {
		ds = new PGSimpleDataSource();
		ds.setServerName(host);
		ds.setDatabaseName(database);
		ds.setUser(user);
		ds.setPassword(password);
		ds.setPortNumber(port);
	}

	/**
	 * Connects to the database. If an error occurs the connection will be
	 * closed.
	 */
	public void connect() {
		try {
			con = ds.getConnection();
		} catch (SQLException e) {
			System.err.println("Connection failed. Please check the provided connection data.");
			System.err.println(e.getMessage());
			closeConnection();
			System.exit(-1);
		}
	}

	/**
	 * Wrapper method for prepareStatement. If an error occurs, a message will
	 * be displayed and the application will be terminated.
	 * 
	 * @param statem
	 *            statement to prepare
	 * @return PreparedStatement object
	 */
	public PreparedStatement prepareStatement(String statem) {
		try {
			return con.prepareStatement(statem);
		} catch (SQLException e) {
			System.err.println(
					"Preparing statement failed. A database access error occured or the connection is closed.");
			System.err.println(e.getMessage());
			System.exit(-1);
		}
		return null;
	}

	/**
	 * Closes the connection. If an error occurs, a message will be displayed
	 * and the application will be terminated.
	 */
	private void closeConnection() {
		try {
			con.close();
		} catch (SQLException e) {
			System.err.println("Closing connection failed.");
			System.err.println(e.getMessage());
			System.exit(-1);
		}
	}
}