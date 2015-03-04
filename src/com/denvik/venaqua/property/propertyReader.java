package com.denvik.venaqua.property;

import java.io.*;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

/**
 * Created by DV21 on 23-12-2014.
 */
public abstract class propertyReader implements propertyReaderInterface
{
    private Properties configProps;
    private Properties defaultProps;

    public void setDefaultProps( Map<String,String> defaultVal )
    {
        defaultProps = new Properties();

        Iterator<String> keySet = defaultVal.keySet().iterator();
        while( keySet.hasNext() ){
            String key = keySet.next();
            defaultProps.setProperty( key , defaultVal.get(key) );
        }

        configProps = new Properties( defaultProps );
    }

    public Properties getDefaultProps(){
        return this.defaultProps;
    }

    public Properties getProps(){
        return this.configProps;
    }

    public void loadProps( InputStream inputStream , boolean fromTxtFile ) throws FileNotFoundException,IOException {

        if( fromTxtFile ){
            configProps.load( inputStream );
        }
        else{
            configProps.loadFromXML( inputStream );
        }
        inputStream.close();
    }

    public String getValue( String key ){
        return this.configProps.getProperty( key );
    }

    public String getValueOrDefault( String key , String defaultValue ){
        return this.configProps.getProperty( key , defaultValue );
    }

    public void setProperty( String key , String val ){
        this.configProps.setProperty( key , val );
    }

}
