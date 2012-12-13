package main;

import java.util.*;
import java.text.*;

public class Functions
{
	public static boolean isEmptyOrBlank(String str)
	{
		return str == null || str.trim().isEmpty();
	}

	public static boolean isValidSex(char c)
	{
		return (c == "V".charAt(0)) || (c == "M".charAt(0));
	}

	public static boolean isValidDate(String inDate)
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		try
		{
			dateFormat.parse(inDate.trim());
		}
		catch (ParseException pe) { return false; }
		return true;
	}
	
	public static boolean isValidDno(long number)
	{
		if (number < 0) return false;
		
		int digits = 1 + (int)Math.floor(Math.log10(Math.abs(number)));
		if (digits == 11) return true;
		
		return false;
	}
	
	public static Integer[] getIdsFromSting(String str)
	{
		String[] arrayOfStrings = str.replaceAll("\\D+", " ").trim().split(" ");
		Integer[] arrayOfIntegers = new Integer[arrayOfStrings.length];
		for (int i = 0; i < arrayOfStrings.length; i++)
		{
		    arrayOfIntegers[i] = Integer.valueOf(arrayOfStrings[i]);
		}
		return arrayOfIntegers;
	}
	


	static final long ONE_HOUR = 60 * 60 * 1000L;
	public static long calculateDays(String startDate, String endDate)
	{
		try 
		{
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		  Date d1 = (Date)formatter.parse(startDate);
		Date d2 = (Date)formatter.parse(endDate);
		
		return ( (d2.getTime() - d1.getTime() + ONE_HOUR) /
                  (ONE_HOUR * 24));

			 } catch (ParseException e)
			  { return 0;  }  

			 
 	}


	

}