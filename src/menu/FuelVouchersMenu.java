package menu;

import java.sql.*;
import java.io.*;

import database.DatabaseConnection;
import main.*;

public class FuelVouchersMenu
{
	public void printMenu()
	{
		PositionsMenu positionsMenu = new PositionsMenu();
		
		System.out.println("\t################    KURO CEKIU  MENIU    ################");
		System.out.println("\t#                                                       #");
		System.out.println("\t#  fuel vouchers - Spausdinti visus kuro cekius.        #");
		System.out.println("\t#  add fuel voucher - Prideti nauja kuro ceki.          #");
		System.out.println("\t#  remove fuel voucher - Istrinti kuro ceki.            #");
		System.out.println("\t#                                                       #");
		System.out.println("\t#########################################################");
		positionsMenu.printMenu(false);
	}
	
	private EmployeesMenu employeesMenu = new EmployeesMenu();
	
	public void allFuelVouchers()
	{
		if (this.countFuelVouchers() <= 0)
		{
			System.out.println("\tKuro cekiu sarasas tuscias. Jei norite prideti rasykite \"add fuel voucher\".");
			return;
		}
		
		System.out.println("\tKuro cekiai:");
		Connection conn = null;
		try
		{
			System.out.printf("\t%3s  %-10s   %-20s   %-14s   %-8s   %-18s   %-12s%n", "Id", "Data", "Darbuotojas", "Kaina uz litra", "Litrai", "Vieta", "Cekio numeris");

			conn = DatabaseConnection.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM fuel_vouchers LEFT JOIN employees ON fuel_vouchers.employee_id = employees.id ORDER BY fuel_vouchers.date");
			while (rs.next())
			{
				System.out.printf("\t%3s  %-10s   %-20s   %-14s   %-8s   %-18s   %-12s%n",
					rs.getString("id"), rs.getString("date"), rs.getString("first_name") + ' ' + rs.getString("last_name"), rs.getDouble("price_per_liter"), rs.getDouble("liters"), rs.getString("place"), rs.getString("voucher_code"));
			}
		}
		catch (SQLException e) { e.printStackTrace(); }
		finally { DatabaseConnection.closeConnection(conn); }
	}
	
	public void addFuelVoucher()
	{
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("\tPrideti kuro ceki:");

		String date = "";
		java.sql.Date date_sql;
		int employee_id = 0;
		double price_per_liter = 0.0;
		double liters = 0.0;
		String place = "";
		String voucher_code = "";
		
		
		System.out.println("\tIveskite data, kada buvo piltas kuras: (Formatas yyyy-MM-dd)");
		try { date = reader.readLine(); } catch (IOException e) { e.printStackTrace(); }
		while ( ! Functions.isValidDate(date))
		{
			System.out.println("\tIvesta data yra neteisinga! Iveskite nauja: (Formatas yyyy-MM-dd)");
			try { date = reader.readLine(); } catch (IOException e) { e.printStackTrace(); }
		}
		date_sql = java.sql.Date.valueOf(date);
		
		this.employeesMenu.allEmployees();
		System.out.println("\tIveskite darbuotojo id, kuris pyle kura.");
		try { employee_id = Integer.parseInt(reader.readLine()); } catch (IOException e) { e.printStackTrace(); }
		while ( ! this.employeesMenu.isValidEmployee(employee_id))
		{
			System.out.println("\tToks darbuotojas nerastas! Meginkite dar karta:");
			try { employee_id = Integer.parseInt(reader.readLine()); } catch (IOException e) { e.printStackTrace(); }
		}
		
		System.out.println("\tIveskite kaina uz litra: (Pvz.: 4.52)");
		try { price_per_liter = Double.parseDouble(reader.readLine()); } catch (IOException e) { e.printStackTrace(); }
		
		System.out.println("\tIveskite kiek litru buvo ipilta: (Pvz.: 37.12)");
		try { liters = Double.parseDouble(reader.readLine()); } catch (IOException e) { e.printStackTrace(); }
	
	
		System.out.println("\tIveskite vieta, kur buvo piltas kuras:");
		try { place = reader.readLine(); } catch (IOException e) { e.printStackTrace(); }
		while (Functions.isEmptyOrBlank(place))
		{
			System.out.println("\tVieta, kur buvo piltas kuras, negali buti tuscia! Iveskite nauja:");
			try { place = reader.readLine(); } catch (IOException e) { e.printStackTrace(); }
		}
		
		System.out.println("\tIveskite kuro cekio koda:");
		try { voucher_code = reader.readLine(); } catch (IOException e) { e.printStackTrace(); }
		while (Functions.isEmptyOrBlank(voucher_code))
		{
			System.out.println("\tKuro cekio kodas negali buti tuscias! Iveskite nauja:");
			try { voucher_code = reader.readLine(); } catch (IOException e) { e.printStackTrace(); }
		}
		Connection conn = null;
		PreparedStatement stmt = null;
		try
		{
			conn = DatabaseConnection.getConnection();
			stmt = conn.prepareStatement("INSERT INTO fuel_vouchers (date, employee_id, price_per_liter, liters, place, voucher_code) values (?, ?, ?, ?, ?, ?)");
			stmt.setDate(1, date_sql);
			stmt.setInt(2, employee_id);
			stmt.setDouble(3, price_per_liter);
			stmt.setDouble(4, liters);
			stmt.setString(5, place);
			stmt.setString(6, voucher_code);
			stmt.executeUpdate();
			
			System.out.println("\tKuro cekis pridetetas.");
			
		}
		catch (SQLException e) { e.printStackTrace(); }
		finally { DatabaseConnection.closeConnection(conn); }
	}
	
