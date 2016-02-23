/**
 * 
 */
package prep_statements;

import java.sql.PreparedStatement;

/**
 * @author Martin Weber, Daniel May
 * @version 23.02.2015
 *
 */
public class Statements {

	// private Connection con
	private PreparedStatement create, read, update, delete;
	private String table = "xy";// m

	public Statements(/* Connection Objekt con */) {

		//create = con.prepareStatement("INSERT INTO" + table + "VALUES(?,?,?)");
		//read=con.prepareStatement("SELECT * FROM"+table+"WHERE abc=?");
		//update=con.prepareStatement("");
		//delete=con.prepareStatement("");

	}
}
