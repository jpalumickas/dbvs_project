package menu;

import java.sql.*;
import java.io.*;
import java.text.*;

import database.DatabaseConnection;
import main.*;

public class CarsMenu {

	public static void printMenu()
	{
		System.out.println("\t################    AUTOMOBILIU MENIU    ################");
		System.out.println("\t#                                                       #");
		System.out.println("\t#  cars - Spausdinti visus automobilius.                #");
		System.out.println("\t#  add car - Prideti nauja automobili.                  #");
		System.out.println("\t#  remove car - Istrinti automobili.                    #");
		System.out.println("\t#                                                       #");
		System.out.println("\t#  car employees - Automobilio darbuotojai.             #");
		System.out.println("\t#  add car employee - Prideti automobilio darbuotoja.   #");
		System.out.println("\t#  remove car employee - Istrinti automobilio darbuotoja#");
		System.out.println("\t#                                                       #");
		System.out.println("\t#########################################################");
		System.out.println("");
	}
	
	private EmployeesMenu employeesMenu = new EmployeesMenu();

	// Cars
	public void allCars()
	{
		if (this.countCars() <= 0)
		{
			System.out.println("\tAutomobiliu sarasas tuscias. Jei norite prideti rasykite \"add car\".");
			return;
		}
		
		System.out.println("\tAutomobiliai:");
		Connection conn = null;
		try
		{
			DecimalFormat df = new DecimalFormat("#.#");
			System.out.printf("\t%3s  %-12s   %-12s   %-5s   %-11s   %-7s%n", "Id", "Marke", "Modelis", "Metai", "Kuro tipas", "Kuro sanaudos");

			conn = DatabaseConnection.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM cars");
			while (rs.next())
			{
				System.out.printf("\t%3s  %-12s   %-12s   %-5s   %-11s   %-7s%n", 
					rs.getString("id"), rs.getString("make"), rs.getString("model"), rs.getInt("year"), rs.getString("fuel_type"), df.format(rs.getDouble("fuel_consumption")));
			}
		}
		catch (SQLException e) { e.printStackTrace(); }
		finally { DatabaseConnection.closeConnection(conn); }
	}
	
	public void addCar()
	{
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("\tPrideti nauja automobili:");

		String carMake = "";
		String carModel = "";
		String carFuelType = "";
		double carFuelConsumption = 0.0;
		Integer carYear = 0;
		
		System.out.println("\tIveskite automobilio marke:");
		try { carMake = reader.readLine(); } catch (IOException e) { e.printStackTrace(); }
		while (Functions.isEmptyOrBlank(carMake))
		{
			System.out.println("\tAutomobilio marke negali buti tuscia! Iveskite nauja:");
			try { carMake = reader.readLine(); } catch (IOException e) { e.printStackTrace(); }
		}
		
		System.out.println("\tIveskite automobilio modeli:");
		try { carModel = reader.readLine(); } catch (IOException e) { e.printStackTrace(); }
		while (Functions.isEmptyOrBlank(carModel))
		{
			System.out.println("\tAutomobilio modelis negali buti tuscias! Iveskite nauja:");
			try { carModel = reader.readLine(); } catch (IOException e) { e.printStackTrace(); }
		}
		
		System.out.println("\tIveskite automobilio kuro tipa (pvz.: Benzinas):");
		try { carFuelType = reader.readLine(); } catch (IOException e) { e.printStackTrace(); }
		while (Functions.isEmptyOrBlank(carFuelType))
		{
			System.out.println("\tAutomobilio kuro tipas negali buti tuscias! Iveskite nauja:");
			try { carFuelType = reader.readLine(); } catch (IOException e) { e.printStackTrace(); }
		}

		System.out.println("\tIveskite automobilio kuro sanaudas (pvz.: 9.2):");
		try { carFuelConsumption = Double.parseDouble(reader.readLine()); } catch (IOException e) { e.printStackTrace(); }
		while (carFuelConsumption == 0.0)
		{
			System.out.println("\tAutomobilio kuro sanaudos negali buti lygios nuliui! Iveskite naujas:");
			try { carFuelConsumption = Double.parseDouble(reader.readLine()); } catch (IOException e) { e.printStackTrace(); }
		}

		System.out.println("\tIveskite automobilio metus (pvz.: 2012):");
		try { carYear = Integer.parseInt(reader.readLine()); } catch (IOException e) { e.printStackTrace(); }
		while (carYear == 0)
		{
			System.out.println("\tAutomobilio metai negali buti lygus nuliui! Iveskite naujus:");
			try { carYear = Integer.parseInt(reader.readLine()); } catch (IOException e) { e.printStackTrace(); }
		}
		
		Connection conn = null;
		PreparedStatement stmt = null;
		try
		{
			conn = DatabaseConnection.getConnection();
			stmt = conn.prepareStatement("INSERT INTO cars (make, model, fuel_type, fuel_consumption, year) values (?, ?, ?, ?, ?)");
			stmt.setString(1, carMake);
			stmt.setString(2, carModel);
			stmt.setString(3, carFuelType);
			stmt.setDouble(4, carFuelConsumption);
			stmt.setInt(5, carYear);
			stmt.executeUpdate();
			
			System.out.println("\tAutomobilis pridetas.");
			
		}
		catch (SQLException e) { e.printStackTrace(); }
		finally { DatabaseConnection.closeConnection(conn); }
	}
	
