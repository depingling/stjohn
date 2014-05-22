package com.cleanwise.tools;

import org.apache.tools.ant.*;
import org.apache.tools.ant.types.*;

import java.io.*;
import java.sql.*;
import java.text.*;
import java.util.*;

public class DBRefConstraints extends Task {
    private String driver;
    private String url;
    private String userid;
    private String password;
    private boolean enable;
    private File outfile;
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
    
    public void setEnable(boolean e) {
	this.enable = e;
    }

    public void execute() throws BuildException {
	Connection conn;
	DatabaseMetaData meta;

	if (enable) {
	    log("Enabling database referential constraints...");
	} else {
	    log("Disabling database referential constraints...");
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
	  
	    Statement stmt = conn.createStatement();
	    ResultSet rs = stmt.executeQuery("SELECT TABLE_NAME,CONSTRAINT_NAME FROM USER_CONSTRAINTS WHERE OWNER = UPPER('" + userid + "') AND TABLE_NAME LIKE 'CLW_%' AND CONSTRAINT_TYPE = 'R'");
	    ArrayList tables = new ArrayList();
	    ArrayList refs = new ArrayList();
	    while (rs.next()) {
		tables.add(rs.getString("TABLE_NAME"));
		refs.add(rs.getString("CONSTRAINT_NAME"));
	    }
	    rs.close();
	    stmt.close();

	    for (int i = 0; i < tables.size(); i++) {
		String t = null;
		if (enable) {
		    t = " ENABLE";
		} else {
		    t = " DISABLE";
		}
		stmt = conn.createStatement();
		rs = stmt.executeQuery("ALTER TABLE " + 
				       (String)tables.get(i) + t +
				       " CONSTRAINT " +
				       (String)refs.get(i));
		stmt.close();
		rs.close();
	    }

	} catch (SQLException e) {
	    throw new BuildException("SQLException: " + e.getMessage(),
				     e, location);
	}
    }

}
