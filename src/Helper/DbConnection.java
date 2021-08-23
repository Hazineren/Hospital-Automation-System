package Helper;
import java.sql.*;

public class DbConnection {
	
	Connection c = null;
	 
	public DbConnection() {}
	 
	public Connection connDb() {
		try {
			this.c = DriverManager.getConnection("jdbc:mariadb://localhost:3325/hospital?user=root&password=12345");
			return c;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return c;
	} 

}
