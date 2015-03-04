package com.denvik.venaqua.property;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * Created by DV21 on 23-12-2014.
 */
public interface propertyReaderInterface {

    /**
     * call setDefaultProps( Map<String,String> ) method to initialize default properties.
     * @param defaultVal
     */
    public abstract void initDefaultProperties(Map<String, String> defaultVal);

    /**
     * Call loadProps( boolean ) method to load configuration from Txt or Xml file.
     * @param fromTxtFile
     */
    public abstract void loadProperties(InputStream inputStream , boolean fromTxtFile) throws FileNotFoundException,IOException;

    /**
     *  Call getValue( String ) method to get desired property value.
     * @param propName  - Property Name
     * @return          - Returns Value of given Property.
     */
    public abstract String getPropertyValue(String propName);

    /**
     *  Call setProperty( String , String ) method to update Property value.
     * @param key - Property Name
     * @param val - Property Value
     */
    public abstract void setPropertyValue(String key, String val);

    /**
     * Call getValueOrDefault( String , String ) method to get desired property value.
     *
     * @param propName     - Property Name
     * @param defaultValue - If given property is not found then defaultValue will be returned.
     * @return - Returns Value of given Property.
     */
    public String getPropertyValueOrDefault(String propName, String defaultValue);
}
