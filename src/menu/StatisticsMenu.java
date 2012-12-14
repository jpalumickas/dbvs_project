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
		System.out.println("\t#  1. Zmogus, kuris ipyle benzino daugiausiai.          #");
		System.out.println("\t#  Naudojimas: statistics id                            #");
		System.out.println("\t#                                                       #");
		System.out.println("\t#########################################################");
	}
	
	public void statistics()
	{
		System.out.println("Noredami perziureti statistika, iveskite numeri prie komandos.");
		System.out.println("Pvz.: \"statistics 1\", visu statistiku sarasas: \"menu statistics\"");	
	}
}

