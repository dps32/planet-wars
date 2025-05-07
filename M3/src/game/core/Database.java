package game.core;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;

public class Database {
	private static final String url = "jdbc:mysql://sql7.freesqldatabase.com:3306/sql7777295?useSSL=false";
	private static final String user = "sql7777295";
	private static final String pass = "tJ1EgfU7Tj";
	
	private static Connection connect() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection(url, user, pass);
			return conn;
		} catch (Exception e) {
			System.out.println("Connection failed: " + e.toString());
			return null;
		}
	}
	
	
	public static boolean query(String q, ArrayList<?> params) {
		try {
			Connection conn = connect();
			PreparedStatement ps = conn.prepareStatement(q);

			if (params != null) {
				for (int i = 0; i < params.size(); i++) {
					ps.setObject(i + 1, params.get(i));
				}
			}

			ps.executeUpdate();
			ps.close();
			conn.close();

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	
}
