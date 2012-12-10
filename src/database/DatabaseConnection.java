package database;
import java.io.IOException;
import java.sql.*;
 
public class DatabaseConnection
{	
	public static Connection getConnection()
	{
		Connection conn = null;
		try
		{
			try {
				Class.forName("org.postgresql.Driver");

			} catch (ClassNotFoundException e) {
				System.out.println("Nerastas PostgreSQL JDBC.");
				e.printStackTrace();
				return null;
			}
			conn = DriverManager.getConnection("jdbc:postgresql://"+DatabaseConfig.SERVER+"/"+DatabaseConfig.DATABASE+"", DatabaseConfig.USERNAME, DatabaseConfig.PASSWORD);
		} catch (SQLException e)
		{
			e.printStackTrace();
		} 
		return conn;
	}
	
	public static void closeConnection(Connection connection)
	{
		if (connection != null) {
            try {
            	connection.close();
            } catch (SQLException e) {
				e.printStackTrace();
			}
        }
	}
}