package com.cleanwise.tools;
import org.apache.tools.ant.*;
import org.apache.tools.ant.types.*;

import java.io.*;
import java.sql.*;
import java.text.*;
import java.util.*;

public abstract class DBIndex extends Task {
    protected String driver;
    protected String url;
    protected String userid;
    protected String password;
    protected Path classpath;
    
    
    public void setDriver(String driver) {
        this.driver = driver;
    }
    
    public void setUrl(String url) {
        this.url = url;
    }
    
    public void setUserid(String userid) {
        this.userid = userid.toUpperCase();
    }
    
    public void setPassword(String password) {
        this.password = password.toUpperCase();
    }
    
    public void setClasspath(Path classpath) {
        if (this.classpath == null) {
            this.classpath = classpath;
        } else {
            this.classpath.append(classpath);
        }
    }
    
    public Path createClasspath() {
        if (this.classpath == null) {
            this.classpath = new Path(project);
        }
        return this.classpath.createPath();
    }
    
    public void setClasspathRef(Reference r) {
        createClasspath().setRefid(r);
    }
    
    /**
     *Returns a result set of the all the appropriate indexes in the configured schema/database
     */
    protected ResultSet getIndexResultSet(Connection conn)throws SQLException{
        
        String sql =
        "SELECT "+
        "ai.table_name, DECODE(ai.uniqueness,'UNIQUE',0,1) AS NON_UNIQUE, "+
        "ai.index_name, aic.column_position AS ORDINAL_POSITION, aic.column_name "+
        "FROM  "+
        "all_indexes ai,ALL_IND_COLUMNS aic "+
        "WHERE "+
        "ai.owner = '"+userid+"' AND aic.index_owner = '"+userid+"' "+
        "AND aic.index_name = ai.index_name AND ai.status='VALID' AND "+
        "ai.table_name like 'CLW%' "+
        "ORDER BY ai.table_name,ai.index_name, aic.column_position";
        
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        return rs;
    }
    
    /**
     *Processes all indexes for the specified table
     */
    protected void processIndexes(ResultSet rs)
    throws SQLException,IOException{
        while (rs.next()) {
            handleNewDBIndex(
            rs.getString("TABLE_NAME"),
            !rs.getBoolean("NON_UNIQUE"),
            rs.getString("INDEX_NAME"),
            rs.getInt("ORDINAL_POSITION"),
            rs.getString("COLUMN_NAME"));
        }
        rs.close();
        
        return;
    }
    
    /**
     *Handle finding of new database index
     */
    abstract protected void handleNewDBIndex(String table, boolean isUnique, String indexName, int pos, String column)
    throws IOException;
    
}
