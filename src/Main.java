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
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		CarsMenu carsMenu = new CarsMenu();
		PositionsMenu positionsMenu = new PositionsMenu();
		EmployeesMenu employeesMenu = new EmployeesMenu();
		SecondmentsMenu secondmentsMenu = new SecondmentsMenu();
		
		BaseMenu.printSmallMenu();

		for (;;)
		{
			String key = reader.readLine();
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
			
			// Cars Menu
			else if (key.equals("cars")) carsMenu.allCars();
			else if (key.equals("add car")) carsMenu.addCar();
			
			// Positions Menu
			else if (key.equals("positions")) positionsMenu.allPositions();
			else if (key.equals("add position")) positionsMenu.addPosition();
			else if (key.equals("remove position")) positionsMenu.removePosition();
			
			// Secondment Types
			else if (key.equals("secondment types")) secondmentsMenu.allSecondmentTypes();
			else if (key.equals("add secondment type")) secondmentsMenu.addSecondmentType();
			else if (key.equals("remove secondment type")) secondmentsMenu.removeSecondmentType();
				
			// Employees Menu
			else if (key.equals("employees")) employeesMenu.allEmployees();
			else if (key.equals("add employee")) employeesMenu.addEmployee();
			
			else System.out.println("Tokia komanda neegzistuoja.");
		}
	}
}
