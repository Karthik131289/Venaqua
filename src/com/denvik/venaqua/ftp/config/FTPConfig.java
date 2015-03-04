package com.denvik.venaqua.ftp.config;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import com.denvik.venaqua.property.propertyReader;

/**
 * Created by DV21 on 04-03-2015.
 */
public class FTPConfig extends propertyReader {
	
    private static final InputStream INPUT_STREAM = FTPConfig.class.getResourceAsStream( "ftpConfig.properties" );

    public static final String _HOST = "hostAddress";
    public static final String _PORT = "port";
    public static final String _USERNAME = "userName";
    public static final String _PASSWORD = "password";
    public static final String _ROOT_DIR_PATH = "rootDirPath";

    private Map< String , String > defaultProps = new HashMap<String, String>();

    public FTPConfig() throws FileNotFoundException , IOException {
        this.initDefaultProperties( this.defaultProps );
        this.loadProperties( INPUT_STREAM , true );
    }

    @Override
    public void initDefaultProperties(Map<String, String> defaultVal) {
        defaultVal.put( _HOST , "::1" );
        defaultVal.put( _PORT , "21" );
        defaultVal.put( _USERNAME , "karthik" );
        defaultVal.put( _PASSWORD , "karthik" );
        defaultVal.put( _ROOT_DIR_PATH , "ytp" );
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