	public void removeCar()
	{
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		this.allCars();
		
		int car_id = 0;
		System.out.println("\tIveskite automobilio id, kuri norite istrinti:");
		try { car_id = Integer.parseInt(reader.readLine()); } catch (IOException e) {  }
		while ( ! this.isValidCar(car_id))
		{
			System.out.println("\tToks automobilis nerastas! Iveskite nauja:");
			try { car_id = Integer.parseInt(reader.readLine()); } catch (IOException e) {  }
		}
		
		Connection conn = null;
		PreparedStatement stmt = null;
		try
		{
			conn = DatabaseConnection.getConnection();
			stmt = conn.prepareStatement("DELETE FROM cars WHERE id = ?");
			stmt.setInt(1, car_id);
			stmt.executeUpdate();
			
			System.out.println("\tAutomobilis istrintas.");
		}
		catch (org.postgresql.util.PSQLException e) { System.out.println("\tKlaida istrintant automobili. Patikrinkite ar automobilis nera naudojamos."); }
		catch (SQLException e) { e.printStackTrace(); }
		finally { DatabaseConnection.closeConnection(conn); }
	}
	
	public boolean isValidCar(int car_id)
	{
		Connection conn = null;
		Boolean found = false;
		try
		{
			conn = DatabaseConnection.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM cars WHERE id = '"+car_id+"'");
			while (rs.next()) { found = true; } 
		}
		catch (SQLException e) { e.printStackTrace(); }
		finally { DatabaseConnection.closeConnection(conn); }
		return found;
	}
	
	public int countCars()
	{
		Connection conn = null;
		int count = 0;
		try
		{
			conn = DatabaseConnection.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT COUNT(*) AS count FROM cars");
			while (rs.next()) { count = rs.getInt("count"); } 
		}
		catch (SQLException e) { e.printStackTrace(); }
		finally { DatabaseConnection.closeConnection(conn); }
		return count;
	}
	
	// Car employees
	public void allCarEmployees()
	{
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		int car_id = 0;
		
		this.allCars();
		System.out.println("\tIveskite automobilio id:");
		try { car_id = Integer.parseInt(reader.readLine()); } catch (IOException e) { e.printStackTrace(); }
		while ( ! this.isValidCar(car_id))
		{
			System.out.println("\tToks automobilis nerastas! Iveskite nauja:");
			try { car_id = Integer.parseInt(reader.readLine()); } catch (IOException e) { e.printStackTrace(); }
		}
		
		if (this.countCarEmployees(car_id) <= 0)
		{
			System.out.println("\n\tNera darbuotoju, kurie vairuotu sita automobili.");
			return;
		}
		
		System.out.println("\n\tAutomobili vairuojantys darbuotojai:");
		
		Connection conn = null;
		try
		{
			//System.out.printf("\t%-12s  %-12s%n", "Vardas", "Pavarde");

			conn = DatabaseConnection.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT employees.first_name, employees.last_name FROM car_employees JOIN employees ON car_employees.employee_id = employees.id WHERE car_employees.car_id = '"+car_id+"' ORDER BY employees.first_name");
			
			if( ! rs.isBeforeFirst() )
			{
				System.out.println("\tNera darbuotoju, kurie vairuotu si automobili.");
				return;
			}
			
			while (rs.next())
			{
				System.out.printf("\t%-12s  %-12s%n", rs.getString("first_name"), rs.getString("last_name"));
			}
		}
		catch (SQLException e) { e.printStackTrace(); }
		finally { DatabaseConnection.closeConnection(conn); }
	}
	
