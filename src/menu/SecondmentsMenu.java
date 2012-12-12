package menu;

import java.sql.*;
import java.io.*;

import database.DatabaseConnection;
import main.*;

public class SecondmentsMenu
{
	public void printMenu()
	{
		System.out.println("\t################  KOMANDIRUOCIU   MENIU  ################");
		System.out.println("\t#                                                       #");
		System.out.println("\t#  secondments - Spausdinti visas komandiruotes.        #");
		System.out.println("\t#  add secondment - Prideti komandiruote.               #");
		System.out.println("\t#  remove secondment - Istrinti komandiruote.           #");
		System.out.println("\t#                                                       #");
		System.out.println("\t#  secondment types - Komandiruociu tipu sarasas        #");
		System.out.println("\t#  add secondment type - Prideti komandiruotes tipa     #");
		System.out.println("\t#  remove secondment type - Istrinti komandiruotes tipa #");
		System.out.println("\t#                                                       #");
		System.out.println("\t#########################################################");
		System.out.println("");
	}
	
	// Secondments
	
		// Content goes here...
	
	// Secondment Types
	public void allSecondmentTypes()
	{
		System.out.println("Komandiruociu tipai:");
		Connection conn = null;
		try
		{
			System.out.printf("%3s  %-12s%n", "Id", "Tipas");

			conn = DatabaseConnection.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM secondment_types ORDER BY name");
			while (rs.next())
			{
				System.out.printf("%3s  %-12s%n", rs.getString("id"), rs.getString("name"));
			}
		}
		catch (SQLException e) { e.printStackTrace(); }
		finally { DatabaseConnection.closeConnection(conn); }
	}
	
	public void addSecondmentType()
	{
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Prideti nauja komandiruotes tipa:");

		String secondment_type_name = "";
		
		System.out.println("\tIveskite komandiruotes tipa:");
		try { secondment_type_name = reader.readLine(); } catch (IOException e) { e.printStackTrace(); }
		while (Functions.isEmptyOrBlank(secondment_type_name))
		{
			System.out.println("\tKomandiruotes tipas negali buti tuscias! Iveskite nauja:");
			try { secondment_type_name = reader.readLine(); } catch (IOException e) { e.printStackTrace(); }
		}
		
		Connection conn = null;
		PreparedStatement stmt = null;
		try
		{
			conn = DatabaseConnection.getConnection();
			stmt = conn.prepareStatement("INSERT INTO secondment_types (name) values (?)");
			stmt.setString(1, secondment_type_name);
			stmt.executeUpdate();
			
			System.out.println("\tKomandiruotes tipas pridetas.");
			
		}
		catch (SQLException e) { e.printStackTrace(); }
		finally { DatabaseConnection.closeConnection(conn); }
	}
	
	public void removeSecondmentType()
	{
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		this.allSecondmentTypes();
		System.out.println("Istrinti komandiruotes tipa:");
		
		int secondment_type_id = 0;
		System.out.println("\tIveskite komandiruotes tipo id, kuri norite istrinti:");
		try { secondment_type_id = Integer.parseInt(reader.readLine()); } catch (IOException e) { e.printStackTrace(); }
		
		Connection conn = null;
		PreparedStatement stmt = null;
		try
		{
			conn = DatabaseConnection.getConnection();
			stmt = conn.prepareStatement("DELETE FROM secondment_types WHERE id = ?");
			stmt.setInt(1, secondment_type_id);
			stmt.executeUpdate();
			
			System.out.println("\tKomandiruotes tipas istrintas.");
		}
		catch (org.postgresql.util.PSQLException e) { System.out.println("\tKlaida istrintant komandiruotes tipa.\n\tPatikrinkite ar komandiruotes tipas nera naudojamos."); }
		catch (SQLException e) { e.printStackTrace(); }
		finally { DatabaseConnection.closeConnection(conn); }
	}
}