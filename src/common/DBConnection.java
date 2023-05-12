package common;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
	public static Connection getConnection() throws Exception {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		String id = "java", pwd = "1234";
		String url = "jdbc:oracle:thin:@localhost:1521/xe";
		Connection con = DriverManager.getConnection(url, id, pwd);
		return con;
	}
	
}
