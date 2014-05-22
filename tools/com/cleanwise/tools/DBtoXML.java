package com.cleanwise.tools;

import org.apache.tools.ant.*;
import org.apache.tools.ant.types.*;

import java.io.*;
import java.sql.*;
import java.text.*;
import java.util.*;

public class DBtoXML extends Task {
    private String driver;
    private String url;
    private String userid;
    private String password;
    private String prefix;
    private String dbObjectType;
    private File outfile;
    private Path classpath;
    private PrintStream out;
    private HashSet pk;
    private HashSet fk;
    private String classVersionUID;

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

    public void setPrefix(String prefix) {
        this.prefix = prefix.toUpperCase();
    }

    public void setDbObjectType(String dbObjectType) {
        this.dbObjectType = dbObjectType.toUpperCase();
    }

    public void setOutfile(File outfile) {
        this.outfile = outfile;
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

    public void setClassVersionUID(String pVar) {
        classVersionUID=pVar;
    }

    public void execute() throws BuildException {
        Connection conn;
        DatabaseMetaData meta;
        Properties idProps = new Properties();
        try {
            FileInputStream idPropsIS = new FileInputStream(classVersionUID);
            idProps.load(idPropsIS);
        } catch(java.io.FileNotFoundException exc) {
            throw new BuildException(exc.getMessage());
        } catch(java.io.IOException exc){
            throw new BuildException(exc.getMessage());
        }

        log("Generating XML representation of schema to " + outfile);

        try {
            if (outfile != null) {
                out = new PrintStream(new BufferedOutputStream(new FileOutputStream(outfile)));
            } else {
                out = System.out;
            }

            try {
                AntClassLoader loader = new AntClassLoader(project, classpath, false);
                Class.forName(driver);
            } catch (Exception e) {
                throw new BuildException("ClassNotFoundException: " + e.getMessage(),
                                         e, location);
            }

            try {
                conn =
                    DriverManager.getConnection(url, userid, password);

                meta = conn.getMetaData();

                out.print("<?xml version =\"1.0\"?>\n");
                out.print("<database name=\"db\" >\n");

                //indexes
                ////////////
String  indexSql =
     "SELECT i.TABLE_OWNER,i.table_name, i.INDEX_NAME,UNIQUENESS, ic.column_name, COLUMN_POSITION, ic.DESCEND "+
    " FROM all_indexes i join all_ind_columns ic  "+
    " ON i.index_name = ic.index_name "+
    " AND i.table_owner = ic.table_owner "+
    " WHERE i.table_name LIKE 'CLW%' AND owner = 'STJOHN_STAGE' "+
    " ORDER BY i.table_name, i.index_name, ic.column_position ";
System.err.println("AAAAAAAAAAAAAAAAAAA: "+indexSql);
                PreparedStatement stmt = conn.prepareStatement(indexSql);
                ResultSet indexRS=stmt.executeQuery();
                HashMap indexHM = new HashMap();
                HashMap tabIndexHM = new HashMap();
                ArrayList indColumnAL = new ArrayList();
                String prevTabName = "";
                String prevIndexName = "";

                while (indexRS.next()) {
                    String indexName = indexRS.getString("INDEX_NAME");
                    if(indexName==null) {
                        continue;
                    }
                    Index index = new Index();
                    index.indexName = indexName;
                    index.num = indexRS.getShort("COLUMN_POSITION");
                    index.tableName = indexRS.getString("TABLE_NAME");
                    index.columnName = indexRS.getString("COLUMN_NAME");
                    index.ascdesc = indexRS.getString("DESCEND");
                    index.unique = indexRS.getString("UNIQUENESS");
                    if(!prevTabName.equals(index.tableName)){
                        prevTabName = index.tableName;
                        tabIndexHM = new HashMap();
                        indexHM.put(index.tableName, tabIndexHM);
                    }
                    if(!prevIndexName.equals(indexName)) {
                        prevIndexName = indexName;
                        indColumnAL = new ArrayList();
                        tabIndexHM.put(indexName,indColumnAL);
                    }
                    indColumnAL.add(index);
                }
                indexRS.close();
                stmt.close();
                /////////////

                // Only get the tables.
                String typesReq[] = { dbObjectType };
                ResultSet tables = meta.getTables(null,userid,prefix+"_%", typesReq);
                while (tables.next()) {

                    String tabName = tables.getString("TABLE_NAME");

System.err.println("AAAAAAAAAAAAAAAAAAA table: "+tabName);
                    String javaName = getJavaName(tabName);
                    String fullDataJavaName = "com.cleanwise.service.api.value."+javaName+"Data";
                    String versionDataUID = idProps.getProperty(fullDataJavaName);
                    if(versionDataUID==null || versionDataUID.trim().length()==0) {
                        throw new BuildException("No version UID for java class: " + fullDataJavaName);
                    }
                    String fullVectorJavaName = "com.cleanwise.service.api.value."+javaName+"DataVector";
                    String versionVectorUID = idProps.getProperty(fullVectorJavaName);
                    if(versionVectorUID==null || versionVectorUID.trim().length()==0) {
                        throw new BuildException("No version UID for java class: " + fullVectorJavaName);
                    }
                    out.print("   <table name=\"" + tabName + "\" javaname=\"" +
                              javaName + "\" type=\"TABLE\" >\n");
                    out.print("      <serialVersionDataUID><value>"+versionDataUID+"</value></serialVersionDataUID>\n");
                    out.print("      <serialVersionVectorUID><value>"+versionVectorUID+"</value></serialVersionVectorUID>\n");
                    pk = new HashSet();
                    boolean gotpk = false;
                    ResultSet keys =
                        meta.getPrimaryKeys(null, userid, tabName);
                    while (keys.next()) {
                        pk.add(keys.getString("COLUMN_NAME"));
                        gotpk = true;
                        out.print("<!-- " +
                                  " Primary key=" + keys.getString("COLUMN_NAME") +
                                  " -->\n");
                    }
                    keys.close();
                    if ( gotpk == false ) {
                        out.print("<!-- No primary key defined for table: " +
                                  tabName + " -->\n");
                    }


                    fk = new HashSet();
                    ResultSet fkeys =
                        meta.getImportedKeys(null, userid, tabName);
                    while (fkeys.next()) {
                        fk.add(fkeys.getString("FKCOLUMN_NAME"));
                    }
                    fkeys.close();

                    ResultSet columns =
                        meta.getColumns(null, userid, tabName, "%");
                    ArrayList sortedColumns = new ArrayList();
                    while (columns.next()) {

                        ColHolder thiscol = new ColHolder(columns);
                        sortedColumns.add(thiscol);
                    }
                    /*
                    Collections.sort(sortedColumns, COL_NAME_COMPARE);
                     */
                    for ( int idx = 0; idx < sortedColumns.size(); idx++ )
                    {
                        doColumn((ColHolder)sortedColumns.get(idx));
                    }

                    columns.close();

                    ResultSet fkeysRS =
                        meta.getImportedKeys(null, userid, tabName);
                    String prevFkName = "";
                    while (fkeysRS.next()) {
                        Fkey fk = new Fkey();
        fk.pkTableName = fkeysRS.getString("PKTABLE_NAME"); // String => primary key table name being imported
        fk.pkColumnName = fkeysRS.getString("PKCOLUMN_NAME"); // String => primary key column name being imported
        fk.fkTableName = fkeysRS.getString("FKTABLE_NAME"); // String => foreign key table name
        fk.fkColumnName = fkeysRS.getString("FKCOLUMN_NAME"); // String => foreign key column name
        fk.keySeq = fkeysRS.getShort("KEY_SEQ"); // short => sequence number within a foreign key
        fk.updateRule = fkeysRS.getShort("UPDATE_RULE"); // short => What happens to a foreign key when the primary key is updated:
        fk.deleteRule = fkeysRS.getShort("DELETE_RULE"); // short => What happens to the foreign key when primary is deleted.
        fk.fkName = fkeysRS.getString("FK_NAME"); // String => foreign key name (may be null)
        fk.pkName = fkeysRS.getString("PK_NAME"); // String => primary key name (may be null)
                       if(!prevFkName.equals(fk.fkName)) {
                           if(prevFkName.length()!=0) {
                               out.print("    </fkey>\n");
                           }
                           prevFkName = fk.fkName;
                           out.print("    <fkey name=\""+fk.fkName+"\"" +
                               " pkTableName =\""+fk.pkTableName+"\"" +
                               " keySeq =\""+fk.keySeq+"\"" +
                               " updateRule =\""+fk.updateRule+"\"" +
                               " deleteRule =\""+fk.deleteRule+"\"" +
                               " pkName =\""+fk.pkName+"\"" +
                                ">\n");
                       }
                       out.print("         <fkeyref "+
                           " fkColumnName =\""+fk.fkColumnName+"\"" +
                           " pkColumnName =\""+fk.pkColumnName+"\"" +
                            "/>\n");
                    }
                    if(prevFkName.length()!=0) {
                       out.print("    </fkey>\n");
                    }
                    fkeysRS.close();


                    //INDEXES
                    HashMap tIndexHM = (HashMap) indexHM.get(tabName);
                    if(tIndexHM!=null) {
                        Set entrySet = tIndexHM.entrySet();
                        for(Iterator iter=entrySet.iterator(); iter.hasNext();) {
                            Map.Entry me = (Map.Entry) iter.next();
                            String indexName = (String) me.getKey();
                            ArrayList indColumns = (ArrayList) me.getValue();
                            boolean firstFl = true;
                            for(Iterator iter1=indColumns.iterator(); iter1.hasNext();) {
                               Index index = (Index) iter1.next();
                               if(firstFl) {
                                   firstFl = false;
                                   out.print("    <index name=\""+indexName+"\"" +
                                           " unique=\""+index.unique+"\"" +
                                    ">\n");
                               }
                               out.print("       <indcolumn name=\""+index.columnName+"\" " +
                                " ascdesc=\""+index.ascdesc+"\"" +
                                " number=\""+index.num+"\"" +
                                "/>\n");
                            }
                            out.print("   </index>\n");
                        }
                    }
                    out.print("   </table>\n");
                }

            } catch (SQLException e) {
                throw new BuildException("SQLException: " + e.getMessage(),
                                         e, location);
            }

            out.print("</database>\n");

        } catch (IOException ioe) {
            throw new BuildException("Error writing " + outfile.getAbsolutePath(),
                                     ioe, location);
        }
        finally {
            if (out != null && out != System.out) {
                out.close();
            }
        }
    }

    // Class to hold the column information.  This class
    // is usefull in order to resort the column names so
    // that all the appropriate classes get regenerated
    // the same way, regardless of which database is used.
    class ColHolder {

        public ColHolder(ResultSet column)
        {
            try {
                colName = column.getString("COLUMN_NAME");
                colSize = column.getString("COLUMN_SIZE");
                dataType = column.getString("DATA_TYPE");
                typeName = column.getString("TYPE_NAME");
                digits = column.getInt("DECIMAL_DIGITS");
                size = column.getInt("COLUMN_SIZE");
                nullable = column.getInt("NULLABLE");
                sqlDataType = column.getString("SQL_DATA_TYPE");

                /*
                  System.out.println("typeName: "+typeName+" size:
                  "+size+" digits: "+digits+ " datatype: "+dataType+"
                  colSize: "+colSize+" sqlDataType: "+sqlDataType);

                  System.out.println("........ Column: "+colName);
                */
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        // Column attributes.
        String colName,
            colSize,
            dataType,
            typeName,
            sqlDataType;

        int digits,
            size,
            nullable;
    }

    static final Comparator COL_NAME_COMPARE = new Comparator() {
            public int compare(Object o1, Object o2)
            {
                String name1 = ((ColHolder)o1).colName;
                String name2 = ((ColHolder)o2).colName;

                if ( name1.indexOf("_ID") > 0 && name2.indexOf("_ID") > 0 ) {
                    return name1.compareTo(name2);
                }

                // Ensure that the ID columns go to the begining.
                // This is needed for proper update clause generation.
                if ( name1.indexOf("_ID") > 0 ) {
                    return -1;
                }
                if ( name2.indexOf("_ID") > 0 ) {
                    return 1;
                }

                return name1.compareTo(name2);
            }
        };

    private boolean isPK(String colName)
    {
        return pk.contains(colName);
    }

    private boolean isFK(String colName)
    {
        return fk.contains(colName);
    }

    private void doColumn(ColHolder column)
    {
        try {
            String colName = column.colName;
            String colSize = column.colSize;
            String typeName = column.typeName;
            int digits = column.digits;
            int size = column.size;
            int nullable = column.nullable;
            String sqlDataType = column.sqlDataType;
            /*
              System.out.println("typeName: "+typeName+" size: "
              +size+" digits: "+digits+
              " datatype: "+dataType+" colSize: "+colSize
              +" sqlDataType: "+sqlDataType);

              System.out.println("........ Column: "+colName);
            */

            out.print("      <column nullable=\"" + (nullable == DatabaseMetaData.columnNullable) + "\" " +
                    "size=\"" + size + "\" " +
                    "digits=\"" + digits + "\" " +
                    "primary_key=\"" + isPK(colName) + "\" " +
                    "foreign_key=\"" + isFK(colName) + "\" " +
                    ">\n");
            out.print("         <javatype>"
                      + getJavaType(typeName,size,digits) + "</javatype>\n");
            out.print("         <javaname>"
                      + getJavaName(colName) + "</javaname>\n");
            out.print("         <rsgetter>"
                      + getRsgetter(colName,typeName,size,digits) + "</rsgetter>\n");
            out.print("         <name>" + colName + "</name>\n");
            out.print("         <type>" + typeName + "</type>\n");
            out.print("      </column>\n");
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }

    private String getJavaName (String name)
    {
        String s;
        if (name.length() > 4 && name.substring(0,4).equals(prefix+"_")) {
            s = name.substring(4);
        } else {
            s = name;
        }

        String newname = "";
        StringCharacterIterator i = new StringCharacterIterator(s);
        char c = i.first();
        boolean upflag = true;
        while (c != CharacterIterator.DONE) {
            if (c == '_' || c==' ' || c=='/') {
                upflag = true;
            } else {
                if (upflag) {
                    newname += Character.toUpperCase(c);
                } else {
                    newname += Character.toLowerCase(c);
                }
                upflag = false;
            }
            c = i.next();
        }
        return newname;
    }

    private String getJavaType (String datatype, int size, int digits)
    {
        if (datatype.equals("VARCHAR2")) {
            return "String";
        } else if (datatype.equals("NUMBER") && size == 1 && digits == 0 && !prefix.equalsIgnoreCase("CLV")) {
            return "boolean";
        } else if (datatype.equals("NUMBER") && digits == 0 && size!=0 && !prefix.equalsIgnoreCase("CLV")) {
            return "int";
        } else if (datatype.equals("NUMBER")) {
            return "java.math.BigDecimal";
        } else if (datatype.equals("DATE")) {
            return "Date";
        } else if (datatype.equals("CHAR")) {
            return "String";
        } else if (datatype.equals("LONG")) {
            return "String";
        } else if (datatype.equals("CLOB")) {
            return "String";
        } else if (datatype.equals("BLOB")) {
            return "byte[]";
        } else {
            return "XXX";
        }
    }

    private String getRsgetter (String colName, String datatype, int size, int digits)
    {
        if (datatype.equals("VARCHAR2")) {
            return "String";
        } else if (datatype.equals("NUMBER") && size == 1 && digits == 0 && !prefix.equalsIgnoreCase("CLV")) {
            return "Boolean";
        } else if (datatype.equals("NUMBER") && digits == 0 && !prefix.equalsIgnoreCase("CLV")) {
            return "Int";
        } else if (datatype.equals("NUMBER")) {
            return "BigDecimal";
        } else if (datatype.equals("DATE")) {
            int l = colName.length();
            if (l > 5 && colName.substring((l-5),l).equals("_TIME")) {
                return "Time";
            } else {
                if(colName.equals("MOD_DATE") ||colName.equals("ADD_DATE") || colName.endsWith("_DT") ) {
                    return "Timestamp";
                } else {
                    return "Date";
                }
            }
        } else if (datatype.equals("TIME")) {
            return "Timestamp";
        } else if (datatype.equals("CHAR")) {
            return "String";
        } else if (datatype.equals("LONG")) {
            return "String";
        } else if (datatype.equals("CLOB")) {
            return "String";
        } else if (datatype.equals("BLOB")) {
            return "Bytes";
        } else {
            return "XXX";
        }
    }
    public class Index {
        String tableName;
        String indexName;
        String columnName;
        String ascdesc;
        String type;
        String unique;
        short num;
    }
  public class Fkey {
        String pkTableName; // PKTABLE_NAME String => primary key table name being imported
        String pkColumnName; // PKCOLUMN_NAME String => primary key column name being imported
        String fkTableName; // FKTABLE_NAME String => foreign key table name
        String fkColumnName; // FKCOLUMN_NAME String => foreign key column name
        short keySeq; // KEY_SEQ short => sequence number within a foreign key
        short updateRule; // UPDATE_RULE short => What happens to a foreign key when the primary key is updated:
        short deleteRule; // DELETE_RULE short => What happens to the foreign key when primary is deleted.
        String fkName; // FK_NAME String => foreign key name (may be null)
        String pkName; //PK_NAME String => primary key name (may be null)
    }

    /*
    Each primary key column description has the following columns:

PKTABLE_CAT String => primary key table catalog being imported (may be null)
PKTABLE_SCHEM String => primary key table schema being imported (may be null)
PKTABLE_NAME String => primary key table name being imported
PKCOLUMN_NAME String => primary key column name being imported
FKTABLE_CAT String => foreign key table catalog (may be null)
FKTABLE_SCHEM String => foreign key table schema (may be null)
FKTABLE_NAME String => foreign key table name
FKCOLUMN_NAME String => foreign key column name
KEY_SEQ short => sequence number within a foreign key
UPDATE_RULE short => What happens to a foreign key when the primary key is updated:
importedNoAction - do not allow update of primary key if it has been imported
importedKeyCascade - change imported key to agree with primary key update
importedKeySetNull - change imported key to NULL if its primary key has been updated
importedKeySetDefault - change imported key to default values if its primary key has been updated
importedKeyRestrict - same as importedKeyNoAction (for ODBC 2.x compatibility)
DELETE_RULE short => What happens to the foreign key when primary is deleted.
importedKeyNoAction - do not allow delete of primary key if it has been imported
importedKeyCascade - delete rows that import a deleted key
importedKeySetNull - change imported key to NULL if its primary key has been deleted
importedKeyRestrict - same as importedKeyNoAction (for ODBC 2.x compatibility)
importedKeySetDefault - change imported key to default if its primary key has been deleted
FK_NAME String => foreign key name (may be null)
PK_NAME String => primary key name (may be null)
DEFERRABILITY short => can the evaluation of foreign key constraints be deferred until commit
importedKeyInitiallyDeferred - see SQL92 for definition
importedKeyInitiallyImmediate - see SQL92 for definition
importedKeyNotDeferrable - see SQL92 for definition

Parameters:
catalog - a catalog name; must match the catalog name as it is stored in the database; "" retrieves those without a catalog; null means that the catalog name should not be used to narrow the search
schema - a schema name; must match the schema name as it is stored in the database; "" retrieves those without a schema; null means that the schema name should not be used to narrow the search
table - a table name; must match the table name as it is stored in the database
Returns:
ResultSet - each row is a primary key column description
Throws:
SQLException - if a database access error occurs
See Also:
getExportedKeys(java.lang.String, java.lang.String, java.lang.String)
*/
}
