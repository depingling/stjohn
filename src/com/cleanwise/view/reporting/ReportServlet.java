package com.cleanwise.view.reporting;

/**
 * This a servlet class that generates the XML data for the report
 * At a minimum it is passed a 'name' parameter that identifies the
 * EJB method that should be called.  Any of these will likely require
 * their own specific parameters.
 *
 * Copyright:   Copyright (c) 2001
 * Company:     CleanWise, Inc.
 * @author      Tim Besser
 *
 */

import java.io.*;
import java.util.*;
import java.text.SimpleDateFormat;

import javax.servlet.*;
import javax.servlet.http.*;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import org.apache.xml.serialize.*;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.session.OrderAnalysis;
import com.cleanwise.service.api.value.OrderAnalysisView;
import com.cleanwise.service.api.value.OrderAnalysisViewVector;
import com.cleanwise.view.utils.CurrencyFormat;


public class ReportServlet extends HttpServlet {
    
    /**
     * <code>init</code> Placeholder for any initialization
     *
     * @param config a <code>ServletConfig</code> value
     * @exception ServletException if an error occurs
     */
    public void init(ServletConfig config) throws ServletException {
    }
   
     /**
     * <code>doGet</code> generates the XML data to be used by the report
     * engine.
     *
     * @param req a <code>HttpServletRequest</code> value
     * @param res a <code>HttpServletResponse</code> value
     * @exception ServletException if an error occurs
     * @exception IOException if an error occurs
     */
    public void doGet(HttpServletRequest req, 
		      HttpServletResponse res)
	throws ServletException, IOException
    {
	try {
             String name = (String)req.getParameter("name");
                if (name == null) {
  		throw new ServletException("Missing 'name' parameter");
	        }
            OrderAnalysisViewVector oav = null; 
     
            
            String interval = null;
            Date beginDate = null;
            Date endDate = null;
            java.math.BigDecimal minAmt;
            java.math.BigDecimal maxAmt;
            boolean inclusive = true;
            int accountId = 0;
            int vendorId = 0;
            int manufacturerId = 0;
            String account = req.getParameter("account");

	    APIAccess factory = new APIAccess();
	    OrderAnalysis orderAnalysisBean = 
		factory.getOrderAnalysisAPI();

	    SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");

            //parse all parameters
	    String begin = req.getParameter("begin");
	    beginDate = df.parse(begin);

	   	String end = req.getParameter("end");
		if (end == null) {
		    endDate = new Date();
		} else {
		    endDate = df.parse(end);
		}

	    String amt = req.getParameter("min");
	    if (amt == null) {
		minAmt = new java.math.BigDecimal(0);
	    } else {
		try {
		    minAmt = CurrencyFormat.parse(amt);
		} catch (Exception e) {
		    minAmt = new java.math.BigDecimal(0);
		}
	    }

	    amt = req.getParameter("max");
	    if (amt == null) {
		maxAmt = new java.math.BigDecimal(0);
	    } else {
		try {
		    maxAmt = CurrencyFormat.parse(amt);
		} catch (Exception e) {
		    maxAmt = new java.math.BigDecimal(0);
		}
	    }

	    String inc = req.getParameter("inclusive");
	    if (inc != null && inc.equals("false")) {
		inclusive = false;
	    }

	    String a = req.getParameter("account");
	    if (a != null && !a.equals("")) {
		accountId = Integer.parseInt(a);
	    }

            	    String v = req.getParameter("vendor");
	    if (v != null && !v.equals("")) {
		vendorId = Integer.parseInt(v);
	    }

            	    String m = req.getParameter("manufacturer");
	    if (m != null && !m.equals("")) {
		manufacturerId = Integer.parseInt(m);
	    }

            

              /* *****************
              * for each report a new else if must be added based on request name
              */
            if (name.equals("getOrderAnalysis")) {
                 interval = req.getParameter("interval");
		         oav =
                 orderAnalysisBean.getOrderAnalysis(account, beginDate, 
                                                   endDate, interval, 
                                                   minAmt, maxAmt, 
                                                   inclusive);
                 
            } else if (name.equals("getOrderAnalysisSummary")){
                    oav = new OrderAnalysisViewVector();
		    OrderAnalysisView oa = 
			orderAnalysisBean.getOrderAnalysisSummary(account, 
								  beginDate, 
								  endDate, 
								  minAmt, 
								  maxAmt, 
								  inclusive);
		    oav.add(oa);
              } else if (name.equals("getOrderAnalysisByState")){
                    oav =
                    orderAnalysisBean.getOrdersByState(accountId, beginDate, 
                                                       endDate, minAmt, maxAmt, 
                                                       inclusive);
                    
              } else if (name.equals("getOrderAnalysisByType")){
                     oav =
                    orderAnalysisBean.getOrdersByType(accountId, beginDate, 
                                                     endDate);

                     }
            
	

        DocumentBuilderFactory docfactory = DocumentBuilderFactory.newInstance();
        docfactory.setNamespaceAware(true);
        DocumentBuilder docBuilder = null;

        try {
        	docBuilder = docfactory.newDocumentBuilder();
        } catch (ParserConfigurationException ex) {
        }
        Document doc = docBuilder.getDOMImplementation().createDocument("", "OrderAnalysisList", null );
	    //Create node
	    Element root = doc.getDocumentElement();
	    //Add node to XML document
	    doc.appendChild(root);
	    Iterator iter = oav.iterator();
            int n = 0;
	    while (iter.hasNext()) {
		OrderAnalysisView oa = (OrderAnalysisView)iter.next();
		Element node = oa.toXml(doc);
                // uncomment if you want to see xml output
		root.appendChild(node);
		    // XXX - Until memory problems solved, limit the
		    // report to 1000 'rows'
		  //  if (n++ > 1000) {
		//	break;
		//    }
	    } 

	    // done with vector, maybe this will help with memory;
	    // report generator needs all it can get
	    oav = null;

	    OutputStream out = res.getOutputStream();
        //    OutputStream out2 = System.out;

        OutputFormat format = new OutputFormat( Method.XML, "UTF-8", true );
        XMLSerializer serializer = new XMLSerializer(out,format);
        serializer.serialize(doc);

    //    doc.write(out);
    //    doc.write(out2);


	} catch (Exception e) {
	    e.printStackTrace();
	    throw new ServletException(e.getMessage());
	}
    }
    
   
    

    /**
     * <code>doPost</code> just calls doGet(). i.e. a GET is treated
     * identically to a POST
     *
     * @param req a <code>HttpServletRequest</code> value
     * @param res a <code>HttpServletResponse</code> value
     * @exception ServletException if an error occurs
     * @exception IOException if an error occurs
     */
    public void doPost(HttpServletRequest req, HttpServletResponse res)
	throws ServletException, IOException
    {
	// we don't care whether it is a post or a get
	doGet(req, res);
    }
   
}
