package application;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBconnection {
	private final static String dburl = "jdbc:mysql://localhost:3306/cabinetdentaire";
	private final static String username = "root"; 
	private final static String password = "root"; 
	private static Connection connect;

	public DBconnection() {

	}

	public static Connection getConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connect = DriverManager.getConnection(dburl, username, password);
			System.out.println("connected");
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("echoueeee!!!!");
		}
		return connect;
	}

}
