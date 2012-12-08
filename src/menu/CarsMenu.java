package menu;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import database.DatabaseConnection;

public class CarsMenu {

	public static void printMenu() {

	}

	public static void allCars() {
		Connection conn = null;
		try {
			System.out.printf("%1s  %-7s   %-7s   %-6s   %-6s%n", "n",
					"result1", "result2", "time1", "time2");
			
			conn = DatabaseConnection.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM cars");
			while (rs.next()) {
				String l = rs.getString("model");// your sql record
													// saved as string
				System.out.println(l);// writes your sql record
			}
			conn.close();
			System.out.println("aa");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DatabaseConnection.closeConnection(conn);
		}
	}
}
