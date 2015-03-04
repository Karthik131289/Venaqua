package com.denvik.venaqua.csv.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public final class FileUtils
{

	public static File getFile( String path , String extension )
	{
		File f = new File( path );
		try
		{
			if( !f.exists() )
				throw new FileNotFoundException();
			else
			{
				if( f.isDirectory() )
					throw new IOException( path + " is not a valid .csv file..." );
				else
					if( !f.getName().endsWith( extension ) )
						throw new IOException( path + " is not a valid ."+ extension +" file..." );
			}
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
		return f ;
	}

	public static void writeData(String data)
	{
		// TODO Auto-generated method stub
		
	}

	public static StringBuffer readFile( File f ) 
	{
		StringBuffer buf = new StringBuffer();
		BufferedReader reader ;
		FileReader fr;
		String line;
		try
		{
			if( f.exists() )
			{
				fr = new FileReader( f );
				reader = new BufferedReader( fr );
				
				while( ( line = reader.readLine() ) != null )
				{
					buf.append( line );
					buf.append( "\n" );
				}
				
				fr.close();
				reader.close();
			}
			else
				throw new FileNotFoundException();
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
		
		return buf;
	}
	
}
