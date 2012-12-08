import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import database.*;
import menu.*;

public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				System.in));

		BaseMenu.printMenu();
		CarsMenu.allCars();
		for (;;) {
			String key = reader.readLine();
			if (key.equals("quit"))
			{
				System.out.println("Programa isjungiama.");
				break;
			}
			else if (key.equals("menu")) BaseMenu.printMenu(); 
			else if (key.equals("cars")) CarsMenu.allCars(); 
			else if (key.equals("test")) {
				try {
					Connection conn = DatabaseConnection.getConnection();
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
				}

				System.out.println("aa");
			}

		}
	}
}
