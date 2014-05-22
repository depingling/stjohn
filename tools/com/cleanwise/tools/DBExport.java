package com.cleanwise.tools;

import org.apache.tools.ant.*;
import org.apache.tools.ant.types.*;
import org.apache.tools.ant.taskdefs.ExecTask;

import java.io.*;
import java.sql.*;
import java.text.*;
import java.util.*;

public class DBExport extends Task {
    private String driver;
    private String url;
    private String user;
    private String dbauser;
    private String dbapassword;
    private String server;
    private String outfile;
    private String expcmd;
    private Path classpath;
    private PrintStream out;

    public void setDriver(String driver) {
	this.driver = driver;
    }

    public void setUrl(String url) {
	this.url = url;
    }

    public void setUser(String user) {
	this.user = user.toUpperCase();
    }

    public void setDbauser(String dbauser) {
	this.dbauser = dbauser.toUpperCase();
    }

    public void setDbapassword(String dbapassword) {
	this.dbapassword = dbapassword.toUpperCase();
    }

    public void setServer(String server) {
	this.server = server;
    }

    public void setOutfile(String outfile) {
	this.outfile = outfile;
    }

    public void setExpcmd(String expcmd) {
	this.expcmd = expcmd;
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

	ExecTask cmd = (ExecTask) project.createTask("exec");
	cmd.setExecutable(expcmd);
	cmd.setFailonerror(true);
	cmd.setTaskName( getTaskName() );
	cmd.createArg().setValue(dbauser + "/" + dbapassword + "@" + server);
	cmd.createArg().setValue("FILE=" + outfile);
	cmd.createArg().setValue("OWNER=" + user);
	cmd.execute();

    }
}
