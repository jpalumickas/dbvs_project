package menu;

import java.sql.*;
import java.io.*;
import java.text.*;

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
	
	private CarsMenu carsMenu = new CarsMenu();
	
	// Secondments
	
	public void allSecondments()
	{
		if (this.countSecondments() <= 0)
		{
			System.out.println("\tKomandiruociu sarasas tuscias. Jei norite prideti rasykite \"add secondment\".");
			return;
		}
		
		System.out.println("\tKomandiruotes:");
		Connection conn = null;
		try
		{
			DecimalFormat df = new DecimalFormat("#.##");
			System.out.printf("\t%3s  %-20s   %-20s   %-20s   %-8s   %-13s   %-7s%n", "Id", "Marsrutas", "Tipas", "Automobilis", "Pinigai", "Nuvaziuota km", "Laikotarpis");

			conn = DatabaseConnection.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT S.*,ST.name AS type, (c.make || ' ' || c.model) AS car FROM secondments S LEFT JOIN secondment_types ST ON S.type_id = ST.id LEFT JOIN cars c ON S.car_id = C.id ORDER BY S.to_date");
			while (rs.next())
			{
				String period = Functions.calculateDays(rs.getString("from_date"), rs.getString("to_date")) + " d."; //("+rs.getString("from_date")+" - "+rs.getString("to_date")+")";
				System.out.printf("\t%3s  %-20s   %-20s   %-20s   %-8s   %-13s   %-7s%n",
					rs.getString("id"), rs.getString("from_where")+" - "+rs.getString("to_where"), rs.getString("type"), rs.getString("car"), rs.getString("money_for_secondment"), df.format(rs.getDouble("driven_km")), period);
			}
		}
		catch (SQLException e) { e.printStackTrace(); }
		finally { DatabaseConnection.closeConnection(conn); }
	}
	
	public void addSecondment()
	{
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("\tPrideti komandiruote:\n");

		String from_where = "";
		String to_where = "";
		int type_id = 0;
		int car_id = 0;
		String money_for_secondment = "";
		Double driven_km = 0.0;
		String from_date_input = "";
		String to_date_input = "";
		java.sql.Date from_date;
		java.sql.Date to_date;
		
		
		System.out.println("\tIveskite isvykimo vieta:");
		try { from_where = reader.readLine(); } catch (IOException e) { e.printStackTrace(); }
		while (Functions.isEmptyOrBlank(from_where))
		{
			System.out.println("\tIsvykimo vieta negali buti tuscia! Iveskite nauja:");
			try { from_where = reader.readLine(); } catch (IOException e) { e.printStackTrace(); }
		}
		
		System.out.println("\tIveskite atvykimo vieta:");
		try { to_where = reader.readLine(); } catch (IOException e) { e.printStackTrace(); }
		while (Functions.isEmptyOrBlank(to_where))
		{
			System.out.println("\tAtvykimo vieta negali buti tuscia! Iveskite nauja:");
			try { to_where = reader.readLine(); } catch (IOException e) { e.printStackTrace(); }
		}
		
		this.allSecondmentTypes();
		System.out.println("\tIveskite komandiruotes tipo id:");
		try { type_id = Integer.parseInt(reader.readLine()); } catch (IOException e) { e.printStackTrace(); }
		while ( ! this.isValidSecondmentType(type_id))
		{
			System.out.println("\tKomandiruotes tipas neteisingas. Iveskite nauja id:");
			try { type_id = Integer.parseInt(reader.readLine()); } catch (IOException e) { e.printStackTrace(); }
		}
		
		this.carsMenu.allCars();
		System.out.println("\tIveskite automobilio id:");
		try { car_id = Integer.parseInt(reader.readLine()); } catch (IOException e) { e.printStackTrace(); }
		while ( ! this.carsMenu.isValidCar(car_id))
		{
			System.out.println("\tAutomobilis su tokiu id nerastas. Iveskite nauja id:");
			try { car_id = Integer.parseInt(reader.readLine()); } catch (IOException e) { e.printStackTrace(); }
		}
		
		System.out.println("\tIveskite kiek pinigu skirta komandiruotei:");
		try { money_for_secondment = reader.readLine(); } catch (IOException e) { e.printStackTrace(); }
		while (Functions.isEmptyOrBlank(money_for_secondment))
		{
			System.out.println("\tPinigu skaicius negali buti tuscias! Iveskite nauja:");
			try { money_for_secondment = reader.readLine(); } catch (IOException e) { e.printStackTrace(); }
		}
		
		System.out.println("\tIveskite kiek nuvaziuota kilometru su automobiliu:");
		try { driven_km = Double.parseDouble(reader.readLine()); } catch (IOException e) { e.printStackTrace(); }
		//while (Functions.isEmptyOrBlank(money_for_secondment))
		//{
		//	System.out.println("\tAutomobilio! Iveskite nauja:");
		//	try { money_for_secondment = reader.readLine(); } catch (IOException e) { e.printStackTrace(); }
		//}
		
		System.out.println("\tIveskite isvykimo data: (Formatas yyyy-MM-dd)");
		try { from_date_input = reader.readLine(); } catch (IOException e) { e.printStackTrace(); }
		while ( ! Functions.isValidDate(from_date_input))
		{
			System.out.println("\tNeteisingai ivesta isvykimo data! Iveskite nauja: (Formatas yyyy-MM-dd)");
			try { from_date_input = reader.readLine(); } catch (IOException e) { e.printStackTrace(); }
		}
		from_date = java.sql.Date.valueOf(from_date_input);
		
		System.out.println("\tIveskite gryzimo data: (Formatas yyyy-MM-dd)");
		try { to_date_input = reader.readLine(); } catch (IOException e) { e.printStackTrace(); }
		while ( ! Functions.isValidDate(to_date_input))
		{
			System.out.println("\tNeteisingai ivesta gryzimo data! Iveskite nauja: (Formatas yyyy-MM-dd)");
			try { to_date_input = reader.readLine(); } catch (IOException e) { e.printStackTrace(); }
		}
		to_date = java.sql.Date.valueOf(to_date_input);
		
		Connection conn = null;
		PreparedStatement stmt = null;
		try
		{
			conn = DatabaseConnection.getConnection();
			stmt = conn.prepareStatement("INSERT INTO secondments (from_where, to_where, type_id, car_id, money_for_secondment, driven_km, from_date, to_date) values (?, ?, ?, ?, ?, ?, ?, ?)");
			stmt.setString(1, from_where);
			stmt.setString(2, to_where);
			stmt.setInt(3, type_id);
			stmt.setInt(4, car_id);
			stmt.setString(5, money_for_secondment);
			stmt.setDouble(6, driven_km);
			stmt.setDate(7, from_date);
			stmt.setDate(8, to_date);
			stmt.executeUpdate();
			
			System.out.println("\tKomandiruote prideta.");
			
		}
		catch (SQLException e) { e.printStackTrace(); }
		finally { DatabaseConnection.closeConnection(conn); }
	}
	
	public void removeSecondment()
	{
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		this.allSecondments();
		
		int secondment_id = 0;
		System.out.println("\tIveskite komandiruotes id, kuria norite istrinti:");
		try { secondment_id = Integer.parseInt(reader.readLine()); } catch (IOException e) {  }
		while ( ! this.isValidSecondment(secondment_id))
		{
			System.out.println("\tTokia komandiruote nerasta! Iveskite nauja:");
			try { secondment_id = Integer.parseInt(reader.readLine()); } catch (IOException e) {  }
		}
		
		Connection conn = null;
		PreparedStatement stmt = null;
		try
		{
			conn = DatabaseConnection.getConnection();
			stmt = conn.prepareStatement("DELETE FROM secondments WHERE id = ?");
			stmt.setInt(1, secondment_id);
			stmt.executeUpdate();
			
			System.out.println("\tKomandiruote istrinta.");
		}
		catch (org.postgresql.util.PSQLException e) { System.out.println("\tKlaida istrintant komandiruote. Patikrinkite ar komandiruote nera naudojama."); }
		catch (SQLException e) { e.printStackTrace(); }
		finally { DatabaseConnection.closeConnection(conn); }
	}
	
	public boolean isValidSecondment(int secondment_id)
	{
		Connection conn = null;
		Boolean found = false;
		try
		{
			conn = DatabaseConnection.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM secondments WHERE id = '"+secondment_id+"'");
			while (rs.next()) { found = true; } 
		}
		catch (SQLException e) { e.printStackTrace(); }
		finally { DatabaseConnection.closeConnection(conn); }
		return found;
	}
	
	public int countSecondments()
	{
		Connection conn = null;
		int count = 0;
		try
		{
			conn = DatabaseConnection.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT COUNT(*) AS count FROM secondments");
			while (rs.next()) { count = rs.getInt("count"); } 
		}
		catch (SQLException e) { e.printStackTrace(); }
		finally { DatabaseConnection.closeConnection(conn); }
		return count;
	}
	
	// Secondment Types
	public void allSecondmentTypes()
	{
		System.out.println("\tKomandiruociu tipai:");
		Connection conn = null;
		try
		{
			System.out.printf("\t%3s  %-12s%n", "Id", "Tipas");

			conn = DatabaseConnection.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM secondment_types ORDER BY name");
			while (rs.next())
			{
				System.out.printf("\t%3s  %-12s%n", rs.getString("id"), rs.getString("name"));
			}
		}
		catch (SQLException e) { e.printStackTrace(); }
		finally { DatabaseConnection.closeConnection(conn); }
	}
	
	public void addSecondmentType()
	{
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("\tPrideti nauja komandiruotes tipa:");

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
		System.out.println("\tIstrinti komandiruotes tipa:");
		
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
	
	public boolean isValidSecondmentType(int secondment_id)
	{
		Connection conn = null;
		Boolean found = false;
		try
		{
			conn = DatabaseConnection.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM secondment_types WHERE id = '"+secondment_id+"'");
			while (rs.next()) { found = true; } 
		}
		catch (SQLException e) { e.printStackTrace(); }
		finally { DatabaseConnection.closeConnection(conn); }
		return found;
	}
	
	public int countSecondmentTypes()
	{
		Connection conn = null;
		int count = 0;
		try
		{
			conn = DatabaseConnection.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT COUNT(*) AS count FROM secondment_types");
			while (rs.next()) { count = rs.getInt("count"); } 
		}
		catch (SQLException e) { e.printStackTrace(); }
		finally { DatabaseConnection.closeConnection(conn); }
		return count;
	}
}