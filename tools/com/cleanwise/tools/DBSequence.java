package com.cleanwise.tools;

import org.apache.tools.ant.*;
import org.apache.tools.ant.types.*;

import java.io.*;
import java.sql.*;
import java.text.*;
import java.util.*;

public class DBSequence extends Task {
	private String driver;
	private String url;
	private String userid;
	private String password;
	private File outfile;
	private Path classpath;
	private PrintStream out;
	private HashSet pk;
	private HashSet fk;

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

	public void execute() throws BuildException {
		Connection conn;
		DatabaseMetaData meta;

		log("Recreating database sequences...");

		try {
			AntClassLoader loader = new AntClassLoader(project, classpath,false);
			Class.forName(driver);
		} catch (Exception e) {
			throw new BuildException("ClassNotFoundException: " + e.getMessage(), e, location);
		}
		String tabName = null;
		String idName = null;
		try {
			conn = DriverManager.getConnection(url, userid, password);

			meta = conn.getMetaData();

			// Only get the tables.
			String typesReq[] = { "TABLE" };
			ResultSet rs = meta.getTables(null, userid, "CLW_%", typesReq);
			ArrayList tables = new ArrayList();
			while (rs.next()) {
				tables.add(rs.getString("TABLE_NAME"));
			}
			rs.close();
			Iterator tableI = tables.iterator();
			while (tableI.hasNext()) {
				try {
					tabName = (String) tableI.next();
					idName = tabName.substring(4);
					Statement stmt = conn.createStatement();
					try {
						rs = stmt.executeQuery("DROP SEQUENCE " + tabName
								+ "_SEQ");
					} catch (SQLException e) {
						// ignore - maybe it doesn't yet exist
					}
					stmt.close();
					stmt = conn.createStatement();
					rs = stmt.executeQuery("SELECT MAX(" + idName
							+ "_ID)+1 FROM " + tabName);
					String start = "1";
					if (rs.next()) {
						start = rs.getString(1);
						if (start == null) {
							start = "1";
						}
					}
					rs.close();
					stmt.close();
					stmt = conn.createStatement();
					rs = stmt.executeQuery("CREATE SEQUENCE "+ tabName+ "_SEQ START WITH "+ start
									+ " INCREMENT BY 1 NOMINVALUE NOMAXVALUE NOCYCLE CACHE 10 NOORDER");
					rs.close();
					stmt.close();
				} catch (SQLException e) {
					System.out.println("SQLException creating sequence on table: "+ tabName
									+ "  column: "+ idName+ "_ID : " + e.getMessage());
				}
			}

		} catch (SQLException e) {
			throw new BuildException("SQLException " + e.getMessage(), e, location);
		}

	}
}
