<%@page language="java" %>
<%@page import="javax.servlet.http.*" %>
<%@page import="java.io.*" %>


<%
String s = System.getProperty("xri.reqCt");
java.lang.Integer envI;
if ( null == s) {
  envI = new java.lang.Integer(0);
  System.setProperty("xri.reqCt", "0");
} else {
  envI = new java.lang.Integer(s);
}

int mReqCt  = envI.intValue() + 1;
System.setProperty("xri.reqCt", String.valueOf(mReqCt));

%>

<%
	
	PrintWriter writer = response.getWriter();

	if ( request.getParameter("action") != null && 
	     request.getParameter("action").equals("xmlreq") ) {

	    String reqQuery = request.getParameter("searchv");

	    response.setContentType("text/xml");
	    String queryRes = "Not found";
	    try {
	    	// assume this is a status check.
		queryRes = "<server>" + java.net.InetAddress.getLocalHost()
		  + "</server>";

                java.math.BigDecimal fm = 
		  new java.math.BigDecimal(Runtime.getRuntime().freeMemory());
                java.math.BigDecimal tm =
		  new java.math.BigDecimal(Runtime.getRuntime().totalMemory());
                java.math.BigDecimal mbval = new java.math.BigDecimal(1000000);
		fm = fm.divide(mbval, java.math.BigDecimal.ROUND_UP);
		tm = tm.divide(mbval, java.math.BigDecimal.ROUND_UP);
                java.math.BigDecimal pfm = fm.divide(tm, 3, java.math.BigDecimal.ROUND_UP);

	    	queryRes += "<freemem>" + fm +" MB </freemem> "
                + "<totalmem>" + tm +" MB </totalmem>" 
	    	+ "<rfreemem>" + pfm +" </rfreemem>"  
		;

		queryRes += "<threads>" + 
			 String.valueOf(Thread.currentThread().activeCount()
			+ "</threads>");


		java.util.Hashtable loginSessions = (java.util.Hashtable)
		pageContext.getAttribute("login.session.vector",
		PageContext.APPLICATION_SCOPE );	    

	       if ( null != loginSessions ) { 
	         queryRes += "<sessioncount>" +
		 	  loginSessions.keySet().size()
		 	  + "</sessioncount>" ;
                 java.util.Enumeration sesKeys = loginSessions.keys();

                 while (sesKeys.hasMoreElements()) {

                   javax.servlet.http.HttpSession thisSes = 
                     (javax.servlet.http.HttpSession)sesKeys.nextElement();  
                   String userName = null;

                   try { userName = (String)thisSes.getAttribute("LoginUserName"); }
                   catch (Exception e) {}
                   if ( null == userName ) {
                     loginSessions.remove(thisSes);
                   }
                 }
	       }


	    } catch (Exception e) {
		e.printStackTrace();
	    }
	    String xmlresp = "<?xml version=\"1.0\"?>" 
		+ "<result> <msg> Requests:" + mReqCt + "- "
		+ new java.util.Date()
		+ " </msg> "
		+ "<resultList> "+ queryRes
		+ "</resultList></result>\n\n";

	    writer.println(xmlresp);
	    writer.flush();
	    try { Thread.sleep(10); 
	    } catch(Exception e) {}
	    writer.close();
	} else {


	response.setContentType("text/html");
	writer.println("<html>");
	writer.println("<head>");
	writer.println("<title> Server Info</title>");
	writer.println("</head>");
	writer.println("<body bgcolor=white>");

	writer.println("<table border=\"0\">");
	writer.println("<td>");
	writer.println("<h1>Application Server</h1>");
	writer.println("</td>");
	writer.println("</tr>");
	writer.println("</table>");

	writer.println("<table border=\"0\" width=\"100%\">");
	java.util.Enumeration names = request.getHeaderNames();
	while (names.hasMoreElements()) {
	    String name = (String) names.nextElement();
	    writer.println("<tr>");
	    writer.println("  <th align=\"right\">" + name + ":</th>");
	    writer.println("  <td>" + request.getHeader(name) + "</td>");
	    writer.println("</tr>");
	}
	writer.println("</table>");


	writer.println("</body>");
	writer.println("</html>");

    }

%>

<!-- done -->
