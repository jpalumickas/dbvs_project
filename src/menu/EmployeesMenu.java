package menu;

import java.sql.*;
import java.io.*;

import database.DatabaseConnection;
import main.*;

public class EmployeesMenu
{	
	public void allEmployees()
	{
		System.out.println("Darbuotojai:");
		Connection conn = null;
		try
		{
			System.out.printf("%3s  %-12s   %-12s   %-12s   %-8s   %-13s   %-7s%n", "Id", "Vardas", "Pavarde", "Gimimo diena", "Lytis", "Asmens kodas", "Pareigos");

			conn = DatabaseConnection.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM employees LEFT JOIN positions ON employees.position_id = positions.id ORDER BY employees.id");
			while (rs.next())
			{

				String sex = (rs.getString("sex").equals("V")) ? "Vyras" : "Moteris";
				System.out.printf("%3s  %-12s   %-12s   %-12s   %-8s   %-13s   %-7s%n",
					rs.getString("id"), rs.getString("first_name"), rs.getString("last_name"), rs.getString("birthday"), sex, rs.getLong("dno"), rs.getString("position_name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DatabaseConnection.closeConnection(conn);
		}
	}
}