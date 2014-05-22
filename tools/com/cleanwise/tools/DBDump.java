package com.cleanwise.tools;

import org.apache.tools.ant.*;
import org.apache.tools.ant.types.*;

import java.io.*;
import java.sql.*;
import java.text.*;
import java.util.*;

public class DBDump extends Task {
    private final String TERMINATOR = "";

    private String driver;
    private String url;
    private String userid;
    private String password;
    private String dir;
    private Path classpath;
    private PrintStream out;

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

    public void setDir(String dir) {
	this.dir = dir;
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
    
    public void execute() throws BuildException {
	Connection conn;
	DatabaseMetaData meta;

	log("Dumping database to directory: " + dir);

	try {

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
	  
		// Only get the tables.
		String typesReq[] = { "TABLE" };
		ResultSet tables = meta.getTables(null,userid,"CLW_%", typesReq);
		ArrayList tableNames = new ArrayList();

		while (tables.next()) {
		    String tabName = tables.getString("TABLE_NAME");
		    tableNames.add(tabName);
		}

		java.text.SimpleDateFormat df = 
		    new SimpleDateFormat("MM/dd/yyyy");
		java.text.SimpleDateFormat tf = 
		    new SimpleDateFormat("HH:mm:ss");

		Iterator tableI = tableNames.iterator();
		while (tableI.hasNext()) {
		    String tabName = (String)tableI.next();

		    out = new PrintStream(new BufferedOutputStream(new FileOutputStream(dir + "/" + tabName + ".ctl")));

		    out.print("LOAD DATA\n");
		    out.print("INFILE *\n");
		    out.print("INTO TABLE " + tabName + "\n");
		    out.print("FIELDS TERMINATED BY '" + TERMINATOR + "'\n");
		    out.print("TRAILING NULLCOLS\n");
		    out.print("(\n");

		    boolean first = true;

		    ResultSet columns =
			meta.getColumns(null, userid, tabName, "%");
		    while (columns.next()) {
			String colName = columns.getString("COLUMN_NAME");
			String typeName = columns.getString("TYPE_NAME");
			int digits = columns.getInt("DECIMAL_DIGITS");
			int size = columns.getInt("COLUMN_SIZE");
			int nullable = columns.getInt("NULLABLE");
			if (!first) {
			    out.print("," + colName + " ");
			} else {
			    first = false;
			    out.print(colName + " ");
			}
			out.print(getFieldInfo(colName,typeName,size,digits) +
				  "\n");
		    }
		    columns.close();
		    out.print(")\n");
		    out.print("BEGINDATA\n");

		    Statement stmt = conn.createStatement();
		    ResultSet rs = 
			stmt.executeQuery("SELECT * FROM " + tabName);
		    ResultSetMetaData rsmd = rs.getMetaData();
		    int numberOfColumns = rsmd.getColumnCount();

		    while (rs.next()) {
			for (int i = 1; i <= numberOfColumns; i++) {
			    String coltype = rsmd.getColumnTypeName(i);
			    String v;
			    if (coltype.equals("DATE")) {
				String colname = rsmd.getColumnName(i);
				int l = colname.length();
				java.util.Date d;
				if (l > 5 && 
				    colname.substring((l-5),l).equals("_TIME")) {
				    d = rs.getTime(i);
				    if (d == null) {
					v = "";
				    } else {
					v = tf.format(d);
				    }
				} else {
				    d = rs.getDate(i);
				    if (d == null) {
					v = "";
				    } else {
					v = df.format(d);
				    }
				}
			    } else {
				v = rs.getString(i);
				if (v == null) v = "";
			    }
			    out.print(v);
			    if (i == numberOfColumns) {
				out.print("\n");
			    } else {
				out.print(TERMINATOR);
			    }
			}
		    }
		    rs.close();
		    stmt.close();
		    out.close();
		}
	    } catch (SQLException e) {
		throw new BuildException("SQLException: " + e.getMessage(),
					 e, location);
	    }

	} catch (IOException ioe) {
	    throw new BuildException("Error writing ", ioe, location);
	}
	finally {
	    out.close();
	}
    }

    private String getFieldInfo (String colName, String datatype, 
				 int size, int digits)
    {
	if (datatype.equals("VARCHAR2") || datatype.equals("CHAR")) {
	    return "CHAR(" + size + ") NULLIF (" + colName + "=BLANKS)";
	} else if (datatype.equals("NUMBER") && digits == 0) {
	    return "INTEGER EXTERNAL NULLIF (" + colName + "=BLANKS)";
	} else if (datatype.equals("NUMBER")) {
	    return "DECIMAL EXTERNAL NULLIF (" + colName + "=BLANKS)";
	} else if (datatype.equals("DATE")) {
	    int l = colName.length();
	    if (l > 5 && 
		colName.substring((l-5),l).equals("_TIME")) {
		return "DATE \"HH24:MI:SS\" NULLIF (" + colName + "=BLANKS)";
	    } else {
		return "DATE \"MM/DD/YYYY\" NULLIF (" + colName + "=BLANKS)";
	    }
	} else {
	    return "XXX";
	}
    }

}
