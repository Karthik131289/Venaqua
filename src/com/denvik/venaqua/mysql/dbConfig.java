package com.denvik.venaqua.mysql;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import com.denvik.venaqua.property.propertyReader;

/**
 * Created by DV21 on 06-03-2015.
 */
public class dbConfig extends propertyReader {
    private static final InputStream INPUT_STREAM = dbConfig.class.getResourceAsStream( "dbConfig.properties" );

    public static final String _DRIVERCLASS = "driverClass";
    public static final String _DB_URL = "url";
    public static final String _DB_USERNAME = "userName";
    public static final String _DB_PASSWORD = "password";

    private Map< String , String > defaultProps = new HashMap<String, String>();

    public dbConfig() throws FileNotFoundException , IOException {
        this.initDefaultProperties( this.defaultProps );
        this.loadProperties( INPUT_STREAM , true );
    }

    @Override
    public void initDefaultProperties(Map<String, String> defaultVal) {
        defaultVal.put(_DRIVERCLASS, "com.mysql.jdbc.Driver" );
        defaultVal.put( _DB_URL , "jdbc:mysql://localhost/venaqua" );
        defaultVal.put( _DB_USERNAME , "denvik" );
        defaultVal.put( _DB_PASSWORD , "denvik" );
        super.setDefaultProps( defaultVal );
    }

    @Override
    public void loadProperties( InputStream inputStream , boolean fromTxtFile) throws FileNotFoundException, IOException {
        super.loadProps( inputStream , fromTxtFile );
    }

    @Override
    public String getPropertyValue(String propName) {
        return super.getValue( propName );
    }

    @Override
    public void setPropertyValue(String key, String val) {
        super.setProperty( key , val );
    }

    @Override
    public String getPropertyValueOrDefault(String propName, String defaultValue) {
        return super.getValueOrDefault( propName , defaultValue );
    }
}
