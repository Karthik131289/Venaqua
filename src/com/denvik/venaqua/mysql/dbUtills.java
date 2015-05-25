package com.denvik.venaqua.mysql;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by DV21 on 06-03-2015.
 */
public class dbUtills {

    private static dbConfig config;
    private static Connection connection;

    public static void initConfig() throws IOException {
        config = new dbConfig();
    }

    public static dbConfig getConfig() {
        return config;
    }

    public static void initDB() throws ClassNotFoundException, SQLException {
        Class.forName( config.getPropertyValue( config._DRIVERCLASS ) );
        connection = DriverManager.getConnection( config.getPropertyValue( config._DB_URL ) , config.getPropertyValue( config._DB_USERNAME ) ,
                            config.getPropertyValue( config._DB_PASSWORD ) );
    }
    public static void closeConnection() throws SQLException {
        connection.close();
    }
    public static Connection getConnection() {
        return connection;
    }
    public static void deleteRecord( String tableName , String fieldName , String conditionString ) throws SQLException {
        Statement statement = null;
        String queryString  = null;

        statement = getConnection().createStatement();
        queryString = "DELETE FROM " + tableName + " WHERE " + fieldName + " " + conditionString + ";";
        statement.execute( queryString );
        statement.close();
    }
    public static void insertRecord( String tableName , String[] fieldNames , Object[] fieldValues ) throws SQLException {
        Statement statement = null;
        String queryString  = null;

        statement = getConnection().createStatement();

        queryString = "INSERT INTO " + tableName + " (";
        for( int i=0; i<fieldNames.length; i++ ) {
            queryString = queryString + fieldNames[i];
            if( i!=(fieldNames.length-1) )
                queryString = queryString + ",";
            else
                queryString = queryString + ") VALUES (";
        }
        for (int i=0;i<fieldValues.length; i++ ) {
            if( fieldValues[i] instanceof String )
                queryString = queryString + "'" + fieldValues[i] + "'";
            else
                queryString = queryString + fieldValues[i];
            if( i!=(fieldValues.length-1) )
                queryString = queryString + ",";
            else
                queryString = queryString + ");";
        }

        statement.execute( queryString );
        statement.close();
    }
}
