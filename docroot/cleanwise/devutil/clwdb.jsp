<%@page language="java" import="java.sql.*,java.util.*,javax.naming.*,javax.sql.*"%>
<%@page import="com.cleanwise.view.utils.ClwCustomizer" %>
<html>
	<head>
		<title>
			DB description
		</title>
		<% String storeDir=ClwCustomizer.getStoreDir(); %>

<%!

	Connection conn;
	DatabaseMetaData meta;
	DataSource ds = null;
	String returnPage = "../adminportal/systemhome.do";
	String mThisPage = returnPage + "?action=browseDatabase";
	String gSchemaName = "";
	Hashtable gFilterVals = null;

	String f_header(String schname) {
  		String hdr = "<div class=\"bx\"> " +  "<table><tr><td>" + "Current Schema: " + schname +
  		"</td><td>" + "<b> <a href='" + mThisPage + "&op=list_schemas'>List all Schemas</a>" +
  		"</td><td>" + "<a href='" + mThisPage + "&op=disp'>Table names</a>" +
  		"<td><a href='" + mThisPage + "&op=dispall'>Desc all tables</a>" + 
  		"<td align=left><a href='" + returnPage + "'>Admin</a>" + "</tr></table></div><br>";
  		return hdr;
	}

	String f_tableDescLink(String pTabname) {
		String tabref = "<a href='" + mThisPage + "&op=desc&tabname=" + pTabname + "'> Desc </a> ";
	  	return tabref;
	}

	String f_tableDumpLink(String pTabname) {
	  	String tabref = "<a href='" + mThisPage + "&op=dump&tabname=" + pTabname + "'> Dump </a> ";
	  	return tabref;
	}

	String f_dumpTableAction(Connection conn, String pTabname, String pColname, String pColval, String pCond) {
    	Hashtable tabColumns = new Hashtable();
		try {
	    	ResultSet columns = meta.getColumns(null, gSchemaName, pTabname, "%"); 
	    	while (columns.next()) {
	      		tabColumns.put( columns.getString("COLUMN_NAME"),
	        	columns.getString("TYPE_NAME") );
	    	}
	    	columns.close();
		} 
		catch (Exception e) { 
			e.printStackTrace(); 
		}
		StringBuffer res = new StringBuffer();
		String sqlstr = "select * from " + pTabname;
		if (null != pCond && pCond.length() > 0) {
	  		sqlstr += " where " + pColname + " " + pColval;
		}
		if (gFilterVals == null ) { 
			gFilterVals = new Hashtable();
		}
	    if (null != gFilterVals ) {
	    	for (Enumeration e=gFilterVals.keys(); e.hasMoreElements();) {
	        	String vname = (String)e.nextElement();
	           	String val = (String)gFilterVals.get(vname);
	         	if (tabColumns.containsKey(vname)) {
	           		if (sqlstr.indexOf(" where ") > 0)	{
	             		sqlstr += " and " + vname + " " + val;
	           		}
	           		else
	           		{
	             		sqlstr += " where " + vname + " " + val ;
	           		}
	         	}
	      	}
	    }
		if (pTabname != null && pColname != null && pColval != null) {
		  	gFilterVals.put(pColname, pColval);
		}
		if (pCond == null) {
			if (pColname != null) {
				sqlstr += " order by " + pColname + " DESC";
			}
		}
		else
		{
			if (pCond.equals("start")) {
    			sqlstr = "select * from " + pTabname + " order by " + pColname + " ASC";
  			}
			else if (pCond.equals("end")) {
    			sqlstr = "select * from " + pTabname + " order by " + pColname + " DESC";
			}
		}
		res.append(  "<br>Query: " + sqlstr);
		try {
			java.sql.Statement stmt1;
  			ResultSet rs;
  			ResultSetMetaData rsmd;
  			int maxr = 1001;
			try { 
    			stmt1 = conn.createStatement();
    			stmt1.setMaxRows(maxr);
			    rs = stmt1.executeQuery( sqlstr ); 
    			rsmd = rs.getMetaData();
  			} 
  			catch (SQLException e) {
    			res.append(  "<br> Got SQL exception: " + e);
    			return res.toString();
  			}
  			catch (Exception e) {
    			res.append(  "<br> Got exception: " + e);
    			return res.toString();
  			}
			int ncols = rsmd.getColumnCount();
			int rowc = 0;
			int tc = 0;
  			res.append(" <br> <table> ");
  			boolean add_a = true;
			rowc = 0;
			while (rs.next()) {
  				rowc++;
				res.append(  "<tr><td>ROW</td><td>" + ++tc + "</td></tr>");
				for (int i = 1; i < (ncols+1); i++ ) {
				    String tcl = "td2";
    				if (1 == i ) { 
    					tcl = "td0";    
    				} 
			    	res.append("\r\n <tr><td class=\"" + tcl + "\">" + rsmd.getColumnName(i) + "</td><td>");
			    	String v = rs.getString(i);
    				if (null != v ) {
      					res.append(v);
    				} 
    				else {
      					res.append("&nbsp;");
    				}
					res.append(  "</td>");
	  				res.append(  "</tr>");
  				}  /* End of for loop for all columns.*/
			}  /* End of data found result set. */
			res.append(  "</table>");
			stmt1.close();
		} 
		catch (Exception e) {
			res.append( "<p>exception 2: " + e);
		}
		return res.toString();
	}

	String f_descTable(String pTabname, int pidx, HttpServletRequest request) {
		String desc = "<table><tr bgcolor=lightblue><td> DB Table ";
  		if (pidx > 0) { 
  			desc += pidx; 
  		} 
  		else { 
  			desc += "<br>"; 
  		}
  		desc += "<td colspan=2> " + pTabname + "</tr>";
		try {
    		ResultSet keys = meta.getPrimaryKeys(null, gSchemaName, pTabname); 
    		while (keys.next()) {
      			desc += "<tr bgcolor=lightgrey><td>Primary Key " + "<td>" + keys.getString("COLUMN_NAME") + "</tr>";
    		}
    		keys.close();
    		// Get the foreign keys.	  
    		ResultSet fkeys = meta.getImportedKeys(null, gSchemaName, pTabname); 
    		while (fkeys.next()) {
      			desc += "<tr bgcolor=yellow><td>Foreign key<td>" + fkeys.getString("FKCOLUMN_NAME") + "</tr>";
    		}
    		fkeys.close();
    		// Get all the column information for this table.
    		ResultSet columns = meta.getColumns(null, gSchemaName, pTabname, "%"); 
    		while (columns.next()) {
    			desc += "<form method='GET' action='" + returnPage + "'>";
    			desc += "<tr><td> <td>- <b>" + columns.getString("COLUMN_NAME") + "</b></td>";
			    desc += "<td>" + columns.getString("TYPE_NAME") + "<td> (" + columns.getString("COLUMN_SIZE") + ")";
			    String rem = columns.getString("REMARKS");
    			if (rem != null) {
    				desc += "<td>" + rem;
    			}
    			desc += "<td>" + "<input type=hidden name=action value=browseDatabase>" 
    					+ "<input type=hidden name=op value=dump>"
      					+ "<input type=hidden name=condition value=eq>"
      					+ "<input type=hidden name=tabname value=" + pTabname + ">"
      					+ "<input type=hidden name=colname value=" + columns.getString("COLUMN_NAME") + ">"
      					+ "<input type=text name=colval>"
      					+ "<input class=\"fb\" type=submit name=Submit value=Submit>";
    			desc += "</td></tr></form>";
  			}
			desc += "</table>";
			columns.close();
		} 
		catch (Exception e) {
  			desc += e.toString();
		}
		return desc;
	}
