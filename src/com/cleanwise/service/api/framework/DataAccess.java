package com.cleanwise.service.api.framework;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 * Interface for the dataAccess objects
 * @author  bstevens
 */
public interface DataAccess {
    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@param int the offset to use which is useful when using 1 query to populate multiple objects
    *@returns a populated <xsl:value-of select="@javaname"/>Data Object.
    *@throws SQLException
    */
    public ValueObject parseResultSet(ResultSet rs,int offset) throws SQLException;
    
    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a <xsl:value-of select="@javaname"/>Data Object
    *using the parseResultSet method.
    */
    public String getSelectColumns();
    
    /**
    *@Returns a count of the number of columns the <xsl:value-of select="@javaname"/>Data Object represents.
    */
    public int getColumnCount();
}
