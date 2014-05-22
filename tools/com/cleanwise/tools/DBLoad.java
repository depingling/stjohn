package com.cleanwise.tools;

import org.apache.tools.ant.*;
import org.apache.tools.ant.types.*;
import org.apache.tools.ant.taskdefs.ExecTask;

import java.io.*;
import java.sql.*;
import java.text.*;
import java.util.*;

public class DBLoad extends Task {
    private final String TERMINATOR = "|";

    private String driver;
    private String url;
    private String userid;
    private String password;
    private String server;
    private String dir;
    private String ignore;
    private String loadcmd;
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

    public void setServer(String server) {
	this.server = server;
    }

    public void setDir(String dir) {
	this.dir = dir;
    }

    public void setIgnore(String ignore) {
	this.ignore = ignore;
    }

    public void setLoadcmd(String loadcmd) {
	this.loadcmd = loadcmd;
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

	log("Loading database from directory: " + dir);

	try {
	    AntClassLoader loader = new AntClassLoader(project, classpath, false);
	    Class.forName(driver);
	} catch (Exception e) {
	    throw new BuildException("ClassNotFoundException: " + 
				     e.getMessage(), e, location);
	}
	
	try {
	    conn = 
		DriverManager.getConnection(url, userid, password);
	    
	    meta = conn.getMetaData();
	    
	    // Only get the tables.
	    String typesReq[] = { "TABLE" };
	    ResultSet tables = meta.getTables(null,userid,"CLW_%", typesReq);
	    
	    while (tables.next()) {
		String tabName = tables.getString("TABLE_NAME");
		if (ignore != null && tabName.equals(ignore)) {
		    continue;
		}
		loadTable(tabName);
		if (new File(dir+"/"+tabName+".ctl").exists()) {
		    System.out.println("sqlldr " + tabName);
		}
	    }

	} catch (SQLException e) {
	    throw new BuildException("SQLException: " + e.getMessage(),
				     e, location);
	}
    }

    public void loadTable(String tabName) throws BuildException {
	ExecTask cmd = (ExecTask) project.createTask("exec");
	cmd.setExecutable(loadcmd);
	cmd.setFailonerror(true);
	cmd.setTaskName( getTaskName() );
	cmd.setDir(new File(dir));
	cmd.createArg().setValue(userid + "/" + password + "@" + server);
	cmd.createArg().setValue("CONTROL=" + tabName + ".ctl");
	cmd.createArg().setValue("ERRORS=0");
	cmd.createArg().setValue("LOG=" + tabName + ".log");
	cmd.createArg().setValue("SILENT=(HEADER, FEEDBACK)");
	try {
	    cmd.execute();
	} catch (BuildException be) {
	    log("Failed loading " + tabName);
	    throw be;
	}
    }
}
