/**
 * 
 */
package prep_statements;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Martin Weber
 * @version 24.02.2015
 *
 */
public class PSRead {
	
	private PreparedStatement read;
	
	/**
	 * Constructor for the Prepared Statement 
	 * @param con the DB connection needed for the prepared Statement
	 */
	public PSRead(DBConnector con){
		read=con.prepareStatement("SELECT * FROM person WHERE nummer > ?");
	}
	
	public ResultSet readResult(int border) throws SQLException{
		try {
			read.setInt(1, border);
			return read.executeQuery();
		} catch (SQLException e) {
			System.err.println("Execution of Query failed"+e.getMessage());
			throw new SQLException();
		}
		
	}
}