%>

		<STYLE TYPE="text/css">
			.fb  {
			font-size: 9px;
			}
			body  {
			font-size: 11px;
			}
			td  {
			font-size: 10px;
			font-family: Verdana;
			background-color: #ccccff;
			}
			.td2  {
			 font-size: 10px;
			 font-family: Verdana;
			 font-weight: bold;
			 background-color: #ff9966;
			 color: #000066;
			}
			.td0  {
			 font-size: 10px;
			 font-family: Verdana;
			 font-weight: bold;
			 background-color: white;
			 color: black;
			 border: solid 1px black;
			}
			.bx {
			 border: solid 1px black;
			 background-color: white;
			}
			A {
			 text-decoration: none;
			 border-top: solid 1px #cccccc;
			 border-left: solid 1px #cccccc;
			 border-right: solid 1px black;
			 border-bottom: solid 1px black;
			 background-color: #cccccc;
			}
			A:hover {
			background-color: black;
			color: white;
			}
			TH {border: solid black; border-width: 1px;
			font-size: 10px;
			background-color: #ccffcc;
			}
		</STYLE>
	</head>
	<body>
<%
	// Check for a valid login.
	String userName = (String)session.getAttribute("LoginUserName");
	if (userName == null) {
		// Redirect to the logon page.
	  	response.sendRedirect("/"+storeDir+"/userportal"); 
	}
	String userType = (String)session.getAttribute("UserType");
	if (userType == null || userType.indexOf("ADMINISTRATOR") == -1) {
	  	// Redirect to the logon page.
	  	response.sendRedirect("/"+storeDir+"/userportal"); 
	}
	String sname = (String)session.getAttribute("DB_SCHEMA");
	String hdrstring = f_header(sname);
