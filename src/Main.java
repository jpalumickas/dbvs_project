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
		
		BaseMenu.printSmallMenu();

		for (;;)
		{
			String key = reader.readLine();
			if (key.equals("quit"))
			{
				System.out.println("Programa isjungiama.");
				break;
			}
			// Base Menu
			else if (key.equals("menu")) BaseMenu.printMenu(); 
			else if (key.equals("menu cars")) carsMenu.printMenu();
			else if (key.equals("menu positions")) positionsMenu.printMenu();
			
			// Cars Menu
			else if (key.equals("cars")) carsMenu.allCars();
			else if (key.equals("add car")) carsMenu.addCar();
			
			// Positions Menu
			else if (key.equals("positions")) positionsMenu.allPositions();
			else if (key.equals("add position")) positionsMenu.addPosition();
			else if (key.equals("remove position")) positionsMenu.removePosition();
			
			// Employees Menu
			else if (key.equals("employees")) employeesMenu.allEmployees();
		}
	}
}
