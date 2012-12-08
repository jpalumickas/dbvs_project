import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import database.*;
import menu.*;

public class Main {
	public static void main(String[] args) throws IOException
	{
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		CarsMenu carsMenu = new CarsMenu();
		BaseMenu.printMenu();
		for (;;)
		{
			String key = reader.readLine();
			if (key.equals("quit"))
			{
				System.out.println("Programa isjungiama.");
				break;
			}
			else if (key.equals("menu")) BaseMenu.printMenu(); 
			else if (key.equals("menu cars")) carsMenu.printMenu();
			else if (key.equals("cars")) carsMenu.allCars();
			else if (key.equals("add car")) carsMenu.addCar();

		}
	}
}