%>
		<%= hdrstring %>
<%
	String lact = request.getParameter("op");
	if (lact == null) {
		lact = "disp";
	}
	String lact2 = request.getParameter("op2");
	if (lact2 == null) {
		lact2 = "";
	}
	String datasource = "java:/OracleDS";
	InitialContext ctx = new InitialContext();
	ds = (DataSource)ctx.lookup(datasource);
	conn = ds.getConnection();
	meta = conn.getMetaData();
	// Pick a schema first.
	String ostring = "  ";
	String opstr = request.getParameter("op");
	if (null == opstr ) {
		opstr = "init";
	}
	if (session.getAttribute("DB_SCHEMA") == null || opstr.equals("list_schemas") || opstr.equals("choose_schema")) {
  		gSchemaName = request.getParameter("schema_name");
  		if (gSchemaName != null ) {
    		session.setAttribute("DB_SCHEMA", gSchemaName);
    		response.sendRedirect(mThisPage);
    		return;
  		}
  		gSchemaName = (String)session.getAttribute("DB_SCHEMA");
  		if (opstr.equals("list_schemas") || gSchemaName == null ) {
    		ostring = "<div class=\"bx\">";
    		ostring += "<table><tr bgcolor=white><td>Schemas</td><td></td></tr>" ;
    		ResultSet sch = meta.getSchemas();
    		while (sch.next()) {
      			ostring += "<tr><td></td><td><a href='" + mThisPage + "&op=choose_schema&amp;schema_name=" + sch.getString(1) + "'>" + sch.getString(1) + "</a></td></tr>";
    		}
    		ostring += "</table></div>";
    		sch.close();
  		}
	}
%>
		<%= ostring %>
