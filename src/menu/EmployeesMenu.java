package menu;

import java.sql.*;
import java.io.*;

import database.DatabaseConnection;
import main.*;

public class EmployeesMenu
{
	public void printMenu()
	{
		PositionsMenu positionsMenu = new PositionsMenu();
		
		System.out.println("\t################    DARBUOTOJU  MENIU    ################");
		System.out.println("\t#                                                       #");
		System.out.println("\t#  employees - Spausdinti visus darbuotojus.            #");
		System.out.println("\t#  add employee - Prideti nauja darbuotoja.             #");
		System.out.println("\t#  remove employee - Istrinti darbuotoja.               #");
		System.out.println("\t#                                                       #");
		System.out.println("\t#########################################################");
		positionsMenu.printMenu(false);
	}
	
	public void allEmployees()
	{
		if (this.countEmployees() <= 0)
		{
			System.out.println("\tDarbuotoju sarasas tuscias. Jei norite prideti rasykite \"add employee\".");
			return;
		}
		
		System.out.println("\tDarbuotojai:");
		Connection conn = null;
		try
		{
			System.out.printf("\t%3s  %-12s   %-12s   %-12s   %-8s   %-13s   %-7s%n", "Id", "Vardas", "Pavarde", "Gimimo diena", "Lytis", "Asmens kodas", "Pareigos");

			conn = DatabaseConnection.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM employees LEFT JOIN positions ON employees.position_id = positions.id ORDER BY employees.id");
			while (rs.next())
			{

				String sex = (rs.getString("sex").equals("V")) ? "Vyras" : "Moteris";
				System.out.printf("\t%3s  %-12s   %-12s   %-12s   %-8s   %-13s   %-7s%n",
					rs.getString("id"), rs.getString("first_name"), rs.getString("last_name"), rs.getString("birthday"), sex, rs.getLong("dno"), rs.getString("position_name"));
			}
		}
		catch (SQLException e) { e.printStackTrace(); }
		finally { DatabaseConnection.closeConnection(conn); }
	}
	
	public void addEmployee()
	{
		PositionsMenu positionsMenu = new PositionsMenu();
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("\tPrideti darbuotoja:");

		String first_name = "";
		String last_name = "";
		String birthday = "";
		java.sql.Date birthday_sql;
		char sex = '\u0000';
		long dno = 0L;
		int position_id = 0;
		
		
		System.out.println("\tIveskite darbuotojo varda:");
		try { first_name = reader.readLine(); } catch (IOException e) { e.printStackTrace(); }
		while (Functions.isEmptyOrBlank(first_name))
		{
			System.out.println("\tDarbuotojo vardas negali buti tuscias! Iveskite nauja:");
			try { first_name = reader.readLine(); } catch (IOException e) { e.printStackTrace(); }
		}
		
		System.out.println("\tIveskite darbuotojo pavarde:");
		try { last_name = reader.readLine(); } catch (IOException e) { e.printStackTrace(); }
		while (Functions.isEmptyOrBlank(last_name))
		{
			System.out.println("\tDarbuotojo pavarde negali buti tuscia! Iveskite nauja:");
			try { last_name = reader.readLine(); } catch (IOException e) { e.printStackTrace(); }
		}
		
		System.out.println("\tIveskite darbuotojo gimimo data: (Formatas: yyyy-MM-dd)");
		try { birthday = reader.readLine(); } catch (IOException e) { e.printStackTrace(); }
		while ( ! Functions.isValidDate(birthday))
		{
			System.out.println("\tDarbuotojo gimimo data neteisinga! Iveskite nauja: (Formatas: yyyy-MM-dd)");
			try { birthday = reader.readLine(); } catch (IOException e) { e.printStackTrace(); }
		}
		birthday_sql = java.sql.Date.valueOf(birthday);
		
		System.out.println("\tIveskite darbuotojo lyti: (V - vyras, M - moteris)");
		try { sex = reader.readLine().toUpperCase().charAt(0); } catch (IOException e) { e.printStackTrace(); }
		while ( ! Functions.isValidSex(sex))
		{
			System.out.println("\tNeteisinga darbuotojo lytis. Iveskite nauja: (V - vyras, M - moteris)");
			try { sex = reader.readLine().toUpperCase().charAt(0); } catch (IOException e) { e.printStackTrace(); }
		}
		
		System.out.println("\tIveskite darbuotojo asmens koda:");
		try { dno = Long.parseLong(reader.readLine()); } catch (IOException e) { e.printStackTrace(); }
		while ( ! Functions.isValidDno(dno))
		{
			System.out.println("\tNeteisingas darbuotojo asmens kodas. Iveskite nauja:");
			try { dno = Long.parseLong(reader.readLine()); } catch (IOException e) { e.printStackTrace(); }
		}
		
		positionsMenu.allPositions();
		System.out.println("\tIveskite darbuotojo pareigas:");
		try { position_id = Integer.parseInt(reader.readLine()); } catch (IOException e) { e.printStackTrace(); }
		while ( ! positionsMenu.isValidPosition(position_id))
		{
			System.out.println("\tNeteisingos darbuotojo pareigos. Iveskite naujas:");
			try { position_id = Integer.parseInt(reader.readLine()); } catch (IOException e) { e.printStackTrace(); }
		}
		
		Connection conn = null;
		PreparedStatement stmt = null;
		try
		{
			conn = DatabaseConnection.getConnection();
			stmt = conn.prepareStatement("INSERT INTO employees (first_name, last_name, birthday, sex, dno, position_id) values (?, ?, ?, ?, ?, ?)");
			stmt.setString(1, first_name);
			stmt.setString(2, last_name);
			stmt.setDate(3, birthday_sql);
			stmt.setString(4, String.valueOf(sex));
			stmt.setLong(5, dno);
			stmt.setInt(6, position_id);
			stmt.executeUpdate();
			
			System.out.println("\tDarbuotojas pridetetas.");
			
		}
		catch (SQLException e) { e.printStackTrace(); }
		finally { DatabaseConnection.closeConnection(conn); }
	}
	
