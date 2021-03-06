package menu;

import java.sql.*;
import java.io.*;

import database.DatabaseConnection;
import main.*;

public class PositionsMenu
{
	public void printMenu()
	{
		this.printMenu(true);
	}
	
	public void printMenu(Boolean showHeader)
	{
		if (showHeader)
		System.out.println("\t################     PAREIGU   MENIU     ################");
		System.out.println("\t#                                                       #");
		System.out.println("\t#  positions - Spausdinti visas pareigas.               #");
		System.out.println("\t#  add position - Prideti naujas pareigas.              #");
		System.out.println("\t#  remove position - Istrinti pareigas.                 #");
		System.out.println("\t#                                                       #");
		System.out.println("\t#########################################################");
		System.out.println("");
	}

	public void allPositions()
	{
		if (this.countPositions() <= 0)
		{
			System.out.println("\tPareigu sarasas tuscias. Jei norite prideti rasykite \"add position\".");
			return;
		}
		
		System.out.println("\tPareigos:");
		Connection conn = null;
		try
		{
			System.out.printf("\t%3s  %-12s%n", "Id", "Pareigos");

			conn = DatabaseConnection.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM positions ORDER BY position_name");
			while (rs.next())
			{
				System.out.printf("\t%3s  %-12s%n", rs.getString("id"), rs.getString("position_name"));
			}
		}
		catch (SQLException e) { e.printStackTrace(); }
		finally { DatabaseConnection.closeConnection(conn); }
	}
	
	public void addPosition()
	{
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("\tPrideti naujas pareigas:");

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
		System.out.println("\tIstrinti pareigas:");
		
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
	
	public boolean isValidPosition(int position_id)
	{
		Connection conn = null;
		Boolean found = false;
		try
		{
			conn = DatabaseConnection.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM positions WHERE id = '"+position_id+"'");
			while (rs.next()) { found = true; } 
		}
		catch (SQLException e) { e.printStackTrace(); }
		finally { DatabaseConnection.closeConnection(conn); }
		return found;
	}
	
	public int countPositions()
	{
		Connection conn = null;
		int count = 0;
		try
		{
			conn = DatabaseConnection.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT COUNT(*) AS count FROM positions");
			while (rs.next()) { count = rs.getInt("count"); } 
		}
		catch (SQLException e) { e.printStackTrace(); }
		finally { DatabaseConnection.closeConnection(conn); }
		return count;
	}
}