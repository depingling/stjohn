package com.cleanwise.tools;
import org.apache.tools.ant.*;
import org.apache.tools.ant.types.*;

import java.io.*;
import java.sql.*;
import java.text.*;
import java.util.*;


public class DBIndexLoader extends DBIndex {
    private File inFile;
    private HashMap indexes = new HashMap();
    private Set currentIndexes = new HashSet();
    
    
    
    public void setInFile(File f){
        inFile = f;
    }
    

    /**
     *Does the main work.  This is the method called when ant is executed.
     */
    public void execute() throws BuildException {
        Connection conn=null;
        
        log("Starting DB Index Loader...");
        
        
        try {
            AntClassLoader loader = new AntClassLoader(project, classpath, false);
            Class.forName(driver);
        } catch (Exception e) {
            throw new BuildException("ClassNotFoundException: " + e.getMessage(),
            e, location);
        }
        String tabName=null;
        String idName=null;
        try {
            BufferedReader in = new BufferedReader(new FileReader(inFile));
            conn =DriverManager.getConnection(url, userid, password);
            
            // Read file
            log("Reading in definition file");
            String lLine;
            while((lLine = in.readLine()) != null){
                StringTokenizer tok = new StringTokenizer(lLine,",");
                String table = tok.nextToken();
                boolean isUnique = Boolean.getBoolean(tok.nextToken());
                String indexName = tok.nextToken();
                int pos = Integer.parseInt(tok.nextToken());
                String column = tok.nextToken();
                handleNewFileIndex(table, isUnique, indexName, pos, column);
            }
            //read in current indexes
            log("Analyzing Current Indexes");
            ResultSet rs = getIndexResultSet(conn);
            processIndexes(rs);
            rs.close();
            
            //create indexes from file
            conn =DriverManager.getConnection(url, userid, password);
            log("Creating/Re-Creating Indexes");
            createDBIndexes(conn);
            
            //deal with leftover
            Iterator it = currentIndexes.iterator();
            while(it.hasNext()){
                log("Found Exta Index: "+it.next());
            }
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
    
    
    
    /**
     *Handles a new index definition as placed in the file, or generated out of the database meta
     *data.  Will group these together into their own indexes.
     */
    private void handleNewFileIndex(String table, boolean isUnique, String indexName, int pos, String column)
    {
        IndexDef lIndex;
        if(indexes.containsKey(indexName)){
            lIndex = (IndexDef) indexes.get(indexName);
        }else{
            lIndex = new IndexDef(table, isUnique, indexName);
            indexes.put(indexName, lIndex);
        }
        lIndex.addColumn(pos, column);
    }
    
    /**
     *Creates/Re-creates all of the database indexes.
     */
    private void createDBIndexes(Connection conn)throws SQLException{
        Iterator it = indexes.values().iterator();
        while(it.hasNext()){
            IndexDef lIndex= (IndexDef) it.next();
            currentIndexes.remove(lIndex.name);
            log("Creating Index: "+lIndex.name);
            Statement dropStmt = null;
            try{
                dropStmt = conn.createStatement();
                dropStmt.execute(lIndex.dropSQL());
            }catch(SQLException e){
                //index does not exists, continue
            }finally{
                if(dropStmt != null){
                    dropStmt.close();
                }
            }
            Statement createStmt = null;
            try{
                createStmt = conn.createStatement();
                createStmt.execute(lIndex.toSQL());
            }catch(SQLException e){
                if(lIndex.name.endsWith("_PK") || lIndex.name.startsWith("SYS_")){
                    try{
                        //createStmt = conn.createStatement();
                        createStmt.execute(lIndex.rebuildSQL());
                        log("   REBUILT");
                    }catch(SQLException e2){
                        log("FAILED To rebuild primary key: "+lIndex.name);
                    }
                }else{
                    log("FAILED To create index:");
                    log("       "+lIndex.toSQL());
                }
            }finally{
                if(createStmt != null){
                    createStmt.close();
                }
            }
        }
    }
    
    protected void handleNewDBIndex(String table, boolean isUnique, String indexName, int pos, String column) throws IOException {
        currentIndexes.add(indexName);
    }
    
    /**
     *Holds the value of a single index
     */
    private class IndexDef{
        String table;
        boolean isUnique;
        String name;
        ArrayList columns = new ArrayList();
        /**
         *Creates a new index definition with the minimal data.  At some point addColumn
         *must be called or this index will be invalid
         */
        private IndexDef(String pTable,boolean pIsUnique,String pName){
            table = pTable;
            isUnique = pIsUnique;
            name = pName;
        }
        
        /**
         *Adds a column to this index at the specified position.  No validation is done, and it is
         *assumed that all of the positions will be filled in as this class allows null columns.
         */
        private void addColumn(int pos, String pColumn){
            columns.add(pos - 1, pColumn);
        }
        
        /**
         *Generates a drop index statement suitable for SQL execution
         */
        private String dropSQL(){
            StringBuffer sqlBuf = new StringBuffer();
            sqlBuf.append("DROP INDEX ");
            sqlBuf.append(name);
            return sqlBuf.toString();
        }
        
        /**
         *Generates a rebuild index statement suitable for SQL execution
         */
        private String rebuildSQL(){
            StringBuffer sqlBuf = new StringBuffer();
            sqlBuf.append("ALTER INDEX ");
            sqlBuf.append(name);
            sqlBuf.append(" REBUILD");
            return sqlBuf.toString();
        }
        
        /**
         *Generates a create index statement suitable for SQL execution
         */
        private String toSQL(){
            StringBuffer sqlBuf = new StringBuffer();
            sqlBuf.append("CREATE ");
            if(isUnique){
                sqlBuf.append(" UNIQUE ");
            }
            sqlBuf.append("INDEX ");
            sqlBuf.append(name);
            sqlBuf.append(" ON ");
            sqlBuf.append(table);
            sqlBuf.append(" (");
            Iterator it = columns.iterator();
            while(it.hasNext()){
                sqlBuf.append(it.next());
                if(it.hasNext()){
                    sqlBuf.append(",");
                }
            }
            sqlBuf.append(")");
            return sqlBuf.toString();
        }
    }
}

