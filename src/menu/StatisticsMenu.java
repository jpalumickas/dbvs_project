package menu;

import java.sql.*;
import java.io.*;
import java.text.*;

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
			DecimalFormat df = new DecimalFormat("#.#");
			conn = DatabaseConnection.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT fv.employee_id, sum(fv.liters), e.first_name, e.last_name FROM fuel_vouchers fv LEFT JOIN employees e ON fv.employee_id = e.id GROUP BY fv.employee_id, e.first_name, e.last_name ORDER BY 2 DESC LIMIT 1");
			while (rs.next())
			{
				System.out.println("\t"+rs.getString("first_name")+" "+rs.getString("last_name")+", kuris ipyle "+df.format(rs.getDouble("sum"))+" litrus.");
			}
		}
		catch (SQLException e) { e.printStackTrace(); }
		finally { DatabaseConnection.closeConnection(conn); }
	}
	
	
}