	public void removeFuelVoucher()
	{
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("\tIstrinti kuro ceki:");
		
		this.allFuelVouchers();
		
		int fuel_voucher_id = 0;
		System.out.println("\tIveskite kuro cekio id, kuri norite istrinti:");
		try { fuel_voucher_id = Integer.parseInt(reader.readLine()); } catch (IOException e) {  }
		while ( ! this.isValidFuelVoucher(fuel_voucher_id))
		{
			System.out.println("\tToks kuro cekis nerastas! Iveskite nauja:");
			try { fuel_voucher_id = Integer.parseInt(reader.readLine()); } catch (IOException e) {  }
		}
		
		Connection conn = null;
		PreparedStatement stmt = null;
		try
		{
			conn = DatabaseConnection.getConnection();
			stmt = conn.prepareStatement("DELETE FROM fuel_vouchers WHERE id = ?");
			stmt.setInt(1, fuel_voucher_id);
			stmt.executeUpdate();
			
			System.out.println("\tKuro cekis istrintas.");
		}
		catch (org.postgresql.util.PSQLException e) { System.out.println("\tKlaida istrintant kuro ceki. Patikrinkite ar kuro cekis nera naudojamos."); }
		catch (SQLException e) { e.printStackTrace(); }
		finally { DatabaseConnection.closeConnection(conn); }
	}
	
	public boolean isValidFuelVoucher(int fuel_voucher_id)
	{
		Connection conn = null;
		Boolean found = false;
		try
		{
			conn = DatabaseConnection.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM fuel_vouchers WHERE id = '"+fuel_voucher_id+"'");
			while (rs.next()) { found = true; } 
		}
		catch (SQLException e) { e.printStackTrace(); }
		finally { DatabaseConnection.closeConnection(conn); }
		return found;
	}
	
	public int countFuelVouchers()
	{
		Connection conn = null;
		int count = 0;
		try
		{
			conn = DatabaseConnection.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT COUNT(*) AS count FROM fuel_vouchers");
			while (rs.next()) { count = rs.getInt("count"); } 
		}
		catch (SQLException e) { e.printStackTrace(); }
		finally { DatabaseConnection.closeConnection(conn); }
		return count;
	}
}