	public void removeEmployee()
	{
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		this.allEmployees();
		System.out.println("\tIstrinti darbuotoja:");
		
		int employee_id = 0;
		System.out.println("\tIveskite darbuotojo id, kuri norite istrinti:");
		try { employee_id = Integer.parseInt(reader.readLine()); } catch (IOException e) {  }
		while ( ! this.isValidEmployee(employee_id))
		{
			System.out.println("\tToks darbuotojas nerastas! Iveskite nauja:");
			try { employee_id = Integer.parseInt(reader.readLine()); } catch (IOException e) {  }
		}
		
		Connection conn = null;
		PreparedStatement stmt = null;
		try
		{
			conn = DatabaseConnection.getConnection();
			stmt = conn.prepareStatement("DELETE FROM employees WHERE id = ?");
			stmt.setInt(1, employee_id);
			stmt.executeUpdate();
			
			System.out.println("\tDarbuotojas istrintas.");
		}
		catch (org.postgresql.util.PSQLException e) { System.out.println("\tKlaida istrintant darbuotoja. Patikrinkite ar darbuotojas nera naudojamos."); }
		catch (SQLException e) { e.printStackTrace(); }
		finally { DatabaseConnection.closeConnection(conn); }
	}
	
	public boolean isValidEmployee(int employee_id)
	{
		Connection conn = null;
		Boolean found = false;
		try
		{
			conn = DatabaseConnection.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM employees WHERE id = '"+employee_id+"'");
			while (rs.next()) { found = true; } 
		}
		catch (SQLException e) { e.printStackTrace(); }
		finally { DatabaseConnection.closeConnection(conn); }
		return found;
	}
	
	public int countEmployees()
	{
		Connection conn = null;
		int count = 0;
		try
		{
			conn = DatabaseConnection.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT COUNT(*) AS count FROM employees");
			while (rs.next()) { count = rs.getInt("count"); } 
		}
		catch (SQLException e) { e.printStackTrace(); }
		finally { DatabaseConnection.closeConnection(conn); }
		return count;
	}
}