package prep_statements;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.postgresql.ds.PGSimpleDataSource;

/**
 * The DBConnector is used to connect to a database.
 * 
 * @author Daniel May
 * @version 20160223.1
 *
 */
public class DBConnector {

	private PGSimpleDataSource ds;
	// private Connection con;

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
	 * connects to the database
	 */
	public void connect() {
		try {
			Connection con = ds.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}