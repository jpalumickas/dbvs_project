package main;

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
	

}