package com.cleanwise.tools;
import org.apache.tools.ant.*;
import org.apache.tools.ant.types.*;

import java.io.*;
import java.sql.*;
import java.text.*;
import java.util.*;


public class DBIndexReader extends DBIndex {
    private File outFile;
    private FileWriter out;
    
    public void setOutFile(File f){
        outFile = f;
    }
    
    
    
    
    
    public void execute() throws BuildException {
        Connection conn=null;
        DatabaseMetaData meta;
        
        log("Starting DB Index Reader...");
        
        
        try {
            AntClassLoader loader = new AntClassLoader(project, classpath, false);
            Class.forName(driver);
        } catch (Exception e) {
            throw new BuildException("ClassNotFoundException: " + e.getMessage(),
            e, location);
        }
        
        try {
            out = new FileWriter(outFile);
            conn =DriverManager.getConnection(url, userid, password);
            
            
            log("Analyzing Indexes");
            ResultSet rs = getIndexResultSet(conn);
            processIndexes(rs);
            rs.close();
            
            out.flush();
            out.close();
        } catch (SQLException e) {
            throw new BuildException(e.getMessage());
        } catch (IOException e) {
            throw new BuildException(e.getMessage());
        }finally{
            try{
                conn.close();
            }catch(Exception e){e.printStackTrace();}
        }
    }
    
    protected void handleNewDBIndex(String table, boolean isUnique, String indexName, int pos, String column)
    throws IOException{
        if(indexName != null){
            out.write(table);
            out.write(',');
            out.write(Boolean.toString(isUnique));
            out.write(',');
            out.write(indexName);
            out.write(',');
            out.write(Integer.toString(pos));
            out.write(',');
            out.write(column);
            out.write('\n');
            out.flush();
        }
    }
    
    
    /*private class IndexDef{
        new IndexDef(){}
    }*/
}

