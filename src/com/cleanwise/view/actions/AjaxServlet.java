package com.cleanwise.view.actions;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.apache.struts.action.*;


public final class AjaxServlet extends ActionSuper {

    int mReqCt  = 0;

    public ActionForward performSub(ActionMapping mapping,
				    ActionForm form,
				    HttpServletRequest request,
				    HttpServletResponse response)
	throws IOException, ServletException {
	
	PrintWriter writer = response.getWriter();

	if ( request.getParameter("action") != null && 
	     request.getParameter("action").equals("xmlreq") ) {

	    String reqQuery = request.getParameter("searchv");

	    response.setContentType("text/xml");
	    String queryRes = "Not found";
	    try {
	    	// assume this is a status check.
	    	queryRes = "<freemem>Free mem: " + String.valueOf(Runtime.getRuntime().freeMemory())
		+"</freemem><totalmem>Tot  mem: " + String.valueOf(Runtime.getRuntime().totalMemory())
		+"</totalmem><threads>Thread n: " + String.valueOf(Thread.currentThread().activeCount()
			+ "</threads>");
	    } catch (Exception e) {
		e.printStackTrace();
	    }
	    String xmlresp = "<?xml version=\"1.0\"?>" 
		+ "<result> <msg> Requests:" + ++mReqCt + "- "
		+ new java.util.Date()
		+ " </msg> "
		+ "<resultList> "+ queryRes
		+ "</resultList></result>\n\n";
	    writer.println(xmlresp);
	    writer.flush();
	    try { Thread.sleep(10); 
	    } catch(Exception e) {}
	    writer.close();
	    return null;
	}


	response.setContentType("text/html");
	writer.println("<html>");
	writer.println("<head>");
	writer.println("<title>Search Servlet Info</title>");
	writer.println("</head>");
	writer.println("<body bgcolor=white>");

	writer.println("<table border=\"0\">");
	writer.println("<td>");
	writer.println("<h1>AJAX Application Servlet</h1>");
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

	return null;
    }
    

}
