package menu;

import java.sql.*;
import java.io.*;

import database.DatabaseConnection;
import main.*;

public class PositionsMenu
{

	public void allPositions()
	{
		System.out.println("Pareigos:");
		Connection conn = null;
		try
		{
			System.out.printf("%3s  %-12s%n", "Id", "Pareigos");

			conn = DatabaseConnection.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM positions ORDER BY position_name");
			while (rs.next())
			{
				System.out.printf("%3s  %-12s%n", rs.getString("id"), rs.getString("position_name"));
			}
		}
		catch (SQLException e) { e.printStackTrace(); }
		finally { DatabaseConnection.closeConnection(conn); }
	}
	
	public void addPosition()
	{
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Prideti naujas pareigas:");

		String position_name = "";
		
		System.out.println("\tIveskite pareigos pavadinima:");
		try { position_name = reader.readLine(); } catch (IOException e) { e.printStackTrace(); }
		while (Functions.isEmptyOrBlank(position_name))
		{
			System.out.println("\tPareigos pavadinimas negali buti tuscias! Iveskite nauja:");
			try { position_name = reader.readLine(); } catch (IOException e) { e.printStackTrace(); }
		}
		
		Connection conn = null;
		PreparedStatement stmt = null;
		try
		{
			conn = DatabaseConnection.getConnection();
			stmt = conn.prepareStatement("INSERT INTO positions (position_name) values (?)");
			stmt.setString(1, position_name);
			stmt.executeUpdate();
			
			System.out.println("\tPareigos pridetos.");
			
		}
		catch (SQLException e) { e.printStackTrace(); }
		finally { DatabaseConnection.closeConnection(conn); }
	}
	
	public void removePosition()
	{
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		this.allPositions();
		System.out.println("Istrinti pareigas:");
		
		int position_id = 0;
		System.out.println("\tIveskite pareigos id, kuria norite istrinti:");
		try { position_id = Integer.parseInt(reader.readLine()); } catch (IOException e) { e.printStackTrace(); }
		
		Connection conn = null;
		PreparedStatement stmt = null;
		try
		{
			conn = DatabaseConnection.getConnection();
			stmt = conn.prepareStatement("DELETE FROM positions WHERE id = ?");
			stmt.setInt(1, position_id);
			stmt.executeUpdate();
			
			System.out.println("\tPareigos istrintos.");
		}
		catch (org.postgresql.util.PSQLException e) { System.out.println("\tKlaida istrintant pareigas. Patikrinkite ar pareigos nera naudojamos."); }
		catch (SQLException e) { e.printStackTrace(); }
		finally { DatabaseConnection.closeConnection(conn); }
	}
}