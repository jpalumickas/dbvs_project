package menu;

import java.sql.*;
import java.io.*;

import database.DatabaseConnection;
import main.*;

public class StatisticsMenu
{
	public void printMenu()
	{
		PositionsMenu positionsMenu = new PositionsMenu();
		
		System.out.println("\t################    STATISTIKOS MENIU    ################");
		System.out.println("\t#                                                       #");
		System.out.println("\t#  1. Darbuotojas, kuris ipyle benzino daugiausiai.     #");
		System.out.println("\t#  Naudojimas: statistics id                            #");
		System.out.println("\t#                                                       #");
		System.out.println("\t#########################################################");
	}
	
	public void statistics()
	{
		System.out.println("Noredami perziureti statistika, iveskite numeri prie komandos.");
		System.out.println("Pvz.: \"statistics 1\", visu statistiku sarasas: \"menu statistics\"");	
	}
	
	public void employee_who_filled_fuel_mostly()
	{
		
		System.out.println("\tDarbuotojas, kuris ipyle benzino daugiausiai:");
		Connection conn = null;
		try
		{
			conn = DatabaseConnection.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT fn, ln, MAX(sum) as maxsum FROM (SELECT SUM(fv.liters) as sum, e.id, e.first_name as fn, e.last_name as ln FROM fuel_vouchers fv LEFT JOIN employees e ON (fv.employee_id = e.id) GROUP BY fv.employee_id, e.id) as max");
			while (rs.next())
			{
				System.out.println(" ,kuris ipyle "+rs.getString("maxsum")+" litrus.");
			}
		}
		catch (SQLException e) { e.printStackTrace(); }
		finally { DatabaseConnection.closeConnection(conn); }
	}
	
	
}

