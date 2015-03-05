package com.denvik.venaqua.mysql;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class dbUtills 
{
	private static Connection connection;
	private static dbConfig config;
	
	public static void initDBConnection() 
	{
		try 
		{
			config = new dbConfig();
			Class.forName( config.getPropertyValue( config._DRIVER_CLASS ) );
			connection = DriverManager.getConnection( config.getPropertyValue( config._DB_URL ) , config.getPropertyValue( config._DB_USERNAME ) , config.getPropertyValue( config._DB_PASSWORD ) );
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		} 
		catch (ClassNotFoundException e) 
		{
			e.printStackTrace();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
	}
	
	public static Connection getConnection()
	{
		return connection;
	}

	public static dbConfig getConfig()
	{
		return config;
	}
}
