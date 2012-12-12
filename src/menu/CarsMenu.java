package menu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.*;
import java.text.*;

import database.DatabaseConnection;

public class CarsMenu {

	public static void printMenu()
	{
		System.out.println("\t################    AUTOMOBILIU MENIU    ################");
		System.out.println("\t#                                                       #");
		System.out.println("\t#  cars - Spausdinti visus automobilius.                #");
		System.out.println("\t#  add car - Prideti nauja automobili.                  #");
		System.out.println("\t#                                                       #");
		System.out.println("\t#########################################################");
		System.out.println("");
	}

	public void allCars()
	{
		System.out.println("Automobiliai:");
		Connection conn = null;
		try
		{
			DecimalFormat df = new DecimalFormat("#.#");
			System.out.printf("%3s  %-12s   %-12s   %-5s   %-11s   %-7s%n", "Id", "Marke", "Modelis", "Metai", "Kuro tipas", "Kuro sanaudos");

			conn = DatabaseConnection.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM cars");
			while (rs.next())
			{
				System.out.printf("%3s  %-12s   %-12s   %-5s   %-11s   %-7s%n", 
					rs.getString("id"), rs.getString("make"), rs.getString("model"), rs.getInt("year"), rs.getString("fuel_type"), df.format(rs.getDouble("fuel_consumption")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DatabaseConnection.closeConnection(conn);
		}
	}
	
	public void addCar() throws IOException
	{
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Prideti nauja automobili:");

		String carMake = "";
		String carModel = "";
		String carFuelType = "";
		double carFuelConsumption = 0.0;
		Integer carYear = 0;
		
		System.out.println("\tIveskite automobilio marke:");
		carMake = reader.readLine();
		while (isEmptyOrBlank(carMake))
		{
			System.out.println("\tAutomobilio marke negali buti tuscia! Iveskite nauja:");
			carMake = reader.readLine();
		}
		
		System.out.println("\tIveskite automobilio modeli:");
		carModel = reader.readLine();
		while (isEmptyOrBlank(carModel))
		{
			System.out.println("\tAutomobilio modelis negali buti tuscias! Iveskite nauja:");
			carModel = reader.readLine();
		}
		
		System.out.println("\tIveskite automobilio kuro tipa (pvz.: Benzinas):");
		carFuelType = reader.readLine();
		while (isEmptyOrBlank(carFuelType))
		{
			System.out.println("\tAutomobilio kuro tipas negali buti tuscias! Iveskite nauja:");
			carFuelType = reader.readLine();
		}

		System.out.println("\tIveskite automobilio kuro sanaudas (pvz.: 9.2):");
		carFuelConsumption = Double.parseDouble(reader.readLine());
		while (carFuelConsumption == 0.0)
		{
			System.out.println("\tAutomobilio kuro sanaudos negali buti lygios nuliui! Iveskite naujas:");
			carFuelConsumption = Double.parseDouble(reader.readLine());
		}

		System.out.println("\tIveskite automobilio metus (pvz.: 2012):");
		carYear = Integer.parseInt(reader.readLine());
		while (carYear == 0)
		{
			System.out.println("\tAutomobilio metai negali buti lygus nuliui! Iveskite naujus:");
			carYear = Integer.parseInt(reader.readLine());
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
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DatabaseConnection.closeConnection(conn);
		}
	}
	
	public static boolean isEmptyOrBlank(String str)
	{
		return str == null || str.trim().isEmpty();
	}
}
