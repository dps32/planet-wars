package game.application;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import java.sql.Statement;

public class Database {
	private static final String url = "jdbc:mysql://sql7.freesqldatabase.com:3306/sql7779453?useSSL=false";
	private static final String user = "sql7779453";
	private static final String pass = "vSbpssnPdD";
	
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

	// devuelve el id de la row insertada
	public static int insertAndReturnId(String q, ArrayList<?> params) {
		try {
			Connection conn = connect();
			PreparedStatement ps = conn.prepareStatement(q, Statement.RETURN_GENERATED_KEYS);

			if (params != null) {
				for (int i = 0; i < params.size(); i++) {
					ps.setObject(i + 1, params.get(i));
				}
			}

			ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				int id = rs.getInt(1);
				rs.close();
				ps.close();
				conn.close();
				return id;
			} else {
				rs.close();
				ps.close();
				conn.close();
				throw new RuntimeException("error");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("error while query");
		}
	}

}