	public void addCarEmployees()
	{
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		int car_id = 0;
		String car_employees_input = "";
		Integer car_employees[];
		
		this.allCars();
		System.out.println("\tIveskite automobilio id:");
		try { car_id = Integer.parseInt(reader.readLine()); } catch (IOException e) {  }
		while ( ! this.isValidCar(car_id))
		{
			System.out.println("\tToks automobilis nerastas! Iveskite nauja:");
			try { car_id = Integer.parseInt(reader.readLine()); } catch (IOException e) {  }
		}
		
		this.employeesMenu.allEmployees();
		System.out.println("\tIveskite darbuotoju id atskirdami kableliu: (Pvz.: 1,2,3)");
		try { car_employees_input = reader.readLine(); } catch (IOException e) { e.printStackTrace(); }
		car_employees = Functions.getIdsFromSting(car_employees_input);
		while (car_employees.length == 0)
		{
			System.out.println("\tJokie darbuotojai neivesti! Iveskite per naujo:");
			try { car_employees_input = reader.readLine(); } catch (IOException e) { e.printStackTrace(); }
			car_employees = Functions.getIdsFromSting(car_employees_input);
		}
		
		Connection conn = null;
		PreparedStatement stmt = null;
		for (int i = 0; i < car_employees.length; i++)
		{
			try
			{
				conn = DatabaseConnection.getConnection();

				stmt = conn.prepareStatement("INSERT INTO car_employees (car_id, employee_id) values (?, ?)");
				stmt.setInt(1, car_id);
				stmt.setInt(2, car_employees[i]);
				stmt.executeUpdate();
				System.out.println("\tDarbuotojas su id "+car_employees[i]+" pridetas prie automobilio saraso.");
			}
			catch (SQLException e) { System.out.println("\tDarbuotojas su id "+car_employees[i]+" nebuvo ikeltas. Gali buti, kad jau egzistuoja sarase."); }
			finally { DatabaseConnection.closeConnection(conn); }
		}	
	}
	
	public void removeCarEmployees()
	{
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		int car_id = 0;
		String car_employees_input = "";
		Integer car_employees[];
		
		this.allCars();
		System.out.println("\tIveskite automobilio id:");
		try { car_id = Integer.parseInt(reader.readLine()); } catch (IOException e) {  }
		while ( ! this.isValidCar(car_id))
		{
			System.out.println("\tToks automobilis nerastas! Iveskite nauja:");
			try { car_id = Integer.parseInt(reader.readLine()); } catch (IOException e) {  }
		}
		
		this.employeesMenu.allEmployees();
		System.out.println("\tIveskite darbuotoju id atskirdami kableliu: (Pvz.: 1,2,3)");
		try { car_employees_input = reader.readLine(); } catch (IOException e) { e.printStackTrace(); }
		car_employees = Functions.getIdsFromSting(car_employees_input);
		while (car_employees.length == 0)
		{
			System.out.println("\tJokie darbuotojai neivesti! Iveskite per naujo:");
			try { car_employees_input = reader.readLine(); } catch (IOException e) { e.printStackTrace(); }
			car_employees = Functions.getIdsFromSting(car_employees_input);
		}
		
		Connection conn = null;
		PreparedStatement stmt = null;
		for (int i = 0; i < car_employees.length; i++)
		{
			try
			{
				conn = DatabaseConnection.getConnection();

				stmt = conn.prepareStatement("DELETE FROM car_employees WHERE car_id = ? AND employee_id = ?");
				stmt.setInt(1, car_id);
				stmt.setInt(2, car_employees[i]);
				stmt.executeUpdate();
				System.out.println("\tDarbuotojas su id "+car_employees[i]+" istrintas is automobilio saraso.");
			}
			catch (SQLException e) { e.printStackTrace(); }
			finally { DatabaseConnection.closeConnection(conn); }
		}	
	}
	
	public int countCarEmployees(int car_id)
	{
		Connection conn = null;
		int count = 0;
		try
		{
			conn = DatabaseConnection.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT COUNT(*) AS count FROM car_employees WHERE car_id = '"+car_id+"'");
			while (rs.next()) { count = rs.getInt("count"); } 
		}
		catch (SQLException e) { e.printStackTrace(); }
		finally { DatabaseConnection.closeConnection(conn); }
		return count;
	}
}
