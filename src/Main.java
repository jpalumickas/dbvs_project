import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import database.*;
import menu.*;

public class Main
{
	public static void main(String[] args) throws IOException
	{
		// Command line buffer
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		// Menus
		CarsMenu carsMenu = new CarsMenu();
		PositionsMenu positionsMenu = new PositionsMenu();
		EmployeesMenu employeesMenu = new EmployeesMenu();
		SecondmentsMenu secondmentsMenu = new SecondmentsMenu();
		FuelVouchersMenu fuelvouchersMenu = new FuelVouchersMenu();
		StatisticsMenu statisticsMenu = new StatisticsMenu();
		
		// Print menu for the first time.
		BaseMenu.printSmallMenu();

		for (;;)
		{
			String key = reader.readLine();
			
			// Quit program
			if (key.equals("quit"))
			{
				System.out.println("\tPrograma isjungiama...");
				break;
			}
			
			// Base Menu
			else if (key.equals("menu")) BaseMenu.printMenu(); 
			else if (key.equals("menu cars")) carsMenu.printMenu();
			else if (key.equals("menu employees")) employeesMenu.printMenu();
			else if (key.equals("menu positions")) positionsMenu.printMenu();
			else if (key.equals("menu secondments")) secondmentsMenu.printMenu();
			else if (key.equals("menu statistics")) statisticsMenu.printMenu();
			
			// Cars Menu
			else if (key.equals("cars")) carsMenu.allCars();
			else if (key.equals("add car")) carsMenu.addCar();
			else if (key.equals("remove car")) carsMenu.removeCar();
			else if (key.equals("car employees")) carsMenu.allCarEmployees();
			else if (key.equals("add car employees")) carsMenu.addCarEmployees();
			else if (key.equals("remove car employees")) carsMenu.removeCarEmployees();
			
			// Positions Menu
			else if (key.equals("positions")) positionsMenu.allPositions();
			else if (key.equals("add position")) positionsMenu.addPosition();
			else if (key.equals("remove position")) positionsMenu.removePosition();
			
			// Secondment Types
			else if (key.equals("secondments")) secondmentsMenu.allSecondments();
			else if (key.equals("secondments between dates")) secondmentsMenu.allSecondmentsBetweenDates();
			else if (key.equals("add secondment")) secondmentsMenu.addSecondment();
			else if (key.equals("remove secondment")) secondmentsMenu.removeSecondment();
			else if (key.equals("secondment types")) secondmentsMenu.allSecondmentTypes();
			else if (key.equals("add secondment type")) secondmentsMenu.addSecondmentType();
			else if (key.equals("remove secondment type")) secondmentsMenu.removeSecondmentType();
				
			// Employees Menu
			else if (key.equals("employees")) employeesMenu.allEmployees();
			else if (key.equals("add employee")) employeesMenu.addEmployee();
			else if (key.equals("remove employee")) employeesMenu.removeEmployee();
			
			// Fuel Vouchers Menu
			else if (key.equals("fuel vouchers")) fuelvouchersMenu.allFuelVouchers();
			else if (key.equals("add fuel voucher")) fuelvouchersMenu.addFuelVoucher();
			else if (key.equals("remove fuel voucher")) fuelvouchersMenu.removeFuelVoucher();
			else if (key.equals("employee fuel vouchers")) fuelvouchersMenu.employeeFuelVouchers();
			
			// Statistics Menu
			else if (key.equals("statistics")) statisticsMenu.statistics();
			else if (key.equals("statistics 1")) statisticsMenu.employee_who_filled_fuel_mostly();
			
			// Command not found.
			else System.out.println("Tokia komanda neegzistuoja.");
		}
	}
}