<%
	gSchemaName = (String)session.getAttribute("DB_SCHEMA");
	gFilterVals = (Hashtable)session.getAttribute("DB_FILTER");
	if (gFilterVals == null) { 
		gFilterVals = new Hashtable(); 
	}
	session.setAttribute("DB_FILTER", gFilterVals);
	// Only get the tables.
	String typesReq[] = { "TABLE" };
	ResultSet tables = meta.getTables(null,gSchemaName,null,typesReq);
	if (lact.equals("disp")) {
%>
			<p><b>List of tables.</b>
			<table>
<%
		int tc = 0;
		tab_loop1:
		while (tables.next()) {
			String tabName = tables.getString("TABLE_NAME");
			// Dont show the sequence tables.
  			if (tabName.endsWith("_SEQ")) {
  				continue tab_loop1; 
  			}
  			String bgc="white";
  			if ((tc%2) != 0) {
   				bgc = "#cccc99";
  			}
%>
				<tr bgcolor=<%=bgc%>>
					<td>
						<% tc++; %>
						<%= tc %>&nbsp;&nbsp;&nbsp;
					</td>
					<td>
						<%= tabName %>
					</td>
					<td>
						<%= f_tableDumpLink(tabName) %>
					</td>
					<td>
						<%= f_tableDescLink(tabName) %>
					</td>
				</tr>
<% 
		}
%>
			</table>
<% 
	} 
	else if (lact.equals("desc")) {
		String lTabDesc = "";
		String lTabname = request.getParameter("tabname");
		if (lTabname != null) {
  			lTabDesc = "&nbsp;&nbsp;&nbsp;&nbsp;" + f_tableDumpLink(lTabname) + 
  				lTabname + "<br>" +  f_descTable(lTabname, 0, request);
		}
%>
	<%= lTabDesc %>
<% 
	} 
	else if (lact.equals("dump")) {
		String lTabDump = "";
		String lTabname = request.getParameter("tabname");
		String lColname = request.getParameter("colname");
		String lColval = request.getParameter("colval");
		String lCond = request.getParameter("condition");
		if (lTabname != null) {
			lTabDump = "&nbsp;&nbsp;&nbsp;&nbsp;"  + f_tableDescLink(lTabname) + lTabname + "<br>" + 
  				f_dumpTableAction(conn, lTabname, lColname, lColval, lCond);
		}
%>
	<%= lTabDump %>
<% 
	} 
	else if (lact.equals("dispall")) { 
%>
		<b>
			db info, generated on: <%= new java.util.Date().toString() %>
		</b>
<%
		int tcount = 0;
		tab_loop:
		while (tables.next()) {
			String tabName = tables.getString("TABLE_NAME");
			// Dont show the sequence tables.
			if (tabName.endsWith("_SEQ")) {
				continue tab_loop; 
			}
			String tabDesc = f_descTable(tabName, ++tcount, request);
%>
		<%= tabDesc %>
<%
		} // End of tables.next
	} // End of dispall action 
	else if ( lact.equals("rmfilter")) {
  		gFilterVals = new Hashtable();
  		String tabname = request.getParameter("tabname");
  		if (null != tabname) {
    		String tabDesc2 = f_descTable(tabname, 1, request);
%>
    	<%= tabDesc2 %>
<%
  		}
	}
  	// Release references.
  	if (null != conn) {
  		conn.close();
  	} 
  	conn = null;  
  	ds = null;  
  	meta = null;
%>
		<br>
<%
	String val = "";
	String vname = "";
	String filtersRes = "";
	String currTabname = request.getParameter("tabname");
	filtersRes += "<a href='" + mThisPage + "&op=rmfilter";
	if (null != currTabname) {
		filtersRes += "&tabname=" + currTabname;
	}
	filtersRes +="'>[Remove Filters]</a><br>";
	if (null != gFilterVals) {
    	for (Enumeration e=gFilterVals.keys(); e.hasMoreElements();) {
        	vname = (String)e.nextElement();
         	val = (String)gFilterVals.get(vname);
      		filtersRes += "<form method='GET' action='" + returnPage + "'>";
      		filtersRes += " " + vname ;
      		filtersRes += "&nbsp;&nbsp; <input type=hidden name=action value=browseDatabase>"
      			+ "<input type=hidden name=op value=dump>" + "<input type=hidden name=condition value=eq>"
      			+ "<input type=hidden name=tabname value=" + currTabname + ">"
      			+ "<input type=hidden name=colname value=" + vname + ">"
      			+ "<input type=text name=colval value=" + val +">"
      			+ "<input class=\"fb\" type=submit name=Submit value=Submit>";
      		filtersRes += "</form>";
	     }
	}
	session.setAttribute("DB_FILTER", gFilterVals);
%>
<%=filtersRes%>
	</body>
</html>